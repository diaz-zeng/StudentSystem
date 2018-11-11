package com.student.system.dao;

import com.student.system.model.Student;

import java.sql.*;

public final class DatabaseService {
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败！！！");
        }
    }

    ;
    private static final String url = "jdbc:oracle:thin:@192.168.31.4:1521:XE";
    private static final String user = "studentsystem";
    private static final String password = "1234";
    private static DatabaseService instance = null;
    private Connection connection = null;

    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private DatabaseService() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库驱动成功");
        } catch (SQLException e) {
            System.err.println("数据库连接失败");
            System.exit(0);
        }
    }

    public Student insertToStudent(Student student) {
        Student result;
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STUDENTS VALUES(?,?,?,?,?)");
             PreparedStatement preparedStatement1 = connection.prepareStatement("select STUDENT_ID.nextval from dual");
             ResultSet resultSet = preparedStatement1.executeQuery()) {
            resultSet.next();
            int currID = resultSet.getInt(1);
            student.setID(currID);
            preparedStatement.setInt(1,currID);
            preparedStatement.setString(2,student.getName());
            preparedStatement.setString(3,student.getGender());
            preparedStatement.setInt(4,student.getAge());
            preparedStatement.setInt(5,student.getClazz());
            if(preparedStatement.execute())
                return student;
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
