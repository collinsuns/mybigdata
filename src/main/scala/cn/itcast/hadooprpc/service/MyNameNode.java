package cn.itcast.hadooprpc.service;


import cn.itcast.hadooprpc.protocal.ClientNamenodeProtocal;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:30
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public class MyNameNode implements ClientNamenodeProtocal {
    //模拟namenode的业务方法之一：查询元数据
    @Override
    public String getMetaData(String path){

        return path+": 3 - {BLK_1,BLK_2} ....";

    }
}