package com.yt.service.impl;

import com.yt.dao.AreaDao;
import com.yt.entity.Area;
import com.yt.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/2/22 22:32
 */

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaDao areaDao;

    @Override
    public List<Area> getAreaList() {
        return areaDao.queryArea();
    }
}
