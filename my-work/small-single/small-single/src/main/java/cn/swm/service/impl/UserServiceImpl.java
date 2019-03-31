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
import org.springframework.util.DigestUtils;

import java.util.*;

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
        result.setSuccess(true);
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

        result.setSuccess(true);
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

        TbRolePermExample tbRolePermExample = new TbRolePermExample();
        TbRolePermExample.Criteria criteria = tbRolePermExample.createCriteria();
        criteria.andRoleIdEqualTo(tbRole.getId());
        List<TbRolePerm> list = tbRolePermMapper.selectByExample(tbRolePermExample);

        List<Integer> newPermList = new ArrayList<>();
        if(tbRole.getRoles()!=null){
            newPermList = Arrays.asList(tbRole.getRoles());
        }
        //将list转换成编号集合,把编号提出来
        List<Integer> oldPermList = new ArrayList<>();

        for(TbRolePerm tbRolePerm : list){
            if(!newPermList.contains(tbRolePerm.getPermissionId())){
                if(tbRolePermMapper.deleteByPrimaryKey(tbRolePerm.getId())!=1){
                    throw new SmallException("在更新用户权限的时候，删除多于的权限的时候出错");
                }
            }
            oldPermList.add(tbRolePerm.getPermissionId());
        }

        for(Integer perm : newPermList){
            if(!oldPermList.contains(perm)){
                //需要添加
                TbRolePerm tbRolePerm = new TbRolePerm();
                tbRolePerm.setRoleId(tbRole.getId());
                tbRolePerm.setPermissionId(perm);
                if(tbRolePermMapper.insert(tbRolePerm)!=1){
                    throw new SmallException("在更新用户权限信息的时候，添加权限的时候出错");
                }
            }
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

    /**
     * 查询所有的管理员
     * @return
     */
    @Override
    public DataTableResult getUserList() {
        DataTableResult result = new DataTableResult();
        TbUserExample tbUserExample = new TbUserExample();
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);

        for(TbUser tbUser : list){
            String roles = "";
            Iterator<TbRole> iterator = getAllRoles().iterator();
            while (iterator.hasNext()){
                roles += iterator.next().getName()+" ";
            }
            tbUser.setRoleNames(roles);
        }
        result.setData(list);
        result.setSuccess(true);
        return result;
    }

    /**
     * 查询管理员的列表数量
     * @return
     */
    @Override
    public Long getUserCount() {
        TbUserExample tbUserExample = new TbUserExample();
        return tbUserMapper.countByExample(tbUserExample);
    }

    /**
     * 得到所有的角色信息
     * @return
     */
    @Override
    public List<TbRole> getAllRoles() {
        TbRoleExample tbRoleExample = new TbRoleExample();
        return tbRoleMapper.selectByExample(tbRoleExample);
    }

    /**
     * 判断管理员的名字是否跟数据库里面的重复了
     * @param name
     * @return
     */
    @Override
    public boolean isUserName(String name) {
        TbUserExample tbUserExample = new TbUserExample();
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        for(TbUser tbUser : list){
            if(tbUser.getUsername().equals(name)){
                return false;
            }
        }
        return true;
    }

    /**
     * 添加管理员
     * @param tbUser
     * @return
     */
    @Override
    public int addUser(TbUser tbUser) {
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        tbUser.setState(1);
        if(tbUserMapper.insert(tbUser)!=1){
            throw new SmallException("添加管理员失败");
        }
        return 1;
    }

    /**
     * 删除管理员，根据id
     * @param id
     * @return
     */
    @Override
    public int deleteUser(long id) {
        if(tbUserMapper.deleteByPrimaryKey(id)!=1){
            throw new SmallException("删除管理员失败");
        }
        return 1;
    }

    /**
     * 更新用户的信息
     * @param tbUser
     * @return
     */
    @Override
    public int updateUser(TbUser tbUser) {
        TbUser oldTbUser = tbUserMapper.selectByPrimaryKey(tbUser.getId());
        tbUser.setState(1);
        tbUser.setUpdated(new Date());
        tbUser.setCreated(oldTbUser.getCreated());
        if(tbUserMapper.updateByPrimaryKey(tbUser)!=1){
            throw new SmallException("更新用户的时候出错");
        }

        return 1;
    }

    /**
     * 更改密码
     * @param tbUser
     * @return
     */
    @Override
    public int updateAdminPassword(TbUser tbUser) {
        TbUser oldTbUser = tbUserMapper.selectByPrimaryKey(tbUser.getId());
        String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
        oldTbUser.setPassword(md5Pass);
        oldTbUser.setUpdated(new Date());

        if(tbUserMapper.updateByPrimaryKey(oldTbUser)!=1){
            throw new SmallException("修改密码失败");
        }
        return 1;
    }

    /**
     * 根据用户的状态选择是否要停用或者启用用户
     * @param id
     * @param state
     * @return
     */
    @Override
    public int updateStateUser(long id, int state) {
        TbUser tbUser = tbUserMapper.selectByPrimaryKey(id);
        tbUser.setState(state);
        tbUser.setUpdated(new Date());
        if(tbUserMapper.updateByPrimaryKey(tbUser)!=1){
            throw new SmallException("用户状态更新失败");
        }
        return 1;
    }

}
