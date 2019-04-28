package com.yt.service;

import com.yt.BaseTest;
import com.yt.entity.Area;
import com.yt.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:35
 */
public class ShopCategoryServiceTest extends BaseTest {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void testGetAreaList() {
        List<ShopCategory> list = shopCategoryService.getShopCategoryList(new ShopCategory());
        assertEquals(2, list.size());
    }


}
