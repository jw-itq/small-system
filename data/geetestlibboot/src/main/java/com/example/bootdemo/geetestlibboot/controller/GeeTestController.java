package com.example.bootdemo.geetestlibboot.controller;

import com.example.bootdemo.geetestlibboot.jedis.JedisClient;
import com.example.bootdemo.geetestlibboot.pojo.Member;
import com.example.bootdemo.geetestlibboot.pojo.MemberLoginRegist;
import com.example.bootdemo.geetestlibboot.pojo.Result;
import com.example.bootdemo.geetestlibboot.pojo.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import com.google.gson.Gson;

import java.util.UUID;

@RestController
public class GeeTestController {

    public GeeTestController(){
        System.out.println("已经扫描到了这个controller类");
    }

    @Autowired
    private JedisClient jedisClient;

    @Value("1800")
    private Integer SESSION_EXPIRE;

    @RequestMapping(value = "/membertest/geetestInit",method = RequestMethod.GET)
    public String geetesrInit(HttpServletRequest request){

        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        String resStr = "{}";

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        //进行验证预处理
        int gtServerStatus = gtSdk.preProcess(param);

        //将服务器状态设置到redis中
        //request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
        String key = UUID.randomUUID().toString();
        System.out.println(key);
        jedisClient.set(key,gtServerStatus+"");
        jedisClient.expire(key,360);

        resStr = gtSdk.getResponseStr();
        GeetInit geetInit = new Gson().fromJson(resStr,GeetInit.class);
        geetInit.setStatusKey(key);
        return new Gson().toJson(geetInit);
    }

    @RequestMapping(value = "/membertest/login",method = RequestMethod.POST)
    public Result<Member> login(@RequestBody MemberLoginRegist memberLoginRegist,
                                HttpServletRequest request){

        //极验验证
        GeetestLib gtSdk = new GeetestLib(GeetestLib.id, GeetestLib.key,GeetestLib.newfailback);

        String challenge=memberLoginRegist.getChallenge();
        String validate=memberLoginRegist.getValidate();
        String seccode=memberLoginRegist.getSeccode();

        //从redis中获取gt-server状态
        //int gt_server_status_code = (Integer) request.getSession().getAttribute(gtSdk.gtServerStatusSessionKey);
        int gt_server_status_code = Integer.parseInt(jedisClient.get(memberLoginRegist.getStatusKey()));

        //自定义参数,可选择添加
        HashMap<String, String> param = new HashMap<String, String>();

        int gtResult = 0;

        if (gt_server_status_code == 1) {
            //gt-server正常，向gt-server进行二次验证
            gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, param);
            System.out.println(gtResult);
        } else {
            // gt-server非正常情况下，进行failback模式验证
            System.out.println("failback:use your own server captcha validate");
            gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
            System.out.println(gtResult);
        }

        Member member=new Member();
        if (gtResult == 1) {
            // 验证成功

            String token = UUID.randomUUID().toString();
            member.setId((long) 321);
            member.setUsername("s");
            member.setState(1);
            member.setToken(token);
            // 用户信息写入redis：key："SESSION:token" value："user"
            jedisClient.set("SESSION:" + token, new Gson().toJson(member));
            jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        }
        else {
            // 验证失败
            member.setState(0);
            member.setMessage("验证失败");
        }
        return new ResultUtil<Member>().setData(member);
    }
}
