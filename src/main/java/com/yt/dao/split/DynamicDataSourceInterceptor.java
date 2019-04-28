package com.yt.dao.split;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.omg.CORBA.portable.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Locale;
import java.util.Properties;

/**
 * @author yangtong.hust@gmail.com
 * @date 2019/3/20 0:11
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
             @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(DynamicDataSourceInterceptor.class);

    private static final String REGEX = ".*insert\\u0020.*|.*delete\\u0020.*|.*update\\u0020.*";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        // 是否使用事务管理
        boolean synchronizationActive = TransactionSynchronizationManager.isActualTransactionActive();

        Object[] objects = invocation.getArgs();
        MappedStatement ms = (MappedStatement) objects[0];
        String lookupkey = DynamicDataSourceHolder.DB_MASTER;

        if (!synchronizationActive) { // 不适用事务管理
            // 读方法
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                // selectKey 为自增 id 查询主键 (SELECT LAST_INSERT_ID()) 方法，使用主库
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    lookupkey = DynamicDataSourceHolder.DB_MASTER;
                } else {
                    BoundSql boundSql = ms.getSqlSource().getBoundSql(objects[1]);
                    String sql = boundSql.getSql().toLowerCase(Locale.CHINA).replaceAll("[\\t\\n\\r]", " ");
                    logger.debug("执行的 SQL 语句为：" + sql);
                    if (sql.matches(REGEX)) {  // 如果是写方法
                        lookupkey = DynamicDataSourceHolder.DB_MASTER;
                    } else {
                        lookupkey = DynamicDataSourceHolder.DB_SLAVE;
                    }
                }
            }
        } else { // 使用事务管理的
            lookupkey=DynamicDataSourceHolder.DB_MASTER;
        }
        logger.debug("设置方法[{}] use [{}] Strategy, SqlCommandType [{}]..", ms.getId(), lookupkey, ms.getSqlCommandType().name());
        DynamicDataSourceHolder.setDbType(lookupkey);
        return invocation.proceed();
    }

    /**
     * 拦截的对象有增删改查就拦截下来，返回的是本体还是编织好的代理
     * @param o
     * @return
     */
    @Override
    public Object plugin(Object o) {
        if (o instanceof Executor) {
            return Plugin.wrap(o, this);
        } else {
            return o;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
