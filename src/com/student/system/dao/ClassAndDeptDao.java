package com.student.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClassAndDeptDao {
    private Connection connection;

    public ClassAndDeptDao() {
        connection = DatabaseService.getInstance().getConnection();
    }


    public int createClass(String className, int owner) {
        try (PreparedStatement statement = connection.prepareStatement("insert into CLAZZ VALUES (?,?,?)");
             PreparedStatement statement1 = connection.prepareStatement("select CLASS_ID.nextval from dual");
             ResultSet set = statement1.executeQuery()) {
            set.next();
            int result = set.getInt(0);
            statement.setInt(1, result);
            statement.setString(2, className);
            statement.setInt(3, owner);
            if (statement.execute()) {
                return result;
            } else
                return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public boolean deleteClass(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CLAZZ WHERE ID = ?")) {
            statement.setInt(1, id);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateClass(int id, String name) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE CLAZZ SET NAME = ? WHERE ID = ?")) {
            statement.setInt(2, id);
            statement.setString(1, name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int createDept(String dept_Name) {
        int currID = 0;
        try (PreparedStatement statement = connection.prepareStatement("INSERT  INTO DEPT VALUES(?,?) ");
             PreparedStatement statement1 = connection.prepareStatement("select department_ID.nextval from dual");
             ResultSet resultSet = statement1.executeQuery()) {
            resultSet.next();
            currID = resultSet.getInt(1);
            statement.setInt(1, currID);
            statement.setString(2, dept_Name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currID;
    }


    public boolean updateDept(int id, String name) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE DEPT SET NAME = ? WHERE ID = ?")) {
            statement.setInt(2, id);
            statement.setString(1, name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteDept(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM DEPT WHERE ID = ?")) {
            statement.setInt(1, id);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getDeptInfo(int dept_ID) {
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM DEPT WHERE DEPT.ID = ?")) {
            statement.setInt(1, dept_ID);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return Integer.toString(dept_ID) + "---" + set.getString(2);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getClassInfo(int classID) {
        try (PreparedStatement statement = connection.prepareStatement("select CLAZZ.ID,CLAZZ.NAME,TEACHERS.NAME from CLAZZ,TEACHERS WHERE CLAZZ.ID = ? AND CLAZZ.OWNER = TEACHERS.ID")) {
            statement.setInt(1, classID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return String.format("班级ID：%d，班级名称：%s，负责老师：%s", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3));
            } else
                return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
