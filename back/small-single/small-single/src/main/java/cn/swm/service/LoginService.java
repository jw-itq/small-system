package cn.swm.service;

import cn.swm.pojo.front.Member;

public interface LoginService {

    Member checkLoginByToken(String token);

    Member userLogin(String username,String password);

    int loginOut(String token);
}
