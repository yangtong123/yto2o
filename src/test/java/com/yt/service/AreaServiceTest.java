package com.yt.service;

import com.yt.BaseTest;
import com.yt.entity.Area;
import com.yt.service.AreaService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:35
 */
public class AreaServiceTest extends BaseTest {

    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        assertEquals("关山口", areaList.get(0).getAreaName());
    }


}
