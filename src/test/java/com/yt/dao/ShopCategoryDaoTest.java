package com.yt.dao;

import com.yt.BaseTest;
import com.yt.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/3/18 22:04
 */
public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());

        assertEquals(2, shopCategoryList.size());

        ShopCategory testCategory = new ShopCategory();
        ShopCategory parentCategory = new ShopCategory();
        parentCategory.setShopCategoryId(1L);
        testCategory.setParent(parentCategory);

        shopCategoryList = shopCategoryDao.queryShopCategory(testCategory);


        assertEquals(1, shopCategoryList.size());
    }
}
