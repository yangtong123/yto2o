package com.yt.service;

import com.yt.dto.ProductCategoryExecution;
import com.yt.entity.ProductCategory;
import com.yt.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/7 23:08
 */
public interface ProductCategoryService {

    /**
     * 查询某个店铺下的所有商品类别信息
     *
     * @param shopId
     * @return
     */
    List<ProductCategory> getProductCategoryList(Long shopId);

    /**
     * 批量插入
     * @param productCategoryList
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList)
            throws ProductCategoryOperationException;

    /**
     * 将此类别下的商品里的类别 id 置为空，再删除掉该商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(Long productCategoryId, Long shopId)
            throws ProductCategoryOperationException;
}
