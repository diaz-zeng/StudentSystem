package com.student.system.dao;

import com.student.system.model.Student;
import com.student.system.model.Teacher;

import java.sql.*;
import java.util.ArrayList;

public final class DatabaseService {
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("数据库驱动加载成功");
        } catch (ClassNotFoundException e) {
            System.err.println("数据库驱动加载失败！！！");
        }
    }

    private static final String url = "jdbc:oracle:thin:@192.168.31.4:1521:XE";
    private static final String user = "studentsystem";
    private static final String password = "1234";
    private static DatabaseService instance = null;
    private Connection connection = null;

    public static synchronized DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }

    private DatabaseService() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("数据库连接成功");
        } catch (SQLException e) {
            System.err.println("数据库连接失败");
            System.exit(0);
        }
    }

    public int createDept(String dept_Name) {
        int currID = 0;
        try (PreparedStatement statement = connection.prepareStatement("INSERT  INTO DEPT VALUES(?,?) ");
             PreparedStatement statement1 = connection.prepareStatement("select department_ID.nextval from dual");
             ResultSet resultSet = statement1.executeQuery()) {
            resultSet.next();
            currID = resultSet.getInt(1);
            statement.setInt(1, currID);
            statement.setString(2, dept_Name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return currID;
    }

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

    public ArrayList<Teacher> getDetpInfo(int detp_ID) {
        ArrayList<Teacher> teachers = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT TEACHERS.* FROM TEACHERS WHERE TEACHERS.DEPT_ID = ? ")) {
            statement.setInt(1,detp_ID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4), resultSet.getInt(3)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
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
        Student student =null;
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

    public Teacher getTeacherByID(int ID) {
        Teacher teacher =null;
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
            if (!statement.execute()) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return teacher;
    }

    public int createClass(String className, int owner) {
        try (PreparedStatement statement = connection.prepareStatement("insert into CLAZZ VALUES (?,?,?)");
             PreparedStatement statement1 = connection.prepareStatement("select CLASS_ID.nextval from dual");
             ResultSet set = statement1.executeQuery()) {
            set.next();
            int result = set.getInt(0);
            statement.setInt(1, result);
            statement.setString(2, className);
            statement.setInt(3, owner);
            if (statement.execute()) {
                return result;
            } else
                return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

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


}
