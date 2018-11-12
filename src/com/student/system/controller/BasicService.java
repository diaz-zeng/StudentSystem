package com.student.system.controller;

import com.student.system.dao.DatabaseService;
import com.student.system.dao.RedisService;
import com.student.system.model.Student;
import com.student.system.model.Teacher;

import java.util.ArrayList;
import java.util.Set;

public class BasicService {
    private static BasicService instance;

    private DatabaseService databaseService;
    private RedisService redisService;

    public static synchronized BasicService getInstance() {
        if (instance == null) {
            instance = new BasicService();
        }
        return instance;
    }

    private BasicService() {
        databaseService = DatabaseService.getInstance();
        redisService = RedisService.getInstance();
    }

    public boolean getStudentByID(int id) {
        Student student = databaseService.getStudentByID(id);
        if (student != null) {

            System.out.println(student.toString());
            return true;
        } else return false;
    }

    public boolean getTeacherByID(int id) {
        Teacher teacher = databaseService.getTeacherByID(id);
        if (teacher != null) {

            System.out.println(teacher.toString());
            return true;
        } else return false;
    }

    public boolean getStudentsByName(String name) {
        ArrayList<Student> students = databaseService.getStudentByName(name);
        if (students.size() > 0) {
            for (Student student : students) {
                System.out.println(student.toString());
            }
            return true;
        } else return false;
    }

    public boolean getStudentsByClassID(int classID) {
        ArrayList<Student> students = databaseService.getStudentOfClass(classID);
        if (students.size() > 0) {
            for (Student student : students) {
                System.out.println(student.toString());
            }
            return true;
        } else return false;
    }

    public boolean getTeachersByName(String name) {
        ArrayList<Teacher> teachers = databaseService.getTeacherByName(name);
        if (teachers.size() > 0) {
            for (Teacher teacher : teachers) {
                System.out.println(teacher.toString());
            }
            return true;
        } else return false;
    }

    public boolean getResults() {
        ArrayList<String> results = databaseService.getResults();
        if (results.size() > 0) {
            for (String s : results) {
                System.out.println(s);
            }
            return true;
        } else return false;
    }

    public boolean getCourseInfo(String courseID) {
        Set<String> set;
        if (null != (set = redisService.getSet(courseID))) {
            System.out.println("选择课程(" + databaseService.getCourseInfo(courseID) + ")的学生：");
            for (String s : set) {
                System.out.println(databaseService.getStudentByID(Integer.parseInt(s)));
            }
            return true;
        } else return false;
    }

}
