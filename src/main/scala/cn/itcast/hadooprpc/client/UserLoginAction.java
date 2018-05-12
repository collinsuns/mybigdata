package cn.itcast.hadooprpc.client;

import cn.itcast.hadooprpc.protocal.IUserLoginService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.net.InetSocketAddress;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:43
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class UserLoginAction {

    public static void main(String[] args) throws Exception {
        IUserLoginService userLoginService = RPC.getProxy(IUserLoginService.class, 100L, new InetSocketAddress("localhost", 9999), new Configuration());
        String login = userLoginService.login("angelababy", "1314520");
        System.out.println(login);

    }
}