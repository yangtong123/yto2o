package com.yt.exceptions;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/25 23:48
 */
public class ProductCategoryOperationException extends RuntimeException {

    private static final long serialVersionUID = -4068343279294243418L;

    public ProductCategoryOperationException(String msg) {
        super(msg);
    }
}
