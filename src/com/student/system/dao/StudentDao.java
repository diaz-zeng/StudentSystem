package com.student.system.dao;

import com.student.system.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDao {
    private Connection connection;

    public StudentDao() {
        connection = DatabaseService.getInstance().getConnection();
    }


    public Student createStudent(Student student) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO STUDENTS VALUES(?,?,?,?,?)");
             PreparedStatement preparedStatement1 = connection.prepareStatement("select STUDENT_ID.nextval from dual");
             ResultSet resultSet = preparedStatement1.executeQuery()) {
            resultSet.next();
            int currID = resultSet.getInt(1);
            student.setID(currID);
            preparedStatement.setInt(1, currID);
            preparedStatement.setString(2, student.getName());
            preparedStatement.setString(3, student.getGender());
            preparedStatement.setInt(4, student.getAge());
            preparedStatement.setInt(5, student.getClazz());
            if (preparedStatement.execute())
                return student;
            else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public ArrayList<Student> getStudentOfClass(int classID) {
        ArrayList<Student> students = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE STUDENTS.CLAZZ = ?")) {
            statement.setInt(1, classID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM STUDENTS")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public Student getStudentByID(int ID) {
        Student student = null;
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE STUDENTS.ID = ?")) {
            statement.setInt(1, ID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                student = new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public ArrayList<Student> getStudentByName(String name) {
        ArrayList<Student> students = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM STUDENTS WHERE STUDENTS.NAME = ?")) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public boolean deleteStudent(int studentID) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM STUDENTS WHERE ID = ?")) {
            statement.setInt(1, studentID);
            return statement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStudent(Student student) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE STUDENTS SET NAME = ?,AGE = ?,CLAZZ = ? ,GENDER = ? WHERE ID = ?")) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getClazz());
            statement.setString(4, student.getGender());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}