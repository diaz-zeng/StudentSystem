package com.student.system.controller;

public class NotLoginExecption extends Exception {
    @Override
    public String getMessage() {
        return "Need login!";
    }
}
