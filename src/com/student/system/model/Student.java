package com.student.system.model;

public class Student {
    private int ID;
    private String name;
    private int age;
    private int clazz;
    private String gender;


    public Student(String name, int age, int clazz, String gender) {
        this.name = name;
        this.age = age;
        this.clazz = clazz;
        this.gender = gender;
    }

    public Student(int ID, String name, int age, int clazz, String gender) {
        this.ID = ID;
        this.name = name;
        this.age = age;
        this.clazz = clazz;
        this.gender = gender;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getClazz() {
        return clazz;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setClazz(int clazz) {
        this.clazz = clazz;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }
}
