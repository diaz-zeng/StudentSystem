package com.student.system.view;

import com.student.system.controller.AdvancedService;
import com.student.system.controller.NotLoginExecption;
import com.student.system.dao.RedisService;

import java.util.Scanner;

public class AdvancedView {
    private AdvancedService advancedService;
    private RedisService redisService;

    public AdvancedView() throws NotLoginExecption {
        advancedService = AdvancedService.getInstance();
        redisService = RedisService.getInstance();
    }

    public void start() {
        while (true) {
            printMenu();

            String userInput = new Scanner(System.in).nextLine();
            if ("#end".equals(userInput))
                break;
            try {
                switch (Integer.parseInt(userInput)) {
                    case 1: {
                        addStudent();
                        break;
                    }
                    case 2: {
                        addTeacher();
                        break;
                    }
                    case 3: {
                        addDept();
                        break;
                    }
                    case 4: {
                        addClass();
                        break;
                    }
                    case 5: {
                        addCourse();
                        break;
                    }
                    case 6: {
                        choseCourse();
                        break;
                    }
                    case 7: {
                        putResults();
                        break;
                    }
                    case 8: {
                        deleteStudent();
                        break;
                    }
                    case 9: {
                        deleteTeacher();
                        break;
                    }
                    case 10: {
                        deleteDept();
                        break;
                    }
                    case 11: {
                        deleteClass();
                        break;
                    }
                    case 12: {
                        deleteCourse();
                        break;
                    }
                    case 13: {
                        addAdmin();
                        break;
                    }
                    case 14: {
                        removeAdmin();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void printMenu() {
        System.out.println("   高级功能");
        System.out.println(" 1.添加学生信息");
        System.out.println(" 2.添加教师信息");
        System.out.println(" 3.添加部门");
        System.out.println(" 4.添加班级");
        System.out.println(" 5.添加课程");
        System.out.println(" 6.选课");
        System.out.println(" 7.登记成绩");
        System.out.println(" 8.删除学生信息");
        System.out.println(" 9.删除教师信息（需先将其与其他组织解除关联）");
        System.out.println("10.删除部门");
        System.out.println("11.删除班级");
        System.out.println("12.删除课程");
        System.out.println("13.添加管理员");
        System.out.println("14.删除管理员");
        System.out.println("请选择(#end退出)：");
    }

    private void addStudent() {
        System.out.println("请输入学生信息（姓名,年龄,班级,性别（#end以退出））：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        } else {
            try {
                String[] datas = userInput.split(",");
                if (!advancedService.createStudent(datas[0], Integer.parseInt(datas[1]), Integer.parseInt(datas[2]), datas[3])) {
                    System.err.println("信息有误请重新输入");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addTeacher() {
        System.out.println("请输入教师信息（姓名,职称,部门（#end以退出））：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        } else {
            try {
                String[] datas = userInput.split(",");
                if (!advancedService.createTeacher(datas[0], datas[1], Integer.parseInt(datas[2]))) {
                    System.err.println("信息有误请重新输入");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addDept() {
        System.out.println("请输入部门的名字（此名称唯一）");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        } else {
            try {
                if (!advancedService.createDept(userInput)) {
                    System.out.println("创建失败，请检查该部门是否存在！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addClass() {
        System.out.println("请输入班级的名字与指导老师ID");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        } else {
            try {
                String[] datas = userInput.split(",");
                int result = advancedService.createClass(datas[0], Integer.parseInt(datas[1]));
                if (result > 0) {
                    System.out.println("班级创建成功！班级ID：" + result);
                } else {
                    System.out.println("班级创建失败，请检查输入！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addCourse() {
        System.out.println("请输入课程编号,课程名与讲师ID（逗号分隔）：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        } else {
            try {
                String[] datas = userInput.split(",");
                if (advancedService.createCourse(datas[0], datas[1], Integer.parseInt(datas[2]))) {
                    System.out.println("课程创建成功！");
                } else {
                    System.out.println("课程创建失败，请检查课程是否存在！");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }

    private void choseCourse() {

        System.out.println("请输入学生ID与课程编号（逗号分隔）：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        } else {
            try {
                String[] datas = userInput.split(",");
                if (advancedService.choseCourse(Integer.parseInt(datas[0]), datas[1])) {
                    System.out.println("课程选择成功！");
                } else {
                    System.out.println("课程选择失败，学生或课程不存在！");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteStudent() {
        System.out.println("请输入学号:");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        }
        try {
            System.out.println(advancedService.deleteStudent(Integer.parseInt(userInput)) ? "删除成功" : "删除失败，学生可能不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void deleteTeacher() {
        System.out.println("请输入教师ID:");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        }
        try {
            System.out.println(advancedService.deleteTeaher(Integer.parseInt(userInput)) ? "删除成功" : "删除失败，教师可能不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putResults() {
        System.out.println("请输入学生ID，课程编号与成绩（逗号分隔）：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        }
        try {
            String[] datas = userInput.split(",");
            if (advancedService.putResults(Integer.parseInt(datas[0]), datas[1], Integer.parseInt(datas[2]))) {
                System.out.println("成绩登记成功！");
            } else {
                System.out.println("成绩登记失败，学生或课程不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void deleteClass() {
        System.out.println("请输入要删除的班级ID：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        }
        try {
            System.out.println(advancedService.deleteClass(Integer.parseInt(userInput)) ? "删除成功" : "删除失败，班级可能不存在或仍有学生");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse() {
        System.out.println("请输入要删除的课程ID：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        }
        try {
            System.out.println(advancedService.deleteCourse(userInput) ? "删除成功" : "删除失败，课程可能不存");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteDept() {
        System.out.println("请输入要删除的部门ID：");
        String userInput = new Scanner(System.in).nextLine();
        if ("#end".equals(userInput)) {
            return;
        }
        try {
            System.out.println(advancedService.deleteDept(Integer.parseInt(userInput)) ? "删除成功" : "删除失败，部门可能不存在或仍有教师");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String userName = scanner.nextLine();
        System.out.println("请输入密码：");
        String userPassword = scanner.nextLine();
        if (!redisService.isExists(userName)) {
            redisService.put(userName, userPassword);
            System.out.println("管理员添加成功！");
        } else
            System.out.println("管理员添加失败，用户名已存在！");
    }

    private void removeAdmin() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入用户名：");
        String userName = scanner.nextLine();
        System.out.println("请输入密码：");
        String userPassword = scanner.nextLine();
        if (advancedService.removeAdmin(userName, userPassword))
            System.out.println("管理员删除成功！");
        else
            System.out.println("管理员删除失败，用户名或密码不正确！");
    }
}
