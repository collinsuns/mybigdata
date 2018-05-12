package cn.itcast.hadooprpc.protocal;

/**
 * Project Name: mybigdata
 * author: Suns
 * Date: 2017/10/3 20:43
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 * Description:
 */
public interface IUserLoginService {
    public static final long versionID = 100L;

    public String login(String name, String passwd);

}