package com.yt.dao;

import com.yt.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/3/18 21:56
 */
public interface ShopCategoryDao {
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
