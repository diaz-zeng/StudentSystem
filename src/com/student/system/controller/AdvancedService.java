package com.student.system.controller;

import com.student.system.dao.*;
import com.student.system.model.Student;
import com.student.system.model.Teacher;

import java.util.ArrayList;

/**
 * 高级功能服务类，单例实现
 *
 * @author Diaz
 * @version 1
 * @since 2018-11-11
 */
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

    /**
     * 创建一个管理员账户，基于Redis
     *
     * @param name     用户名
     * @param password 密码
     * @return 是否创建成功
     */
    public boolean createAdmin(String name, String password) {
        if (redisService.isExists(name)) {
            return false;
        } else {
            redisService.put(name, password);
            return true;
        }
    }

    /**
     * 删除一个管理员账户，基于Redis
     *
     * @param name     管理员
     * @param password 密码
     * @return 是否删除成功
     */
    public boolean removeAdmin(String name, String password) {
        String result = redisService.get(name);
        if (null != result & result.equals(password)) {
            redisService.remove(name);
            return true;
        } else return false;
    }

    /**
     * 创建一个学生，并在创建前检查数据完整性
     *
     * @param name   姓名
     * @param age    年龄
     * @param clazz  班级
     * @param gender 性别
     * @return 是否创建成功
     */
    public boolean createStudent(String name, int age, int clazz, String gender) {
        if ("男".equals(gender) || "女".equals(gender)) {
            Student student = new Student(name, age, clazz, gender);
            student = studentDao.createStudent(student);
            if (student != null) {
                System.out.println(student.toString());
                return true;
            } else
                return false;
        } else return false;
    }

    /**
     * 更新一个学生信息
     *
     * @param id     原ID
     * @param name   姓名
     * @param age    年龄
     * @param clazz  班级
     * @param gender 性别
     * @return
     */
    public boolean updateStudent(int id, String name, int age, int clazz, String gender) {
        if ("男".equals(gender) || "女".equals(gender)) {
            Student student = new Student(id, name, age, clazz, gender);
            return studentDao.updateStudent(student);
        }
        return false;

    }

    /**
     * 删除一个学生
     *
     * @param id 学生ID
     * @return 是否成功
     */
    public boolean deleteStudent(int id) {
//        int clazz = studentDao.getStudentByID(id).getClazz();
        return studentDao.deleteStudent(id);

    }


    /**
     * 创建一个教师，并在创建前检查数据完整性
     *
     * @param name    姓名
     * @param title   职称
     * @param detp_ID 部门编号
     * @return 是否成功
     */
    public boolean createTeacher(String name, String title, int detp_ID) {
        Teacher teacher = new Teacher(name, title, detp_ID);
        teacher = teacherDao.createTeacher(teacher);
        if (teacher != null) {
            System.out.println(teacher);
            return true;
        } else return false;
    }


    /**
     * 更新教师信息
     *
     * @param id      原ID
     * @param name    姓名
     * @param title   职称
     * @param detp_ID 部门
     * @return
     */
    public boolean updateTeacher(int id, String name, String title, int detp_ID) {
        Teacher teacher = new Teacher(id, name, title, detp_ID);
        return teacherDao.updateTeacher(teacher);
    }

    /**
     * 删除一个教师对象
     *
     * @param id 教师ID
     * @return 是否成功
     */
    public boolean deleteTeaher(int id) {
        return teacherDao.deleteTeacher(id);
    }

    /**
     * 创建一门课程
     *
     * @param id    课程ID
     * @param name  课程名
     * @param owner 辅导教师
     * @return 是否成功
     */
    public boolean createCourse(String id, String name, int owner) {
        return courseAndResultsDao.createCourse(id, name, owner);
    }

    /**
     * 删除一个课程
     *
     * @param courseID 课程ID
     * @return 是否成功
     */
    public boolean deleteCourse(String courseID) {
        return deleteCourse(courseID);
    }

    /**
     * 添加一条成绩记录
     *
     * @param studentID 学号
     * @param courseID  课程编号
     * @param results   成绩
     * @return 是否成功
     */
    public boolean putResults(int studentID, String courseID, int results) {
        return courseAndResultsDao.putResults(studentID, courseID, results);
    }

    /**
     * 创建一个班级
     *
     * @param className 班级名
     * @param owner     辅导教师
     * @return 是否成功
     */
    public int createClass(String className, int owner) {
        return classAndDeptDao.createClass(className, owner);
    }

    /**
     * 删除一个班级
     *
     * @param id 班级ID
     * @return 是否成功
     */
    public boolean deleteClass(int id) {
        return classAndDeptDao.deleteClass(id);
    }

    /**
     * 选课，基于Redis
     *
     * @param studentID 学号
     * @param courseID  课程ID
     * @return 是否成功
     */
    public boolean choseCourse(int studentID, String courseID) {
        if (studentDao.getStudentByID(studentID) != null && courseAndResultsDao.getCourseInfo(courseID) != null) {
            redisService.putSet(courseID, String.valueOf(studentID));
            return true;
        } else return false;
    }

    /**
     * 创建一个部门
     *
     * @param dept_Name 部门名称
     * @return 是否成功
     */
    public boolean createDept(String dept_Name) {
        int result = 0;
        if ((result = classAndDeptDao.createDept(dept_Name)) != 0) {
            System.out.println(Integer.toString(result) + "---" + dept_Name);
            return true;
        } else return false;
    }

    /**
     * 删除一个部门
     *
     * @param id 部门编号
     * @return 是否成功
     */
    public boolean deleteDept(int id) {
        return classAndDeptDao.deleteDept(id);
    }


    /**
     * 显示部门下所有的教师信息
     *
     * @param dept_ID 部门编号
     * @return 是否成功
     */
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
