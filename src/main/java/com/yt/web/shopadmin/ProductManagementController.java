package com.yt.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yt.dto.ImageHolder;
import com.yt.dto.ProductExecution;
import com.yt.entity.Product;
import com.yt.entity.Shop;
import com.yt.enums.ProductStateEnum;
import com.yt.exceptions.ProductOperationException;
import com.yt.service.ProductService;
import com.yt.util.CodeUtil;
import com.yt.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/16 22:13
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;

    private static final int IMAGE_MAX_COUNT = 6;

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
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());

        try {
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());

                for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        ImageHolder productImg = new ImageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
                        productImgList.add(productImg);
                    } else {
                        break;
                    }
                }
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
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);

                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);

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
}
