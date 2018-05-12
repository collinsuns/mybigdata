package cn.itcast.hadooprpc.client;

import cn.itcast.hadooprpc.protocal.ClientNamenodeProtocal;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:37
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class MyHdfsClient {

    public static void main(String[] args) throws IOException {
        ClientNamenodeProtocal namenode = RPC.getProxy(ClientNamenodeProtocal.class, 1L, new InetSocketAddress("localhost", 8888), new Configuration());
        String metaData = namenode.getMetaData("test");
        System.out.println(metaData);
    }
}