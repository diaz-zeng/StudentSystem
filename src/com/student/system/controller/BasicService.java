package com.student.system.controller;

import com.student.system.dao.*;
import com.student.system.model.Student;
import com.student.system.model.Teacher;

import java.util.ArrayList;
import java.util.Set;

public class BasicService {
    private static BasicService instance;

    private RedisService redisService;
    private StudentDao studentDao;
    private TeacherDao teacherDao;
    private ClassAndDeptDao classAndDeptDao;
    private CourseAndResultsDao courseAndResultsDao;

    public static synchronized BasicService getInstance() {
        if (instance == null) {
            instance = new BasicService();
        }
        return instance;
    }

    private BasicService() {
        studentDao = new StudentDao();
        teacherDao = new TeacherDao();
        classAndDeptDao = new ClassAndDeptDao();
        courseAndResultsDao = new CourseAndResultsDao();
        redisService = RedisService.getInstance();
    }

    public boolean getStudentByID(int id) {
        Student student = studentDao.getStudentByID(id);
        if (student != null) {

            System.out.println(student.toString());
            return true;
        } else return false;
    }

    public boolean getTeacherByID(int id) {
        Teacher teacher = teacherDao.getTeacherByID(id);
        if (teacher != null) {

            System.out.println(teacher.toString());
            return true;
        } else return false;
    }

    public boolean getStudentsByName(String name) {
        ArrayList<Student> students = studentDao.getStudentByName(name);
        if (students.size() > 0) {
            for (Student student : students) {
                System.out.println(student.toString());
            }
            return true;
        } else return false;
    }

    public boolean getStudentsByClassID(int classID) {
        ArrayList<Student> students = studentDao.getStudentOfClass(classID);
        if (students.size() > 0) {
            for (Student student : students) {
                System.out.println(student.toString());
            }
            return true;
        } else return false;
    }

    public boolean getTeachersByName(String name) {
        ArrayList<Teacher> teachers = teacherDao.getTeacherByName(name);
        if (teachers.size() > 0) {
            for (Teacher teacher : teachers) {
                System.out.println(teacher.toString());
            }
            return true;
        } else return false;
    }

    public boolean getResults() {
        ArrayList<String> results = courseAndResultsDao.getResults();
        if (results.size() > 0) {
            for (String s : results) {
                System.out.println(s);
            }
            return true;
        } else return false;
    }

    public boolean getCourseInfo(String courseID) {
        Set<String> set = redisService.getSet(courseID);
        if (null != redisService.getSet(courseID) ){
            System.out.println("选择课程(" + courseAndResultsDao.getCourseInfo(courseID) + ")的学生：");
            for (String s : set) {
                System.out.println(studentDao.getStudentByID(Integer.parseInt(s)));
            }
            return true;
        } else return false;
    }

}
