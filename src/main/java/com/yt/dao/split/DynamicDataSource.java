package com.yt.dao.split;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/3/20 0:03
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DynamicDataSourceHolder.getDbType();
    }
}
