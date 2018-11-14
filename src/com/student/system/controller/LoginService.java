package com.student.system.controller;

import com.student.system.dao.RedisService;

/**
 * 登录服务类，单例实现，基于Redis服务
 *
 * @author Diaz
 * @version 1
 * @since 2018-11-11
 */
public class LoginService {
    private static LoginService instance;

    private RedisService redisService;

    private boolean login;

    /**
     * 获取本类唯一实例
     *
     * @return 本类的唯一实例
     */
    public synchronized static LoginService getInstance() {
        if (instance == null) {
            instance = new LoginService();
        }
        return instance;
    }


    /**
     * 构造函数
     */
    private LoginService() {
        login = false;
        redisService = RedisService.getInstance();
    }

    /**
     * 向查询者返回是否登录
     *
     * @return 是否登陆
     */
    public boolean isLogin() {
        return login;
    }

    /**
     * 登录方法
     *
     * @param user     用户名
     * @param password 密码
     * @return 登录是否成功
     */
    public boolean loginIn(String user, String password) {
        return login = password.equals(redisService.get(user));
    }


}
