package cn.swm.service;

import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.dto.OrderDetail;

import java.math.BigDecimal;

public interface OrderService {

    DataTableResult getOrderList(int draw, int start, int length,String search,
                                 String orderCol, String orderDir);

    long getOrderCount();

    int orderDeliver(String orderId, String shippingName, String shippingCode, BigDecimal postFee);

    int orderRemark(String orderId, String message);

    int cancelOrderAdmin(String orderId);

    int deleteOrderById(String orderId);

    OrderDetail getOrderDetail(String orderId);
}
