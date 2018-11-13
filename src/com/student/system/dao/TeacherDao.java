package com.student.system.dao;

import com.student.system.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TeacherDao {
    private Connection connection;

    public TeacherDao() {
        connection = DatabaseService.getInstance().getConnection();
    }


    public Teacher createTeacher(Teacher teacher) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO TEACHERS VALUES(?,?,?,?)");
             PreparedStatement statement1 = connection.prepareStatement("select TEACHER_ID.nextval from dual");
             ResultSet resultSet = statement1.executeQuery()) {
            resultSet.next();
            int currID = resultSet.getInt(1);
            teacher.setID(currID);
            statement.setInt(1, currID);
            statement.setString(2, teacher.getName());
            statement.setInt(3, teacher.getDept_ID());
            statement.setString(4, teacher.getTitle());
            if (statement.executeUpdate()>0) {
                return teacher;
            } else return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public ArrayList<Teacher> getDetpInfo(int detp_ID) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT TEACHERS.* FROM TEACHERS WHERE TEACHERS.DEPT_ID = ? ")) {
            statement.setInt(1, detp_ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }


    public boolean updateTeacher(Teacher teacher) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE TEACHERS SET NAME = ?,TITLE = ?,DEPT_ID = ?  WHERE ID = ?")) {
            statement.setString(1, teacher.getName());
            statement.setString(2, teacher.getTitle());
            statement.setInt(3, teacher.getDept_ID());
            return statement.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public ArrayList<Teacher> getAllTeachers() {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEACHERS")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }


    public Teacher getTeacherByID(int ID) {
        Teacher teacher = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEACHERS WHERE TEACHERS.ID = ?")) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                teacher = new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getInt(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacher;
    }


    public ArrayList<Teacher> getTeacherByName(String name) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM TEACHERS WHERE TEACHERS.NAME = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }

    public boolean deleteTeacher(int teacherID) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM TEACHERS WHERE ID = ?")) {
            statement.setInt(1, teacherID);
            return statement.executeUpdate()>0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
