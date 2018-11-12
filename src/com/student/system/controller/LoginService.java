package com.student.system.controller;

import com.student.system.dao.RedisService;

public class LoginService {
    private static LoginService instance;

    private RedisService redisService;

    private boolean login;

    public synchronized static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }

    private LoginService() {
        login = false;
        redisService = RedisService.getInstance();
    }

    public boolean isLogin() {
        return login;
    }

    public boolean loginIn(String user, String password) {
        return login = password.equals(redisService.get(user));
    }


}
