package com.yt.dao;

import com.yt.entity.Product;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/9 14:55
 */
public interface ProductDao {

    /**
     * 插入商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 更新商品信息
     *
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品类别之前，将商品类别ID置为空
     *
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);
}
