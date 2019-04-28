package com.yt.dao;

import com.yt.BaseTest;
import com.yt.entity.Product;
import com.yt.entity.ProductCategory;
import com.yt.entity.ProductImg;
import com.yt.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/9 16:45
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductDaoTest extends BaseTest {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductImgDao productImgDao;

    @Test
    public void testAInsertProduct() {
        Shop shop1 = new Shop();
        shop1.setShopId(1L);
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryId(3L);

        Product product1 = new Product();
        product1.setProductName("测试1");
        product1.setProductDesc("测试 Desc1");
        product1.setImgAddr("test1");
        product1.setPriority(1);
        product1.setEnableStatus(1);
        product1.setCreateTime(new Date());
        product1.setLastEditTime(new Date());
        product1.setShop(shop1);
        product1.setProductCategory(productCategory1);

        Product product2 = new Product();
        product2.setProductName("测试2");
        product2.setProductDesc("测试 Desc2");
        product2.setImgAddr("test2");
        product2.setPriority(2);
        product2.setEnableStatus(0);
        product2.setCreateTime(new Date());
        product2.setLastEditTime(new Date());
        product2.setShop(shop1);
        product2.setProductCategory(productCategory1);

        Product product3 = new Product();
        product3.setProductName("test3");
        product3.setProductDesc("测试 Desc3");
        product3.setImgAddr("test3");
        product3.setPriority(3);
        product3.setEnableStatus(1);
        product3.setCreateTime(new Date());
        product3.setLastEditTime(new Date());
        product3.setShop(shop1);
        product3.setProductCategory(productCategory1);

        int effectedNum = productDao.insertProduct(product1);
        assert effectedNum == 1;
        effectedNum = productDao.insertProduct(product2);
        assert effectedNum == 1;
        effectedNum = productDao.insertProduct(product3);
        assert effectedNum == 1;
    }

    @Test
    public void testBQueryProductList() throws Exception {
        Product product = new Product();
        List<Product> productList = productDao.queryProductList(product, 0, 3);
        assertEquals(3, productList.size());
        int count = productDao.queryProductCount(product);
        assertEquals(9, count);
        product.setProductName("测试");
        productList = productDao.queryProductList(product, 0, 3);
        assertEquals(3, productList.size());
        count = productDao.queryProductCount(product);
        assertEquals(6, count);
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);
        productList = productDao.queryProductList(product, 0, 3);
        assertEquals(3, productList.size());
        count = productDao.queryProductCount(product);
        assertEquals(6, count);
    }


    @Test
    public void testCQueryProductByProductId() throws Exception {
        long productId = 1;
        ProductImg productImg1 = new ProductImg();
        productImg1.setImgAddr("图片1");
        productImg1.setImgDesc("测试图片1");
        productImg1.setPriority(1);
        productImg1.setCreateTime(new Date());
        productImg1.setProductId(productId);
        ProductImg productImg2 = new ProductImg();
        productImg2.setImgAddr("图片2");
        productImg2.setPriority(1);
        productImg2.setCreateTime(new Date());
        productImg2.setProductId(productId);
        List<ProductImg> productImgList = new ArrayList<ProductImg>();
        productImgList.add(productImg1);
        productImgList.add(productImg2);
        int effectedNum = productImgDao.batchInsertProductImg(productImgList);
        assertEquals(2, effectedNum);
        Product product = productDao.queryProductById(productId);
        assertEquals(2, product.getProductImgList().size());
        effectedNum = productImgDao.deleteProductImgByProductId(productId);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testDUpdateProduct() throws Exception {
        Product product = new Product();
        product.setProductId(1L);
        product.setProductName("第一个产品");
        Shop shop = new Shop();
        shop.setShopId(1L);
        product.setShop(shop);
        int effectedNum = productDao.updateProduct(product);
        assertEquals(1, effectedNum);
    }

}
