package cn.swm.service;

import cn.swm.pojo.TbBase;
import cn.swm.pojo.TbPermission;
import cn.swm.pojo.TbRole;
import cn.swm.pojo.TbUser;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.dto.RoleDto;

import java.util.List;
import java.util.Set;

public interface UserService {

    Set<String> getRoles(String userName);

    Set<String> getPermissions(String userName);

    TbUser getUserByUsername(String username);

    DataTableResult getRoleList();

    Long getRoleCount();

    DataTableResult getPermsList();

    boolean isRoleName(String name);

    int updateRolePerms(TbRole tbRole);

    int deleteRole(int id);

    int addRole(TbRole tbRole);

    Long getPermissonCount();

    int addPermission(TbPermission tbPermission);

    int updatePermission(TbPermission tbPermission);

    int deletepermission(Integer id);

    DataTableResult getUserList();

    Long getUserCount();

    List<TbRole> getAllRoles();

    boolean isUserName(String name);

    int addUser(TbUser tbUser);

    int deleteUser(long id);

    int updateUser(TbUser tbUser);

    int updateAdminPassword(TbUser tbUser);

    int updateStateUser(long id,int state);
}
