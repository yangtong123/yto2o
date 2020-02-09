package com.yt.service.impl;

import com.yt.dao.ProductDao;
import com.yt.dao.ProductImgDao;
import com.yt.dto.ImageHolder;
import com.yt.dto.ProductExecution;
import com.yt.entity.Product;
import com.yt.entity.ProductImg;
import com.yt.enums.ProductStateEnum;
import com.yt.exceptions.ProductOperationException;
import com.yt.service.ProductService;
import com.yt.util.ImageUtil;
import com.yt.util.PageCalculator;
import com.yt.util.PathUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/16 11:03
 */
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  ProductDao productDao;

  @Autowired
  ProductImgDao productImgDao;

  @Override
  public ProductExecution getProductList(Product productCondition, int pageIndex,
      int pageSize) throws ProductOperationException {
    int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
    List<Product> productList = this.productDao
        .queryProductList(productCondition, rowIndex, pageSize);
    int count = this.productDao.queryProductCount(productCondition);
    ProductExecution pe = new ProductExecution();
    pe.setProductList(productList);
    pe.setCount(count);
    return pe;
  }

  @Override
  public Product getProductById(long productId) throws ProductOperationException {
    return this.productDao.queryProductById(productId);
  }

  @Override
  @Transactional
  public ProductExecution addProduct(Product product, ImageHolder thumbnail,
      List<ImageHolder> productImgHolderList) throws ProductOperationException {

    // 参数校验
    if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
      product.setCreateTime(new Date());
      product.setLastEditTime(new Date());
      // 默认上架状态
      product.setEnableStatus(1);

      if (thumbnail != null) {
        this.addThumbnail(product, thumbnail);
      }

      try {
        int effectedNum = this.productDao.insertProduct(product);
        if (effectedNum <= 0) {
          throw new ProductOperationException("创建商品失败");
        }
      } catch (Exception e) {
        throw new ProductOperationException("创建商品失败： " + e.toString());
      }

      if (productImgHolderList != null && productImgHolderList.size() > 0) {
        this.addProductImgList(product, productImgHolderList);
      }

      return new ProductExecution(ProductStateEnum.SUCCESS, product);
    }

    return new ProductExecution(ProductStateEnum.EMPTY);
  }

  @Override
  @Transactional
  public ProductExecution modifyProduct(Product product, ImageHolder thumbnail,
      List<ImageHolder> productImgList) throws ProductOperationException {
    if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
      product.setLastEditTime(new Date());
      if (thumbnail != null) {
        Product tempProduct = this.productDao.queryProductById(product.getProductId());
        if (tempProduct.getImgAddr() != null) {
          ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
        }
        this.addThumbnail(product, thumbnail);
      }

      if (productImgList != null && productImgList.size() > 0) {
        this.deleteProductImgList(product.getProductId());
        this.addProductImgList(product, productImgList);
      }

      try {
        int effectedNum = this.productDao.updateProduct(product);
        if (effectedNum <= 0) {
          throw new ProductOperationException("更新商品信息失败");
        }
        return new ProductExecution(ProductStateEnum.SUCCESS, product);
      } catch (Exception e) {
        throw new ProductOperationException("更新商品信息失败：" + e.toString());
      }
    } else {
      return new ProductExecution(ProductStateEnum.EMPTY);
    }
  }

  private void addThumbnail(Product product, ImageHolder thumbnail) {
    String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
    String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
    product.setImgAddr(thumbnailAddr);
  }

  private void addProductImgList(Product product, List<ImageHolder> productImgHolderList) {
    String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
    List<ProductImg> productImgList = new ArrayList<>();

    for (ImageHolder productImgHolder : productImgHolderList) {
      String imgAddr = ImageUtil.generateNormalThumbnail(productImgHolder, dest);
      ProductImg productImg = new ProductImg();
      productImg.setImgAddr(imgAddr);
      productImg.setProductId(product.getProductId());
      productImg.setCreateTime(new Date());
      productImgList.add(productImg);
    }

    if (productImgList.size() > 0) {
      try {
        int effectedNum = this.productImgDao.batchInsertProductImg(productImgList);
        if (effectedNum <= 0) {
          throw new ProductOperationException("创建商品详情图失败");
        }
      } catch (Exception e) {
        throw new ProductOperationException("创建商品详情图片失败：" + e.toString());
      }
    }
  }

  private void deleteProductImgList(long productId) {
    List<ProductImg> productImgList = this.productImgDao.queryProductImgList(productId);
    for (ProductImg productImg : productImgList) {
      ImageUtil.deleteFileOrPath(productImg.getImgAddr());
    }
    this.productImgDao.deleteProductImgByProductId(productId);
  }
}
