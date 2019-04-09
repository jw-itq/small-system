package cn.swm.service;

import cn.swm.pojo.TbThanks;
import cn.swm.pojo.front.Order;
import cn.swm.pojo.front.OrderInfo;
import cn.swm.pojo.front.PageOrder;

public interface OrderFrontService {

    long addOrder(OrderInfo orderInfo);

    Order getOrder(String orderId);

    String payOrder(TbThanks tbThanks);

    int passPay(String tokenName, String token, String id, String sendType);

    PageOrder getOrderList(String userId,int page,int size);

    int delOrder(String orderId);

    int cancelOrder(Long orderId);
}
