package com.ljw.base.db;

import com.ljw.base.db.client.LjwSqlExecutor;
import com.ljw.base.db.client.SqlExecutor;
import com.ljw.base.ioc.annotation.Autowired;
import com.ljw.base.ioc.annotation.Bean;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/31 12:58
 */
public class BaseDao {
    @Resource(name = "sqlExecutor")
    private SqlExecutor sqlExecutor;

    protected int insert(String sql, Object obj) {
        return sqlExecutor.insert(sql, obj);
    }

    protected Long insert(String sql, Object obj, String after) {
        return sqlExecutor.insert(sql, obj, after);
    }

    protected int update(String sql, Object obj) {
        return sqlExecutor.update(sql, obj);
    }

    protected int delete(String sql, Object obj) {
        return sqlExecutor.delete(sql, obj);
    }

    protected <T> List<T> queryForList(String sql, Class<T> clazz) {
        return sqlExecutor.queryForList(sql, clazz);
    }

    protected <T> List<T> queryForList(String sql, Class<T> clazz, Object obj) {
        return sqlExecutor.queryForList(sql, clazz, obj);
    }

    protected <T> T queryForObject(String sql, Class<T> clazz) {
        return sqlExecutor.queryForObject(sql, clazz);
    }

    protected <T> T queryForObject(String sql, Class<T> clazz, Object obj) {
        return sqlExecutor.queryForObject(sql, clazz, obj);
    }

    protected <T> List<T> queryForLimit(String sql, Class<T> clazz, int start, int end) {
        return sqlExecutor.queryForLimit(sql, clazz, start, end);
    }

    protected <T> List<T> queryForLimit(String sql, Class<T> clazz, Object obj, int start, int end) {
        return sqlExecutor.queryForLimit(sql, clazz, obj, start, end);
    }

    protected String addIfNotNull(String sql, Object object, String addStr) {
        if (object != null) {
            sql += " " + addStr + " ";
        }
        return sql;
    }

    protected static String checkSql(String sql) {
        if (sql == null || sql.length() == 0) {
            return sql;
        }
        sql = sql.trim();
        if (sql.endsWith(",")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        return sql;
    }

}
