package cn.swm.controller;

import cn.swm.common.annotation.SystemControllerLog;
import cn.swm.mapper.TbUserMapper;
import cn.swm.pojo.TbPermission;
import cn.swm.pojo.TbRole;
import cn.swm.pojo.TbUser;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.service.UserService;
import cn.swm.utils.GeetestLib;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
@Api(description = "管理员管理")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/geetestInit",method = RequestMethod.GET)
    public String geetesrInit(HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id,GeetestLib.key,GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String,String> param = new HashMap<>();
        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);
        //将服务器状态设置到session中
        request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey,gtServerStatus);

        resStr = gtSdk.getResponseStr();

        return resStr;
    }

    /**
     *                      username: name,
     *                     password: pass,
     *                     challenge: result.geetest_challenge,
     *                     validate: result.geetest_validate,
     *                     seccode: result.geetest_seccode
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @SystemControllerLog(description = "登陆系统")
    public Result<Object> login(String username, String password,
                                String challenge, String validate, String seccode,
                                HttpServletRequest request){
        //极验验证
        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        //从session中获取gt-server状态
        int gt_server_status = (int) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);

        //自定义参数,可选择添加
        HashMap<String,String> param = new HashMap<>();

        int gtResult = 0;

        if(gt_server_status == 1){
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge,validate,seccode,param);
            System.out.println("gt-server正常，向gt-server进行二次验证--"+gtResult);
        }else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge,validate,seccode);
            System.out.println("gt-server非正常情况下，进行failback模式验证"+gtResult);
        }

        if(gtResult == 1){
            // 验证成功
            Subject subject = SecurityUtils.getSubject();
            //MD5加密
            String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
            UsernamePasswordToken token = new UsernamePasswordToken(username,md5Pass);
            try {
                subject.login(token);
                //shiro中默认存储在session中的过期时间是30分钟，我现在将它更改下,表示永远不过期
                SecurityUtils.getSubject().getSession().setTimeout(-1000L);
                return new ResultUtil<Object>().setData(null);
            }catch (Exception e){
                return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
            }
        }else {
            //验证失败
            return new ResultUtil<Object>().setErrorMsg("验证失败");
        }
    }

    /*@RequestMapping(value = "/user/login",method = RequestMethod.GET)
    public String test(){
        return "hello";
    }*/

    @RequestMapping(value = "/user/userInfo",method = RequestMethod.GET)
    public Result<TbUser> getUserInfo(){
        String username = SecurityUtils.getSubject().getPrincipal().toString();
        TbUser tbUser = userService.getUserByUsername(username);
        tbUser.setPassword(null);
        return new ResultUtil<TbUser>().setData(tbUser);
    }

    @RequestMapping(value = "/user/logout",method = RequestMethod.GET)
    public Result<Object> logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/roleList",method = RequestMethod.GET)
    public DataTableResult getRoleList(){
        return userService.getRoleList();
    }

    @RequestMapping(value = "/user/roleCount",method = RequestMethod.GET)
    public Result<Object> getRoleCount(){
        Long count = userService.getRoleCount();
        return new ResultUtil<Object>().setData(count);
    }

    @RequestMapping(value = "/user/permissionList",method = RequestMethod.GET)
    public DataTableResult getPermsList(){
        return userService.getPermsList();
    }

    /*@RequestMapping(value = "/user/edit/roleName/{roleId}",method = RequestMethod.GET)
    public boolean isRoleName(@PathVariable("roleId")int roleId,String name){
        return userService.isRoleName(roleId,name);
    }*/

    @RequestMapping(value = "/user/updateRole",method = RequestMethod.POST)
    public Result<Object> updateRolePerms(@ModelAttribute TbRole tbRole){
        userService.updateRolePerms(tbRole);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delRole/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deleteRoleById(@PathVariable("ids")int[] ids){
        for(int id : ids){
            if(userService.deleteRole(id)!=1){
               return new ResultUtil<Object>().setErrorMsg("id为"+id+"的角色正在被使用中，不能删除");
            }
        }

        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/roleName",method = RequestMethod.GET)
    public boolean isRoleName(String name){
        return userService.isRoleName(name);
    }

    @RequestMapping(value = "/user/addRole",method = RequestMethod.POST)
    public Result<Object> addRole(@ModelAttribute TbRole tbRole){
        userService.addRole(tbRole);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/permissionCount",method = RequestMethod.GET)
    public Result<Object> getPermissonCount(){
        Long count = userService.getPermissonCount();
        return new ResultUtil<Object>().setData(count);
    }

    @RequestMapping(value = "/user/addPermission",method = RequestMethod.POST)
    public Result<Object> addPermission(@ModelAttribute TbPermission tbPermission){
        userService.addPermission(tbPermission);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updatePermission",method = RequestMethod.POST)
    public Result<Object> updatePermission(@ModelAttribute TbPermission tbPermission){
        userService.updatePermission(tbPermission);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delPermission/{ids}",method = RequestMethod.GET)
    public Result<Object> deletepermission(@PathVariable("ids")int[] ids){
       for(int id : ids){
           userService.deletepermission(id);
       }
       return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/userList",method = RequestMethod.GET)
    public DataTableResult getUserList(){
        return userService.getUserList();
    }

    @RequestMapping(value = "/user/userCount",method = RequestMethod.GET)
    public Result<Object> getUserCount(){
        Long count = userService.getUserCount();
        return new ResultUtil<Object>().setData(count);
    }

    @RequestMapping(value = "/user/getAllRoles",method = RequestMethod.GET)
    public Result<List<TbRole>> getAllRoles(){
        List<TbRole> list = userService.getAllRoles();
        return new ResultUtil<List<TbRole>>().setData(list);
    }

    @RequestMapping(value = "/user/username",method = RequestMethod.GET)
    public boolean isUserName(String name){
        return userService.isUserName(name);
    }

    @RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    public Result<Object> addUser(@ModelAttribute TbUser tbUser){
        userService.addUser(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/delUser/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deleteUser(@PathVariable("ids")long[] ids){
        for(long id : ids){
            userService.deleteUser(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/updateUser",method = RequestMethod.POST)
    public Result<Object> updateUser(@ModelAttribute TbUser tbUser){
        userService.updateUser(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/changePass",method = RequestMethod.POST)
    public Result<Object> updateAdminPassword(@ModelAttribute TbUser tbUser){
        userService.updateAdminPassword(tbUser);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/stop/{id}",method = RequestMethod.PUT)
    public Result<Object> stopUser(@PathVariable("id")long id){
        userService.updateStateUser(id,0);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/user/start/{id}",method = RequestMethod.PUT)
    public Result<Object> startUser(@PathVariable("id")long id){
        userService.updateStateUser(id,1);
        return new ResultUtil<Object>().setData(null);
    }
}
