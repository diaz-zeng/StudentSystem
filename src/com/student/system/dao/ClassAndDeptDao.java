package com.student.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 本类提供了对于Class表以及Dept表的操作
 *
 * @author Diaz
 * @version 1
 * @since 2018-11-11
 */
public class ClassAndDeptDao {
    private Connection connection;

    public ClassAndDeptDao() {
        connection = DatabaseService.getInstance().getConnection();
    }


    /**
     * 创建一个班级到数据库
     *
     * @param className 班级名，唯一约束
     * @param owner     辅导老师的ID
     * @return 班级的ID，由数据库序列CLASS_ID生成
     */
    public int createClass(String className, int owner) {
        try (PreparedStatement statement = connection.prepareStatement("insert into CLAZZ VALUES (?,?,?)");
             PreparedStatement statement1 = connection.prepareStatement("select CLASS_ID.nextval from dual");
             ResultSet set = statement1.executeQuery()) {
            set.next();
            int result = set.getInt(1);
            statement.setInt(1, result);
            statement.setString(2, className);
            statement.setInt(3, owner);
            if (statement.executeUpdate() > 0) {
                return result;
            } else
                return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * 删除一个班级，需要先使没有学生属于该班级
     *
     * @param id 班级编号
     * @return 是否成功
     */
    public boolean deleteClass(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM CLAZZ WHERE ID = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 更新班级信息，暂时还没用到
     *
     * @param id   班级ID
     * @param name 更新后的名字
     * @return 是否成功
     */
    public boolean updateClass(int id, String name) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE CLAZZ SET NAME = ? WHERE ID = ?")) {
            statement.setInt(2, id);
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建一个部门
     *
     * @param dept_Name 部门名称，受唯一约束
     * @return 部门的ID，由数据库序列DEPARTMENT_ID生成
     */
    public int createDept(String dept_Name) {
        int currID = 0;
        try (PreparedStatement statement = connection.prepareStatement("INSERT  INTO DEPT VALUES(?,?) ");
             PreparedStatement statement1 = connection.prepareStatement("select department_ID.nextval from dual");
             ResultSet resultSet = statement1.executeQuery()) {
            resultSet.next();
            currID = resultSet.getInt(1);
            statement.setInt(1, currID);
            statement.setString(2, dept_Name);
            statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currID;
    }

    /**
     * 更新部门名，暂时也没用
     *
     * @param id   部门ID
     * @param name 更新后的部门名
     * @return 是否成功
     */
    public boolean updateDept(int id, String name) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE DEPT SET NAME = ? WHERE ID = ?")) {
            statement.setInt(2, id);
            statement.setString(1, name);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除一个部门
     *
     * @param id 部门ID
     * @return 是否成功
     */
    public boolean deleteDept(int id) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM DEPT WHERE ID = ?")) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取部门下所有的教师信息
     *
     * @param dept_ID 部门ID
     * @return 查询结果，一个教师类型集合
     */
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


    /**
     * 获取班级信息
     *
     * @param classID 班级编号
     * @return 班级信息
     */
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
