package cn.swm.controller;

import cn.swm.common.jedis.JedisClient;
import cn.swm.pojo.TbAddress;
import cn.swm.pojo.common.Result;
import cn.swm.service.AddressService;
import cn.swm.utils.IPInfoUtil;
import cn.swm.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/member/addressList",method = RequestMethod.POST)
    public Result<List<TbAddress>> getAddressList(@RequestBody TbAddress tbAddress){
        List<TbAddress> result = addressService.getAddressList(tbAddress.getUserId());

        return new ResultUtil<List<TbAddress>>().setData(result);
    }

    @RequestMapping(value = "/member/updateAddress",method = RequestMethod.POST)
    public Result<Object> updateAddress(@RequestBody TbAddress tbAddress){
        int result = addressService.updateAddress(tbAddress);

        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/member/addAddress",method = RequestMethod.POST)
    public Result<Object> addressAdd(@RequestBody TbAddress tbAddress, HttpServletRequest request){

        /*
        防炸库验证，思路就是用用户的ip地址作为健存储在redis缓存中，并设置过期时间
        只要还能查询到这个值，那就说明还没有过期，就不能提交，
         */
        String ip = IPInfoUtil.getIpAddr(request);
        //这是ipv6本机的表现形式
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        String redisKey = "addAddress"+ip;
        String temp = jedisClient.get(redisKey);
        if(StringUtils.isNotBlank(temp)){
            return new ResultUtil<Object>().setErrorMsg("你提交的次数太频繁了，请两分钟后再提交，我的服务器要炸了");
        }

        //记录下缓存
        jedisClient.set(redisKey,"ADDED");
        jedisClient.expire(redisKey,120);

        int result = addressService.addressAdd(tbAddress);

        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/member/delAddress",method = RequestMethod.POST)
    public Result<Object> deleteAddressById(@RequestBody TbAddress tbAddress){
        int result = addressService.deleteAddressById(tbAddress);

        return new ResultUtil<Object>().setData(result);
    }


}
