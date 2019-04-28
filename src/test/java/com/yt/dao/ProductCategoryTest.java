package com.yt.dao;

import com.yt.BaseTest;
import com.yt.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/7 23:04
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testBQueryByShopId() {
        long shopId = 1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("类别数为：" + productCategoryList.size());
    }

    @Test
    public void testABatchInsertProductCategory() {
        ProductCategory productCategory1 = new ProductCategory();
        productCategory1.setProductCategoryName("商品类别1");
        productCategory1.setPriority(1);
        productCategory1.setCreateTime(new Date());
        productCategory1.setShopId(1L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别2");
        productCategory2.setPriority(2);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(1L);
        List<ProductCategory> productCategoryList1 = new ArrayList<>();
        productCategoryList1.add(productCategory1);
        productCategoryList1.add(productCategory2);
        int effectedNum = productCategoryDao.batchInsertProductCategory(productCategoryList1);
        assertEquals(2, effectedNum);
    }

    @Test
    public void testCDeleteProductCategory() {
        long shopId = 1;
        List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategoryList(shopId);
        for (ProductCategory productCategory : productCategoryList) {
            if ("商品类别1".equals(productCategory.getProductCategoryName()) || "商品类别2".equals(productCategory.getProductCategoryName())) {
                int effectedNum = productCategoryDao.deleteProductCategory(productCategory.getProductCategoryId(), shopId);
                assertEquals(1, effectedNum);
            }
        }
    }
}
