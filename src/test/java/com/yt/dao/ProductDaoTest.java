package com.yt.dao;

import com.yt.BaseTest;
import com.yt.entity.Product;
import com.yt.entity.ProductCategory;
import com.yt.entity.Shop;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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



}
