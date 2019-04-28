package com.yt.service.impl;

import com.yt.dao.ShopCategoryDao;
import com.yt.entity.ShopCategory;
import com.yt.service.ShopCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/3/18 22:19
 */
@Service
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition) {
        return shopCategoryDao.queryShopCategory(shopCategoryCondition);
    }
}
