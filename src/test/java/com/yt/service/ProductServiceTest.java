package com.yt.service;

import com.yt.BaseTest;
import com.yt.dto.ImageHolder;
import com.yt.dto.ProductExecution;
import com.yt.entity.Product;
import com.yt.entity.ProductCategory;
import com.yt.entity.Shop;
import com.yt.enums.ProductStateEnum;
import com.yt.exceptions.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/16 21:40
 */
public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(3L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        File thumbnailFile = new File("C:\\Users\\dell\\Desktop\\15\\1.jpg");

        InputStream is = new FileInputStream(thumbnailFile);

        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        File productImg1 = new File("C:\\Users\\dell\\Desktop\\15\\2.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:\\Users\\dell\\Desktop\\15\\3.jpg");
        InputStream is2 = new FileInputStream(productImg2);

        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));

        ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);

        assert ProductStateEnum.SUCCESS.getState() == pe.getState();
    }

    @Test
    public void testModifyProduct() throws ShopOperationException, FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(1L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(3L);
        product.setProductId(1L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("正式的商品");
        product.setProductDesc("正式的商品");

        File thumbnailFile = new File("C:\\Users\\dell\\Desktop\\15\\1.jpg");
        InputStream is = new FileInputStream(thumbnailFile);
        ImageHolder thumbnail = new ImageHolder(thumbnailFile.getName(), is);

        File productImg1 = new File("C:\\Users\\dell\\Desktop\\15\\2.jpg");
        InputStream is1 = new FileInputStream(productImg1);
        File productImg2 = new File("C:\\Users\\dell\\Desktop\\15\\3.jpg");
        InputStream is2 = new FileInputStream(productImg2);
        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(productImg1.getName(), is1));
        productImgList.add(new ImageHolder(productImg2.getName(), is2));

        ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());

    }
}
