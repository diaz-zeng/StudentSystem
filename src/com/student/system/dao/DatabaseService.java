package com.student.system.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * 为程序全局提供数据库的链接对象，单例实现
 * @author Diaz
 * @since 2018-11-11
 * @version 1
 */
public final class DatabaseService {
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败！！！");
        }
    }

    private static final String url = "jdbc:oracle:thin:@192.168.31.4:1521:XE";
    private static final String user = "studentsystem";
    private static final String password = "1234";
    private static DatabaseService instance = null;
    private Connection connection = null;

    /**
     * 获取本类的唯一实例
     * @return 本类的唯一实例
     */
    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    /**
     *
     * 获取唯一数据库连接对象
     * @return 连接对象
     */

    public Connection getConnection() {
        return connection;
    }

    private DatabaseService() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            System.err.println("数据库连接失败");
            System.exit(0);
        }
    }


}
