package cn.swm.controller;

import cn.swm.common.jedis.JedisClient;
import cn.swm.pojo.TbPay;
import cn.swm.pojo.TbThanks;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.front.Order;
import cn.swm.pojo.front.OrderInfo;
import cn.swm.pojo.front.PageOrder;
import cn.swm.service.OrderFrontService;
import cn.swm.utils.IPInfoUtil;
import cn.swm.utils.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderFrontController {

    @Autowired
    private OrderFrontService orderFrontService;

    @Autowired
    private JedisClient jedisClient;

    @RequestMapping(value = "/member/addOrder",method = RequestMethod.POST)
    public Result<Object> addOrder(@RequestBody OrderInfo orderInfo, HttpServletRequest request){

         /*
        防炸库验证，思路就是用用户的ip地址作为健存储在redis缓存中，并设置过期时间
        只要还能查询到这个值，那就说明还没有过期，就不能提交，
         */
        String ip = IPInfoUtil.getIpAddr(request);
        //这是ipv6本机的表现形式
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        String redisKey = "addOrder"+ip;
        String temp = jedisClient.get(redisKey);
        if(StringUtils.isNotBlank(temp)){
            return new ResultUtil<Object>().setErrorMsg("你提交的次数太频繁了，请两分钟后再提交，我的服务器要炸了");
        }

        //记录下缓存
        jedisClient.set(redisKey,"addOrder");
        jedisClient.expire(redisKey,120);

        long orderId = orderFrontService.addOrder(orderInfo);

        return new ResultUtil<Object>().setData(orderId);
    }

//    http://localhost:9999/member/orderDetail?orderId=155504705636469
    @RequestMapping(value = "/member/orderDetail",method = RequestMethod.GET)
    public Result<Order> getOrderDetail(String orderId){
        Order order = orderFrontService.getOrder(orderId);

        return new ResultUtil<Order>().setData(order);
    }

    @RequestMapping(value = "/member/payOrder",method = RequestMethod.POST)
    public Result<Object> payOrder(@RequestBody TbThanks tbThanks, HttpServletRequest request){
        /*
        防炸库验证，思路就是用用户的ip地址作为健存储在redis缓存中，并设置过期时间
        只要还能查询到这个值，那就说明还没有过期，就不能提交，
         */
        String ip = IPInfoUtil.getIpAddr(request);
        //这是ipv6本机的表现形式
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        String redisKey = "PayOrder"+ip;
        String temp = jedisClient.get(redisKey);
        if(StringUtils.isNotBlank(temp)){
            return new ResultUtil<Object>().setErrorMsg("你提交的次数太频繁了，请两分钟后再提交，我的服务器要炸了");
        }

        //记录下缓存
        jedisClient.set(redisKey,"addOrder");
        jedisClient.expire(redisKey,120);

        String payNum = orderFrontService.payOrder(tbThanks);

        return new ResultUtil<Object>().setData(payNum);
    }

    @RequestMapping(value = "/member/orderList",method = RequestMethod.GET)
    public Result<PageOrder> getOrderList(String userId,
                                          @RequestParam(defaultValue = "1")int page,
                                          @RequestParam(defaultValue = "5")int size){
        PageOrder pageOrder = orderFrontService.getOrderList(userId,page,size);
        return new ResultUtil<PageOrder>().setData(pageOrder);
    }

    @RequestMapping(value = "/member/delOrder",method = RequestMethod.GET)
    public Result<Object> delOrder(String orderId){
        int result = orderFrontService.delOrder(orderId);
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/member/cancelOrder",method = RequestMethod.POST)
    public Result<Object> cancelOrder(@RequestBody Order order){
        int result = orderFrontService.cancelOrder(order.getOrderId());
        return new ResultUtil<Object>().setData(result);
    }

}
