package com.yt.util;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/3/27 23:02
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
