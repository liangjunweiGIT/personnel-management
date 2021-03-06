package com.ljw.base.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author Created by junwei.liang on 2018/10/30 17:12
 */
public class DbHelper {
    /**
     * 核心连接数
     */
    private static final int CORE_SIZE = Integer.parseInt(PropertiesUtil.getString("CORE_SIZE"));
    /**
     * 最大连接数
     */
    private static final int MAX_SIZE = Integer.parseInt(PropertiesUtil.getString("MAX_SIZE"));
    /**
     * 线程安全的（同步）
     */
    private static final List<Connection> POOL = Collections.synchronizedList(new ArrayList<>());
    /**
     * 当前总连接数(原子性int)
     */
    private static final AtomicInteger CURRENT_POOL_SIZE = new AtomicInteger(0);
    /**
     * 存放本地线程变量 用于共享Connection的事务
     */
    private static final ThreadLocal<Connection> THREAD_LOCAL = new ThreadLocal<>();
    /**
     * 存放是否可以回收事务连接的标记 每在一个事务的方法里嵌套一个事务方法标记就会加一 当标记为零时表示可以回收
     */
    private static final ThreadLocal<AtomicInteger> THREAD_LOCAL_FLAG = new ThreadLocal<>();

    static {
        try {
            Class.forName(PropertiesUtil.getString("DRIVER"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < CORE_SIZE; i++) {
            POOL.add(DbHelper.createConnection());
        }
    }

    private static Connection createConnection() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(PropertiesUtil.getString("URL"),
                    PropertiesUtil.getString("USER"),
                    PropertiesUtil.getString("PASSWORD"));
            CURRENT_POOL_SIZE.getAndIncrement();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return con;
    }

    @Deprecated
    private static void realClose(Connection con) {
        try {
            con.close();
            CURRENT_POOL_SIZE.getAndDecrement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从连接池获取一个连接
     */
    public static synchronized Connection getConnection() {
        if (POOL.size() > 0) {
            return POOL.remove(0);
        } else if (CURRENT_POOL_SIZE.intValue() < MAX_SIZE) {
            return createConnection();
        } else {
            throw new RuntimeException("池中无连接");
        }
    }

    /**
     * 还回一个连接到池中
     */
    public static void close(Connection con) {
        POOL.add(con);
    }

    public static void beginTransaction() throws SQLException {
        if (THREAD_LOCAL.get() == null) {
            Connection connection = getConnection();
            connection.setAutoCommit(false);
            THREAD_LOCAL.set(connection);
        } else {
            if (THREAD_LOCAL_FLAG.get() == null) {
                THREAD_LOCAL_FLAG.set(new AtomicInteger(0));
            } else {
                THREAD_LOCAL_FLAG.get().getAndIncrement();
            }
        }
    }

    public static Connection getTransactionConnection() {
        return THREAD_LOCAL.get();
    }

    public static void commit() throws SQLException {
        THREAD_LOCAL.get().commit();
    }

    public static void rollback() throws SQLException {
        THREAD_LOCAL.get().rollback();
    }

    public static void closeForTransaction() throws SQLException {
        if (THREAD_LOCAL_FLAG.get() == null || THREAD_LOCAL_FLAG.get().intValue() == 0) {
            Connection connection = THREAD_LOCAL.get();
            THREAD_LOCAL.remove();
            connection.setAutoCommit(true);
            close(connection);
        } else {
            THREAD_LOCAL_FLAG.get().getAndDecrement();
        }
    }


}
