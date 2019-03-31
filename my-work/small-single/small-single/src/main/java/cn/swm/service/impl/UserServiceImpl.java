package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbPermissionMapper;
import cn.swm.mapper.TbRoleMapper;
import cn.swm.mapper.TbRolePermMapper;
import cn.swm.mapper.TbUserMapper;
import cn.swm.pojo.*;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.dto.RoleDto;
import cn.swm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private TbRoleMapper tbRoleMapper;

    @Autowired
    private TbPermissionMapper tbPermissionMapper;

    @Autowired
    private TbRolePermMapper tbRolePermMapper;

    @Override
    public Set<String> getRoles(String userName) {
        return tbUserMapper.getRoles(userName);
    }

    @Override
    public Set<String> getPermissions(String userName) {
        return tbUserMapper.getPermissions(userName);
    }

    /**
     * 根据用户名获得用户的信息
     * @param username
     * @return
     */
    @Override
    public TbUser getUserByUsername(String username) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStateEqualTo(1);
        List<TbUser> list = null;
        try {
            list = tbUserMapper.selectByExample(tbUserExample);
        }catch (Exception e){
            throw new SmallException("通过ID获取用户信息失败");
        }
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }

    /**
     * 获得所有的角色信息
     * @return
     */
    @Override
    public DataTableResult getRoleList() {
        DataTableResult result = new DataTableResult();
        List<RoleDto> list = new ArrayList<>();
        TbRoleExample tbRoleExample = new TbRoleExample();
        List<TbRole> roles = tbRoleMapper.selectByExample(tbRoleExample);
        if(roles==null){
            throw new SmallException("查询角色列表失败");
        }

        for(TbRole tbRole : roles){
            RoleDto roleDto = new RoleDto();
            roleDto.setId(tbRole.getId());
            roleDto.setName(tbRole.getName());
            roleDto.setDescription(tbRole.getDescription());

            List<String> perms = tbRoleMapper.getPermsByRoleId(tbRole.getId());
            String names = "";
            for(String perm : perms){
                names += "|"+perm;
            }
            roleDto.setPermissions(names);
            list.add(roleDto);
        }
        result.setData(list);
        return result;
    }

    /**
     * 获得角色的数量
     * @return
     */
    @Override
    public Long getRoleCount() {
        TbRoleExample tbRoleExample = new TbRoleExample();
        return tbRoleMapper.countByExample(tbRoleExample);
    }

    /**
     * 获得权限列表
     * @return
     */
    @Override
    public DataTableResult getPermsList() {
        DataTableResult result = new DataTableResult();
        TbPermissionExample tbPermissionExample = new TbPermissionExample();
        List<TbPermission> list = tbPermissionMapper.selectByExample(tbPermissionExample);

        result.setData(list);
        return result;
    }

    /**
     * 判断编辑的角色是否已经存在
     * @return
     */
    @Override
    public boolean isRoleName(String name) {
        TbRoleExample tbRoleExample = new TbRoleExample();
        TbRoleExample.Criteria criteria = tbRoleExample.createCriteria();
        criteria.andNameEqualTo(name);
        List<TbRole> list = tbRoleMapper.selectByExample(tbRoleExample);
        if(!list.isEmpty()){
            return false;
        }
        return true;
    }

    /**
     * 更新用户权限的信息
     * @param tbRole
     * @return
     */
    @Override
    public int updateRolePerms(TbRole tbRole) {
        if(tbRoleMapper.updateByPrimaryKey(tbRole)!=1){
            throw new SmallException("更新角色权限信息失败");
        }
        return 1;
    }

    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @Override
    public int deleteRole(int id) {
        List<String> userRoles = tbRoleMapper.getUserByRoleId(id);
        if(userRoles!=null||userRoles.size()!=0){
            return 0;
        }

        if(tbRoleMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除角色失败");
        }

        TbRolePermExample tbRolePermExample = new TbRolePermExample();
        TbRolePermExample.Criteria criteria = tbRolePermExample.createCriteria();
        criteria.andRoleIdEqualTo(id);
        List<TbRolePerm> list = tbRolePermMapper.selectByExample(tbRolePermExample);

        for(TbRolePerm tbRolePerm : list){
            if(tbRolePermMapper.deleteByPrimaryKey(tbRolePerm.getId())!=1){
                throw new SmallException("删除角色权限表失败");
            }
        }
        return 1;
    }

    /**
     * 添加角色和角色对应的权限
     * @param tbRole
     * @return
     */
    @Override
    public int addRole(TbRole tbRole) {
        if(!isRoleName(tbRole.getName())){
            throw new SmallException("该角色已经存在");
        }

        if(tbRoleMapper.insert(tbRole)!=1){
            throw new SmallException("添加角色的时候出错");
        }

        if(tbRole.getRoles()!=null){
            TbRoleExample tbRoleExample = new TbRoleExample();
            TbRoleExample.Criteria criteria = tbRoleExample.createCriteria();
            criteria.andNameEqualTo(tbRole.getName());
            TbRole newRoles = tbRoleMapper.selectByExample(tbRoleExample).get(0);
            for(int i = 0;i<tbRole.getRoles().length;i++){
                TbRolePerm tbRolePerm = new TbRolePerm();
                tbRolePerm.setRoleId(newRoles.getId());
                tbRolePerm.setPermissionId(tbRole.getRoles()[i]);
                if(tbRolePermMapper.insert(tbRolePerm)!=1){
                    throw new SmallException("添加角色对应的权限的时候出错");
                }
            }
        }

        return 1;
    }


    /**
     * 获得权限的数量
     * @return
     */
    @Override
    public Long getPermissonCount() {
        TbPermissionExample tbPermissionExample = new TbPermissionExample();
        return tbPermissionMapper.countByExample(tbPermissionExample);
    }

    /**
     * 添加权限
     * @param tbPermission
     * @return
     */
    @Override
    public int addPermission(TbPermission tbPermission) {
        if(tbPermissionMapper.insert(tbPermission)!=1){
            throw new SmallException("添加权限的时候出错");
        }
        return 1;
    }

    /**
     * 修改权限
     * @param tbPermission
     * @return
     */
    @Override
    public int updatePermission(TbPermission tbPermission) {
        if(tbPermissionMapper.updateByPrimaryKey(tbPermission)!=1){
            throw new SmallException("修改权限的时候出错");
        }
        return 1;
    }

    /**
     * 删除权限，根据id
     * @param id
     * @return
     */
    @Override
    public int deletepermission(Integer id) {
        if(tbPermissionMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除权限的时候出错");
        }

        TbRolePermExample tbRolePermExample = new TbRolePermExample();
        TbRolePermExample.Criteria criteria = tbRolePermExample.createCriteria();
        criteria.andPermissionIdEqualTo(id);
        List<TbRolePerm> list = tbRolePermMapper.selectByExample(tbRolePermExample);

        for(TbRolePerm tbRolePerm : list){
            if(tbRolePermMapper.deleteByPrimaryKey(tbRolePerm.getId())!=1){
                throw new SmallException("删除权限跟角色对应的表的时候出错");
            }
        }
        return 1;
    }

}
