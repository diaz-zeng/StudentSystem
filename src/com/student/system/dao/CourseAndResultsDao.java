package com.student.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * 本类提供了对于Course表以及Results表的操作
 *
 * @author Diaz
 * @since 2018-11-11
 * @version 1
 */
public class CourseAndResultsDao {

    private Connection connection;

    public CourseAndResultsDao() {
        connection = DatabaseService.getInstance().getConnection();
    }


    /**
     *
     * 创建一个课程
     *
     * @param courseID 课程ID
     * @param courseName 课程名
     * @param owner 辅导老师的ID
     * @return 是否创建成功
     */
    public boolean createCourse(String courseID, String courseName, int owner) {
        try (PreparedStatement statement = connection.prepareStatement("insert into COURSE VALUES (?,?,?)")) {
            statement.setString(1, courseID);
            statement.setString(2, courseName);
            statement.setInt(3, owner);
            if (statement.executeUpdate()>0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 删除一门课程，在数据库中设置了级联删除，会删除该课程下所有的成绩数据
     * @param courseID 课程ID
     * @return 是否成功
     */
    public boolean deleteCourse(String courseID){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM COURSE WHERE ID = ?")){
            statement.setString(1,courseID);
            return statement.executeUpdate()>0;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取选择了某门课程的所有学生信息以及该课程的信息
     *
     * @param courseID 要查询的课程ID
     * @return 查询结果，返回一个学生类型的集合
     */
    public String getCourseInfo(String courseID) {
        try (PreparedStatement statement = connection.prepareStatement("select COURSE.ID,COURSE.NAME,TEACHERS.NAME from COURSE,TEACHERS WHERE COURSE.ID = ? AND COURSE.OWNER = TEACHERS.ID ")) {
            statement.setString(1, courseID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return String.format("课程代号：%s，课程名称：%s，负责老师：%s", resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 添加一条成绩
     *
     * @param studentID 学号
     * @param courseID 课程ID
     * @param results 成绩
     * @return 是否成功
     */
    public boolean putResults(int studentID, String courseID, int results) {
        try (PreparedStatement statement = connection.prepareStatement("insert into RESULTS VALUES (?,?,?)")) {
            if (results >= 0 && results <= 100) {
                statement.setInt(1, studentID);
                statement.setString(2, courseID);
                statement.setInt(3, results);
                if (statement.executeUpdate()>0) {
                    return true;
                }
            }


        } catch (Exception e) {
            return false;
        }
        return false;
    }

    /**
     *
     * 查询所有课程的成绩表
     * @return 查询结果
     */

    public ArrayList<String> getResults() {
        ArrayList<String> results = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select RESULTS.STU_ID , STUDENTS.NAME, RESULTS.COURSE_ID,COURSE.NAME, RESULTS.RESULTS FROM RESULTS,STUDENTS,COURSE where RESULTS.STU_ID = STUDENTS.ID and  RESULTS.COURSE_ID = COURSE.ID")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                results.add(String.format("学号%d：，姓名%s：，课程代号%s：，课程名称%s：，分数%d：", resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getInt(5)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
}
