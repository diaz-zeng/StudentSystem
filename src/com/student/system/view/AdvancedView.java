package com.student.system.view;

import com.student.system.controller.AdvancedService;
import com.student.system.controller.NotLoginExecption;

import java.util.Scanner;

public class AdvancedView {
    private AdvancedService advancedService;

    public AdvancedView() throws NotLoginExecption {
        advancedService = AdvancedService.getInstance();
    }

    public void start() {

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

            }
        }
    }

    private void addDept(){

    }
}
