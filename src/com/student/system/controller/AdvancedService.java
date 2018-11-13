package com.student.system.controller;

import com.student.system.dao.*;
import com.student.system.model.Student;
import com.student.system.model.Teacher;

import java.util.ArrayList;

public class AdvancedService {
    private static AdvancedService instance;

    private RedisService redisService;
    private StudentDao studentDao;
    private TeacherDao teacherDao;
    private ClassAndDeptDao classAndDeptDao;
    private CourseAndResultsDao courseAndResultsDao;


    public static synchronized AdvancedService getInstance() throws NotLoginExecption {
        if (!LoginService.getInstance().isLogin())
            throw new NotLoginExecption();
        if (instance == null)
            instance = new AdvancedService();
        return instance;
    }

    private AdvancedService() {
        redisService = RedisService.getInstance();
        studentDao = new StudentDao();
        teacherDao = new TeacherDao();
        classAndDeptDao = new ClassAndDeptDao();
        courseAndResultsDao = new CourseAndResultsDao();
    }

    public boolean createAdmin(String name, String password) {
        if (redisService.isExists(name)) {
            return false;
        } else {
            redisService.put(name, password);
            return true;
        }
    }

    public boolean removeAdmin(String name, String password) {
        String result = redisService.get(name);
        if (null != result & result.equals(password)) {
            redisService.remove(name);
            return true;
        } else return false;
    }

    public boolean createStudent(String name, int age, int clazz, String gender) {
        if ("男".equals(gender) || "女".equals(gender)) {
            Student student = new Student(name, age, clazz, gender);
            student = studentDao.createStudent(student);
            if (student != null) {
                System.out.println(student);
                return true;
            } else
                return false;
        } else return false;
    }

    public boolean deleteStudent(int id) {
        int clazz = studentDao.getStudentByID(id).getClazz();
        return redisService.remove(String.valueOf(id), String.valueOf(clazz)) && studentDao.deleteStudent(id);

    }

    public boolean createTeacher(String name, String title, int detp_ID) {
        Teacher teacher = new Teacher(name, title, detp_ID);
        teacher = teacherDao.createTeacher(teacher);
        if (teacher != null) {
            System.out.println(teacher);
            return true;
        } else return false;
    }

    public boolean deleteTeaher(int id) {
        return studentDao.deleteStudent(id);
    }

    public boolean createCourse(String id, String name, int owner) {
        return courseAndResultsDao.createCourse(id, name, owner);
    }

    public boolean deleteCourse(String courseID) {
        return deleteCourse(courseID);
    }

    public boolean putResults(int studentID, String courseID, int results) {
        return courseAndResultsDao.putResults(studentID, courseID, results);
    }

    public int createClass(String className, int owner) {
        return classAndDeptDao.createClass(className, owner);
    }

    public boolean deleteClass(int id) {
        return classAndDeptDao.deleteClass(id);
    }

    public boolean choseCourse(int studentID, String courseID) {
        if (studentDao.getStudentByID(studentID) != null && courseAndResultsDao.getCourseInfo(courseID) != null) {
            redisService.put(courseID, String.valueOf(studentID));
            return true;
        } else return false;
    }

    public boolean createDept(String dept_Name) {
        int result = 0;
        if ((result = classAndDeptDao.createDept(dept_Name)) != 0) {
            System.out.println(Integer.toString(result) + "---" + dept_Name);
            return true;
        } else return false;
    }

    public boolean showDeptInfo(int dept_ID) {
        String dept_info = classAndDeptDao.getDeptInfo(dept_ID);
        ArrayList<Teacher> teachers = teacherDao.getDetpInfo(dept_ID);
        if (dept_info != null && teachers.size() > 0) {
            System.out.println(dept_info);
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
            }
            return true;
        } else return false;
    }

}
