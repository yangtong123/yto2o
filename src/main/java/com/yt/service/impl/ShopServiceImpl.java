package com.yt.service.impl;

import com.yt.dao.ShopDao;
import com.yt.dto.ImageHolder;
import com.yt.dto.ShopExecution;
import com.yt.entity.Shop;
import com.yt.enums.ShopStateEnum;
import com.yt.exceptions.ShopOperationException;
import com.yt.service.ShopService;
import com.yt.util.ImageUtil;
import com.yt.util.PageCalculator;
import com.yt.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Date;
import java.util.List;


/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/25 23:20
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        } else {
            try {
                // 1. 判断是否需要修改图片
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop, thumbnail);
                }
                // 2. 更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modify shop error: " + e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {

        // 空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try {
            // 给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            // 添加店铺信息
            int effectNum = shopDao.insertShop(shop);
            if (effectNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    // 存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("addShopImg error:" + e.getMessage());
                    }
                    // 更新店铺的图片地址
                    effectNum = shopDao.updateShop(shop);
                    if (effectNum <= 0) {
                        throw new ShopOperationException("更新图片地址失败");
                    }
                }
            }

        } catch (Exception e) {
            throw new ShopOperationException("addShop error: " + e.getMessage());
        }

        return new ShopExecution(ShopStateEnum.CHECK, shop);

    }

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex, pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        shop.setShopImg(shopImgAddr);
    }
}
