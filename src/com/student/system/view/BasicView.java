package com.student.system.view;

import com.student.system.controller.BasicService;
import com.student.system.controller.LoginService;
import com.student.system.controller.NotLoginExecption;

import java.util.Scanner;

public class BasicView {
    private LoginService loginService;
    private BasicService basicService;

    public BasicView() {
        basicService = BasicService.getInstance();
        loginService = LoginService.getInstance();
    }

    public void Start() {
        while (true) {
            printMenu();
            String userInput = new Scanner(System.in).nextLine();
            if ("#end".equals(userInput))
                break;
            try {
                switch (Integer.parseInt(userInput)) {
                    case 1: {
                        menuGetStudentByID();
                        break;
                    }
                    case 2: {
                        menuGetStudentsByName();
                        break;
                    }
                    case 3: {
                        menuGetTeacherByID();
                        break;
                    }
                    case 4: {
                        menuGetTeachersByName();
                        break;
                    }
                    case 5: {
                        menuShowResults();
                        break;
                    }
                    case 6: {
                        menuShowCoureseInfo();
                        break;
                    }
                    case 7: {
                        menuAdvancedMode();
                        break;
                    }
                    case 8: {
                        menuLogin();
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void printMenu() {
        System.out.println("欢迎使用" + (loginService.isLogin() ? "(已登录)" : "(未登录)"));
        System.out.println("1.按学号查询学生");
        System.out.println("2.按姓名查询学生");
        System.out.println("3.按ID查询老师");
        System.out.println("4.按姓名查询老师");
        System.out.println("5.成绩公示");
        System.out.println("6.查看课程信息");
        System.out.println("7.高级功能(需登陆)");
        System.out.println("8.管理员登陆");
        System.out.println("请选择(#end退出)：");
    }

    public void menuGetStudentByID() {
        System.out.print("请输入学号");
        try {
            int id = new Scanner(System.in).nextInt();
            if (!basicService.getStudentByID(id)) {
                System.out.println("学生信息不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void menuGetStudentsByName() {
        System.out.println("请输入姓名");
        try {
            String name = new Scanner(System.in).nextLine();
            if (!basicService.getStudentsByName(name)) {
                System.out.println("学生信息不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuGetTeacherByID() {

        System.out.print("请输入ID");
        try {
            int id = new Scanner(System.in).nextInt();
            if (!basicService.getTeacherByID(id)) {
                System.out.println("教师信息不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void menuGetTeachersByName() {
        System.out.println("请输入姓名");
        try {
            String name = new Scanner(System.in).nextLine();
            if (!basicService.getTeachersByName(name)) {
                System.out.println("教师信息不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuShowResults() {
        if (!basicService.getResults()) {
            System.out.println("没有登记过的成绩信息");
        }
    }

    public void menuShowCoureseInfo() {
        System.out.println("请输入课程编号");
        if (!basicService.getCourseInfo(new Scanner(System.in).nextLine())) {
            System.out.println("未找到相应的课程信息");
        }
    }

    public void menuAdvancedMode() {
        try {
            AdvancedView advancedView = new AdvancedView();
            advancedView.start();
        } catch (NotLoginExecption e) {
            System.err.println("请先登录");
        }
    }

    public void menuLogin() {
        String name, password;
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入用户名:");
        name = scanner.nextLine();
        System.out.print("请输入密码:");
        password = scanner.nextLine();
        if (loginService.loginIn(name, password)) {
            System.err.println("登陆成功，欢迎使用");
        } else {
            System.out.println("登陆失败，请检查用户名或密码");
        }
    }
}
