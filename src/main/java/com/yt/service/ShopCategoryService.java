package com.yt.service;

import com.yt.entity.Area;
import com.yt.entity.ShopCategory;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:31
 */
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
