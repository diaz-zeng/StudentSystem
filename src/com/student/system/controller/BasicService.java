package com.student.system.controller;

import com.student.system.dao.*;
import com.student.system.model.Student;
import com.student.system.model.Teacher;

import java.util.ArrayList;
import java.util.Set;

/**
 * 基本功能服务类，主要就是查询，单例实现
 *
 * @author Diaz
 * @version 1
 * @since 2018-11-11
 */
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

    /**
     * 按ID获取学生并作出判断返回值是否正确，如果正确则输出
     *
     * @param id 学生ID
     * @return 是否成功
     */
    public boolean getStudentByID(int id) {
        Student student = studentDao.getStudentByID(id);
        if (student != null) {

            System.out.println(student.toString());
            return true;
        } else return false;
    }

    /**
     * 按ID获取教师并作出判断返回值是否正确，如果正确则输出
     *
     * @param id 教师ID
     * @return 是否成功
     */
    public boolean getTeacherByID(int id) {
        Teacher teacher = teacherDao.getTeacherByID(id);
        if (teacher != null) {

            System.out.println(teacher.toString());
            return true;
        } else return false;
    }

    /**
     * 按姓名获取学生，并判断是否有返回的值，如果有则输出
     *
     * @param name 姓名
     * @return 是否成功
     */
    public boolean getStudentsByName(String name) {
        ArrayList<Student> students = studentDao.getStudentByName(name);
        if (students.size() > 0) {
            for (Student student : students) {
                System.out.println(student.toString());
            }
            return true;
        } else return false;
    }

    /**
     * 按班级获取学生，并判断是否有返回的值，如果有则输出
     *
     * @param classID 班级号
     * @return 是否成功
     */
    public boolean getStudentsByClassID(int classID) {
        ArrayList<Student> students = studentDao.getStudentOfClass(classID);
        if (students.size() > 0) {
            for (Student student : students) {
                System.out.println(student.toString());
            }
            return true;
        } else return false;
    }

    /**
     * 按姓名获取教师，并判断是否有返回的值，如果有则输出
     *
     * @param name 姓名
     * @return 是否成功
     */
    public boolean getTeachersByName(String name) {
        ArrayList<Teacher> teachers = teacherDao.getTeacherByName(name);
        if (teachers.size() > 0) {
            for (Teacher teacher : teachers) {
                System.out.println(teacher.toString());
            }
            return true;
        } else return false;
    }

    /**
     * 获取所有的成绩数据
     *
     * @return 是否存在成绩数据
     */
    public boolean getResults() {
        ArrayList<String> results = courseAndResultsDao.getResults();
        if (results.size() > 0) {
            for (String s : results) {
                System.out.println(s);
            }
            return true;
        } else return false;
    }

    /**
     * 获取一个课程的信息，并判断是否有学生选择该课程，部分基于Redis
     *
     * @param courseID 课程ID
     * @return 是否有数据
     */
    public boolean getCourseInfo(String courseID) {
        Set<String> set = redisService.getSet(courseID);
        if (null != redisService.getSet(courseID)) {
            System.out.println("选择课程(" + courseAndResultsDao.getCourseInfo(courseID) + ")的学生：");
            for (String s : set) {
                System.out.println(studentDao.getStudentByID(Integer.parseInt(s)));
            }
            return true;
        } else return false;
    }

}
