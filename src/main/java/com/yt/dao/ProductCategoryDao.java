package com.yt.dao;

import com.yt.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/7 22:56
 */
public interface ProductCategoryDao {

    /**
     * 通过 shopId 查询店铺商品类别
     * @param shopId
     * @return
     */
    List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增商品类别
     * @param productCategoryList
     * @return
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除指定商品类别
     * @param productCategoryId
     * @return
     */
    int deleteProductCategory(@Param("productCategoryId") long productCategoryId, @Param("shopId") long shopId);
}
