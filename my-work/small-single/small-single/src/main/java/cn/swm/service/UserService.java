package cn.swm.service;

import cn.swm.pojo.TbUser;

import java.util.Set;

public interface UserService {

    Set<String> getRoles(String userName);

    Set<String> getPermissions(String userName);

    TbUser getUserByUsername(String username);
}
