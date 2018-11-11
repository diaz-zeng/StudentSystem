package com.student.system.model;

public class Teacher {
    private int ID;
    private String name;
    private String title;
    private int dept_ID;

    public Teacher(String name, String title, int dept_ID) {
        this.name = name;
        this.title = title;
        this.dept_ID = dept_ID;
    }

    public Teacher(int ID, String name, String title, int dept_ID) {
        this.ID = ID;
        this.name = name;
        this.title = title;
        this.dept_ID = dept_ID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getDept_ID() {
        return dept_ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDept_ID(int dept_ID) {
        this.dept_ID = dept_ID;
    }

    @Override
    public String toString() {
        return String.format("姓名：%s，部门：%d，编号：%d，职称：%s",getName(),getDept_ID(),getID(),getTitle());
    }
}
