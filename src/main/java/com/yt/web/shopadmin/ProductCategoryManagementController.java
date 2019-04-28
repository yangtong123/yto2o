package com.yt.web.shopadmin;

import com.yt.dto.ProductCategoryExecution;
import com.yt.dto.Result;
import com.yt.entity.ProductCategory;
import com.yt.entity.Shop;
import com.yt.enums.ProductCategoryStateEnum;
import com.yt.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/8 0:23
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
        }
    }

    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }

        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution productCategoryExecution = productCategoryService.batchAddProductCategory(productCategoryList);
                processProductCategoryExecution(productCategoryExecution, modelMap);
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }

        return modelMap;
    }

    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId, HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                ProductCategoryExecution productCategoryExecution = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
                processProductCategoryExecution(productCategoryExecution, modelMap);
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确的商品类别编号");
        }

        return modelMap;
    }

    private void processProductCategoryExecution(ProductCategoryExecution productCategoryExecution, Map<String, Object> modelMap) {
        if (productCategoryExecution.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", productCategoryExecution.getStateInfo());
        }
    }
}
