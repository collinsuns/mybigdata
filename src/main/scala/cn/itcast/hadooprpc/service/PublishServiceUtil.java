package cn.itcast.hadooprpc.service;

import cn.itcast.hadooprpc.protocal.ClientNamenodeProtocal;
import cn.itcast.hadooprpc.protocal.IUserLoginService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC.Builder;
import org.apache.hadoop.ipc.RPC.Server;

import java.io.IOException;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:33
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class PublishServiceUtil {
    public static void main(String[] args) throws IOException {
        Builder builder = new Builder(new Configuration());
        builder.setBindAddress("localhost")
                .setPort(8888)
                .setProtocol(ClientNamenodeProtocal.class)
                .setInstance(new MyNameNode());

        Server server = builder.build();
        server.start();

        Builder builder2 = new Builder(new Configuration());
        builder2.setBindAddress("localhost")
                .setPort(9999)
                .setProtocol(IUserLoginService.class)
                .setInstance(new UserLoginServiceImpl());

        Server server2 = builder2.build();
        server2.start();
    }
}