package com.student.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CourseAndResultsDao {

    private Connection connection;

    public CourseAndResultsDao() {
        connection = DatabaseService.getInstance().getConnection();
    }


    public boolean createCourse(String courseID, String courseName, int owner) {
        try (PreparedStatement statement = connection.prepareStatement("insert into COURSE VALUES (?,?,?)")) {
            statement.setString(1, courseID);
            statement.setString(2, courseName);
            statement.setInt(3, owner);
            if (!statement.execute()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteCourse(String courseID){
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM COURSE WHERE ID = ?")){
            statement.setString(1,courseID);
            return statement.execute();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

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

    public boolean putResults(int studentID, String courseID, int results) {
        try (PreparedStatement statement = connection.prepareStatement("insert into RESULTS VALUES (?,?,?)")) {
            if (results >= 0 && results <= 100) {
                statement.setInt(1, studentID);
                statement.setString(2, courseID);
                statement.setInt(3, results);
                if (statement.execute()) {
                    return true;
                }
            }


        } catch (Exception e) {
            return false;
        }
        return false;
    }

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
