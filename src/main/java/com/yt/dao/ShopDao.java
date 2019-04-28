package com.yt.dao;

import com.yt.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/24 21:44
 */
public interface ShopDao {

    /**
     * 新增店铺
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);


    /**
     * 通过店铺 ID 查询
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);

    /**
     * 分页查询店铺
     * @param shopCondition
     * @param rowIndex 从第几行开始
     * @param pageSize 返回的条数
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition, @Param("rowIndex") int rowIndex,
                             @Param("pageSize") int pageSize);

    /**
     * 返回 queryShopList 总数
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);
}
