package com.yt.service;

import com.yt.dto.ImageHolder;
import com.yt.dto.ProductExecution;
import com.yt.entity.Product;
import com.yt.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/14 17:14
 */
public interface ProductService {


    /**
     *
     * @param product
     * @param thumbnail
     * @param productImgList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgList) throws ProductOperationException;
}
