package cn.swm.controller;

import cn.swm.pojo.common.Result;
import cn.swm.utils.GeetestLib;
import cn.swm.utils.ResultUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(description = "管理员管理")
public class UserController {

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
            System.out.println(gtResult);
        }else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge,validate,seccode);
            System.out.println(gtResult);
        }

        if(gtResult == 1){
            // 验证成功
            Subject subject = SecurityUtils.getSubject();
            //MD5加密
            String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
            UsernamePasswordToken token = new UsernamePasswordToken(username,md5Pass);
            try {
                subject.login(token);
                return new ResultUtil<Object>().setData(null);
            }catch (Exception e){
                return new ResultUtil<Object>().setErrorMsg("用户名或密码错误");
            }
        }else {
            //
            return new ResultUtil<Object>().setErrorMsg("验证失败");
        }
    }
}
