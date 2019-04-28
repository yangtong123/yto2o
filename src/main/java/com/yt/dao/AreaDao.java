package com.yt.dao;

import com.yt.entity.Area;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:09
 */
public interface AreaDao {

    /**
     * 列出区域列表
     * @return List<Area>
     */
    List<Area> queryArea();
}
