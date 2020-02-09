package com.yt.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yt.dto.ImageHolder;
import com.yt.dto.ProductExecution;
import com.yt.entity.Product;
import com.yt.entity.ProductCategory;
import com.yt.entity.Shop;
import com.yt.enums.ProductStateEnum;
import com.yt.exceptions.ProductOperationException;
import com.yt.service.ProductCategoryService;
import com.yt.service.ProductService;
import com.yt.util.CodeUtil;
import com.yt.util.HttpServletRequestUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/16 22:13
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

  private static final int IMAGE_MAX_COUNT = 6;
  @Autowired
  private ProductService productService;
  @Autowired
  private ProductCategoryService productCategoryService;

  @RequestMapping(value = "/listproductsbyshop", method = RequestMethod.GET)
  @ResponseBody
  private Map<String, Object> listProductsByShop(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
    int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
    Shop currentShop = (Shop) request.getSession().getAttribute(
        "currentShop");
    if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null)
        && (currentShop.getShopId() != null)) {
      long productCategoryId = HttpServletRequestUtil.getLong(request,
          "productCategoryId");
      String productName = HttpServletRequestUtil.getString(request,
          "productName");
      Product productCondition = this.compactProductCondition4Search(
          currentShop.getShopId(), productCategoryId, productName);
      ProductExecution pe = this.productService.getProductList(
          productCondition, pageIndex, pageSize);
      modelMap.put("productList", pe.getProductList());
      modelMap.put("count", pe.getCount());
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
    }
    return modelMap;
  }

  /**
   *
   */
  @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
  @ResponseBody
  private Map<String, Object> getProductById(@RequestParam Long productId) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    if (productId > -1) {
      Product product = this.productService.getProductById(productId);
      List<ProductCategory> productCategoryList = this.productCategoryService
          .getProductCategoryList(product.getShop().getShopId());
      modelMap.put("product", product);
      modelMap.put("productCategoryList", productCategoryList);
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
    }
    return modelMap;
  }

  @RequestMapping(value = "/getproductcategorylistbyshopId", method = RequestMethod.GET)
  @ResponseBody
  private Map<String, Object> getProductCategoryListByShopId(
      HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<String, Object>();
    Shop currentShop = (Shop) request.getSession().getAttribute(
        "currentShop");
    if ((currentShop != null) && (currentShop.getShopId() != null)) {
      List<ProductCategory> productCategoryList = this.productCategoryService
          .getProductCategoryList(currentShop.getShopId());
      modelMap.put("productCategoryList", productCategoryList);
      modelMap.put("success", true);
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
    }
    return modelMap;
  }

  @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
  @ResponseBody
  private Map<String, Object> addProduct(HttpServletRequest request) {
    Map<String, Object> modelMap = new HashMap<>();

    if (!CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "输入了错误的验证码");
      return modelMap;
    }

    ObjectMapper mapper = new ObjectMapper();
    Product product = null;
    String productStr = HttpServletRequestUtil.getString(request, "productStr");
    MultipartHttpServletRequest multipartRequest = null;
    ImageHolder thumbnail = null;
    List<ImageHolder> productImgList = new ArrayList<>();
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
        request.getSession().getServletContext());

    try {
      if (multipartResolver.isMultipart(request)) {
        thumbnail = this.handleImage((MultipartHttpServletRequest) request, productImgList);
      } else {
        modelMap.put("success", false);
        modelMap.put("errMsg", "上传图片不能为空");
        return modelMap;
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    }

    try {
      product = mapper.readValue(productStr, Product.class);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    }

    if (product != null && thumbnail != null && productImgList.size() > 0) {
      try {
        // 从 session 中获取当前店铺的 Id 并赋值给 product, 减少对前端数据的依赖
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Shop shop = new Shop();
        shop.setShopId(currentShop.getShopId());
        product.setShop(shop);

        ProductExecution pe = this.productService
            .addProduct(product, thumbnail, productImgList);

        if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", pe.getStateInfo());
        }
      } catch (ProductOperationException e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请输入商品信息");
      return modelMap;
    }

    return modelMap;
  }

  @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
  @ResponseBody
  private Map<String, Object> modifyProduct(HttpServletRequest request) {
    boolean statusChange = HttpServletRequestUtil.getBoolean(request,
        "statusChange");
    Map<String, Object> modelMap = new HashMap<String, Object>();
    if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
      modelMap.put("success", false);
      modelMap.put("errMsg", "输入了错误的验证码");
      return modelMap;
    }

    ObjectMapper mapper = new ObjectMapper();
    Product product = null;

    MultipartHttpServletRequest multipartRequest = null;
    ImageHolder thumbnail = null;
    List<ImageHolder> productImgList = new ArrayList<>();
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
        request.getSession().getServletContext());

    try {
      if (multipartResolver.isMultipart(request)) {
        thumbnail = this.handleImage((MultipartHttpServletRequest) request, productImgList);
      }
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    }

    try {
      String productStr = HttpServletRequestUtil.getString(request, "productStr");
      product = mapper.readValue(productStr, Product.class);
    } catch (Exception e) {
      modelMap.put("success", false);
      modelMap.put("errMsg", e.toString());
      return modelMap;
    }

    if (product != null && thumbnail != null && productImgList.size() > 0) {
      try {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        Shop shop = new Shop();
        shop.setShopId(currentShop.getShopId());
        product.setShop(shop);

        ProductExecution pe = this.productService
            .modifyProduct(product, thumbnail, productImgList);

        if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
          modelMap.put("success", true);
        } else {
          modelMap.put("success", false);
          modelMap.put("errMsg", pe.getStateInfo());
        }
      } catch (ProductOperationException e) {
        modelMap.put("success", false);
        modelMap.put("errMsg", e.toString());
        return modelMap;
      }
    } else {
      modelMap.put("success", false);
      modelMap.put("errMsg", "请输入商品信息");
      return modelMap;
    }

    return modelMap;
  }

  private ImageHolder handleImage(MultipartHttpServletRequest request,
      List<ImageHolder> productImgList) throws IOException {
    MultipartHttpServletRequest multipartRequest;
    ImageHolder thumbnail;
    multipartRequest = request;
    CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest
        .getFile("thumbnail");
    thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(),
        thumbnailFile.getInputStream());

    for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
      CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest
          .getFile("productImg" + i);
      if (productImgFile != null) {
        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(),
            productImgFile.getInputStream());
        productImgList.add(productImg);
      } else {
        break;
      }
    }
    return thumbnail;
  }


  private Product compactProductCondition4Search(long shopId,
      long productCategoryId, String productName) {
    Product productCondition = new Product();
    Shop shop = new Shop();
    shop.setShopId(shopId);
    productCondition.setShop(shop);
    if (productCategoryId != -1L) {
      ProductCategory productCategory = new ProductCategory();
      productCategory.setProductCategoryId(productCategoryId);
      productCondition.setProductCategory(productCategory);
    }
    if (productName != null) {
      productCondition.setProductName(productName);
    }
    return productCondition;
  }
}
