package com.yt.exceptions;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/4/14 17:16
 */
public class ProductOperationException extends RuntimeException {


    public ProductOperationException(String msg) {
        super(msg);
    }
}
