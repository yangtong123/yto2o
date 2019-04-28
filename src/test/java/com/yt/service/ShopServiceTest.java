package com.yt.service;

import com.yt.BaseTest;
import com.yt.dto.ImageHolder;
import com.yt.dto.ShopExecution;
import com.yt.entity.Area;
import com.yt.entity.PersonInfo;
import com.yt.entity.Shop;
import com.yt.entity.ShopCategory;
import com.yt.enums.ShopStateEnum;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/26 0:06
 */
public class ShopServiceTest extends BaseTest {
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList() {
        Shop shopCondition = new Shop();
        ShopCategory sc = new ShopCategory();
        sc.setShopCategoryId(1L);
        shopCondition.setShopCategory(sc);
        ShopExecution se = shopService.getShopList(shopCondition, 1, 2);
        System.out.println("店铺列表数: " + se.getShopList().size());
        System.out.println("店铺总数为：" + se.getCount());
    }

    @Test
    @Ignore
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = new Shop();
        shop.setShopId(2L);
        shop.setShopName("修改后的店铺名称");
        File shopImg = new File("E:\\IdeaProject\\yto2o\\image\\tesla.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder("new.jpg", is);
        ShopExecution shopExecution = shopService.modifyShop(shop, thumbnail);
        System.out.println("新的图片地址：" + shopExecution.getShop().getShopImg());
    }


    @Test
    @Ignore
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);
        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);
        shop.setShopName("测试的店铺4");
        shop.setShopDesc("test3");
        shop.setShopAddr("test3");
        shop.setPhone("test3");
        shop.setShopImg("test3");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("E:\\IdeaProject\\yto2o\\image\\ironman.jpg");
        InputStream shopImgInputStream = new FileInputStream(shopImg);
        ImageHolder thumbnail = new ImageHolder(shopImg.getName(), shopImgInputStream);
        ShopExecution shopExecution = shopService.addShop(shop, thumbnail);

        assertEquals(ShopStateEnum.CHECK.getState(), shopExecution.getState());
    }
}
