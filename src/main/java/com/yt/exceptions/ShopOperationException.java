package com.yt.exceptions;

import java.io.Serializable;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/25 23:48
 */
public class ShopOperationException extends RuntimeException {


    private static final long serialVersionUID = 2394573021242928961L;

    public ShopOperationException(String msg) {
        super(msg);
    }
}
