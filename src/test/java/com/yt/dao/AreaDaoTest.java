package com.yt.dao;

import com.yt.BaseTest;
import com.yt.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:20
 */
public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea() {
        List<Area> areaList = areaDao.queryArea();
        assertEquals(2, areaList.size());
    }
}
