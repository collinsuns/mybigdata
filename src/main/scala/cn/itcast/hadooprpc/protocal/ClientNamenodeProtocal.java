package cn.itcast.hadooprpc.protocal;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:30
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public interface ClientNamenodeProtocal {

    public static final long versionID=1L; //会读取这个版本号， 但可以和客户端的不一样， 没有校验
    public String getMetaData(String path);
}