package com.yt.dao;

import com.yt.entity.ProductImg;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/9 14:57
 */
public interface ProductImgDao {

    /**
     * 批量添加商品详情图片
     *
     * @param productImgList
     * @return
     */
    int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 通过商品 ID 查询商品图片
     * @param productId
     * @return
     */
    List<ProductImg> queryProductImgList(Long productId);

    /**
     * 通过商品 ID 删除商品图片
     * @param productId
     * @return
     */
    int deleteProductImgByProductId(Long productId);
}
