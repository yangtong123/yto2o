package com.yt.service;

import com.yt.dto.ImageHolder;
import com.yt.dto.ShopExecution;
import com.yt.entity.Shop;
import com.yt.exceptions.ShopOperationException;

import java.io.InputStream;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/25 23:11
 */
public interface ShopService {

    /**
     * 根据 ShopId 获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 更新店铺信息，包括图片的处理
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 注册店铺信息，包括图片处理
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;

    /**
     * 根据 shopCondition 分页返回相应列表数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
}
