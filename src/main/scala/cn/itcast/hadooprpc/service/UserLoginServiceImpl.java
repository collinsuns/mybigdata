package cn.itcast.hadooprpc.service;


import cn.itcast.hadooprpc.protocal.IUserLoginService;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:45
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class UserLoginServiceImpl implements IUserLoginService {
    @Override
    public String login(String name, String passwd) {
        return name + "logged in successfully...";
    }
}