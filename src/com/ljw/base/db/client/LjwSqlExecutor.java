package com.ljw.base.db.client;

import com.ljw.base.db.pojo.SqlModel;
import com.ljw.base.ioc.annotation.Autowired;
import com.ljw.base.ioc.annotation.Bean;
import com.ljw.base.util.ClassUtil;
import com.ljw.base.util.CollectionUtil;
import com.ljw.base.util.DbHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/31 13:44
 */
@Bean(name = "sqlExecutor")
public class LjwSqlExecutor implements SqlExecutor {
    private SqlClient sqlClient = new SqlClient();

    @Override
    public int insert(String sql, Object obj) {
        return update(sql, obj);
    }

    @Override
    public Long insert(String sql, Object obj, String after) {
        if (update(sql, obj) == 0) {
            return null;
        }
        Map<String, Object> map = this.queryForObject(after, Map.class);
        if (map == null || map.isEmpty()) {
            throw new RuntimeException(after + "语句未查询到自增主键的值,请检查sql或数据库设置");
        }
        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        Map.Entry<String, Object> entry = iterator.next();
        ClassUtil.setFieldValueByName(obj, ClassUtil.getFiledMapByClass(obj.getClass()).get(entry.getKey()), entry.getKey(), entry.getValue());
        return Long.valueOf(entry.getValue().toString());
    }

    @Override
    public int update(String sql, Object obj) {
        SqlModel sqlModel = sqlClient.analyticalSql(sql, "#");
        Connection con = DbHelper.getTransactionConnection();
        boolean isTransaction = true;
        if (con == null) {
            con = DbHelper.getConnection();
            isTransaction = false;
        }
        try {
            PreparedStatement ps = con.prepareStatement(sqlModel.getSql());
            setPreparedStatement(ps, sqlModel, obj);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (!isTransaction) {
                DbHelper.close(con);
            }
        }
        return 0;
    }

    @Override
    public int delete(String sql, Object obj) {
        return update(sql, obj);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> clazz) {
        return queryForList(sql, clazz, null);
    }

    @Override
    public <T> List<T> queryForList(String sql, Class<T> clazz, Object obj) {
        SqlModel sqlModel = sqlClient.analyticalSql(sql, "#");
        Connection con = DbHelper.getTransactionConnection();
        boolean isTransaction = true;
        if (con == null) {
            con = DbHelper.getConnection();
            isTransaction = false;
        }
        List<T> list = null;
        try {
            PreparedStatement ps = con.prepareStatement(sqlModel.getSql());
            setPreparedStatement(ps, sqlModel, obj);
            ResultSet rs = ps.executeQuery();
            list = getResultList(rs, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!isTransaction) {
                DbHelper.close(con);
            }
        }
        return list;
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> clazz) {
        return queryForObject(sql, clazz, null);
    }

    @Override
    public <T> T queryForObject(String sql, Class<T> clazz, Object obj) {
        List<T> list = queryForList(sql, clazz, obj);
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        if (list.size() > 1) {
            throw new RuntimeException("查询返回行数大于1");
        }
        return list.get(0);
    }

    @Override
    public <T> List<T> queryForLimit(String sql, Class<T> clazz, int start, int end) {
        return queryForLimit(sql, clazz, null, start, end);
    }

    @Override
    public <T> List<T> queryForLimit(String sql, Class<T> clazz, Object obj, int start, int end) {
        if (start > end) {
            throw new RuntimeException("分页语句错误:起始位的值不能超过截止位");
        }
        sqlClient.limitSql(sql, start, end);
        return queryForList(sql, clazz, obj);
    }

    /**
     * 填充Sql条件
     */
    private void setPreparedStatement(PreparedStatement ps, SqlModel sqlModel, Object obj) throws SQLException {
        if (obj == null) {
            return;
        }
        if (ClassUtil.isPrimitive(obj) && CollectionUtil.getSubCount(sqlModel.getSql(), "?") == 1) {
            if (CollectionUtil.getSubCount(sqlModel.getSql(), "?") == 1) {
                ps.setObject(1, obj);
            } else {
                throw new RuntimeException("sql语句:" + sqlModel.getSql() + "格式不正确");
            }
        } else if (obj instanceof Map) {
            for (Map.Entry<Integer, String> entry : sqlModel.getProperties().entrySet()) {
                ps.setObject(entry.getKey(), ((Map) obj).get(entry.getValue()));
            }
        } else {
            for (Map.Entry<Integer, String> entry : sqlModel.getProperties().entrySet()) {
                ps.setObject(entry.getKey(), ClassUtil.getFieldValueByName(entry.getValue(), obj));
            }
        }
    }

    /**
     * 封装结果集到指定对象的list
     */
    private <T> List<T> getResultList(ResultSet rs, Class<T> clazz) throws Exception {
        List<T> list = new ArrayList<>();
        if (ClassUtil.isPrimitive(clazz)) {
            return getOnlyResult(rs, clazz);
        }
        List<Map<String, Object>> resultMapList = getResultMapList(rs);
        if (clazz == null || clazz.equals(Map.class)) {
            return (List<T>) resultMapList;
        }
        Map<String, Class<?>> filedMap = ClassUtil.getFiledMapByClass(clazz);
        for (Map<String, Object> map : resultMapList) {
            T obj = clazz.getConstructor().newInstance();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                ClassUtil.setFieldValueByName(obj, filedMap.get(entry.getKey()), entry.getKey(), map.get(entry.getKey()));
            }
            list.add(obj);
        }
        return list;
    }

    /**
     * 封装结果集到List<Map>集合 将列名转换为对象属性名
     */
    private List<Map<String, Object>> getResultMapList(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        //获取键名
        ResultSetMetaData md = rs.getMetaData();
        //获取行的数量
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>(16);
            for (int i = 1; i <= columnCount; i++) {
                //获取键名及值
                rowData.put(sqlClient.column2Property(md.getColumnLabel(i)), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    /**
     * 获取基本类型的唯一结果
     *
     * @param rs
     * @param clazz
     * @param <T>
     * @return
     * @throws SQLException
     */
    private <T> List<T> getOnlyResult(ResultSet rs, Class<T> clazz) throws SQLException {
        List<T> list = new ArrayList<>();
        //获取键名
        ResultSetMetaData md = rs.getMetaData();
        //获取行的数量
        int columnCount = md.getColumnCount();
        if (columnCount > 1) {
            throw new RuntimeException("查询返回行数大于1");
        }
        while (rs.next()) {
            list.add((T) rs.getObject(1));
        }
        return list;
    }
}
