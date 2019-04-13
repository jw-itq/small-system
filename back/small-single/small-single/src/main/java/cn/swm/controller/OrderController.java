package cn.swm.controller;

import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.common.Result;
import cn.swm.pojo.dto.OrderDetail;
import cn.swm.service.OrderService;
import cn.swm.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order/list",method = RequestMethod.GET)
    public DataTableResult getOrderList(int draw, int start, int length,@RequestParam("search[value]") String search,
                                        @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir){

        //获取客户端需要排序的列
        String[] cols = {"checkbox","order_id", "payment","buyer_nick", "shipping_code","buyer_message", "create_time", "payment_time", "close_time","end_time","status"};
        String orderColume = cols[orderCol];
        if(orderColume==null){
            orderColume = "create_time";
        }

        if(orderDir==null){
            orderDir = "desc";
        }

        return orderService.getOrderList(draw,start,length,search,orderColume,orderDir);
    }

    @RequestMapping(value = "/order/count",method = RequestMethod.GET)
    public Result<Object> getOrderCount(){
        Long result = orderService.getOrderCount();
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/order/countState/{state}",method = RequestMethod.GET)
    public Result<Object> getOrderCountState(@PathVariable(value = "state")int state){
        int result = orderService.getOrderCountState(state);
        return new ResultUtil<Object>().setData(result);
    }

    @RequestMapping(value = "/order/deliver",method = RequestMethod.POST)
    public Result<Object> orderDeliver(@RequestParam String orderId,
                                       @RequestParam String shippingName,
                                       @RequestParam String shippingCode,
                                       @RequestParam BigDecimal postFee){
        orderService.orderDeliver(orderId,shippingName,shippingCode,postFee);
        return new ResultUtil<Object>().setData(null);
    }

    /**
     *                  orderId: parent.orderId,
     *                 message: $("#remark").val()
     * @return
     */
    @RequestMapping(value = "/order/remark",method = RequestMethod.POST)
    public Result<Object> orderRemark(@RequestParam String orderId,@RequestParam String message){
        orderService.orderRemark(orderId,message);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/order/cancel/{id}",method = RequestMethod.GET)
    public Result<Object> cancelOrder(@PathVariable("id")String id){
        orderService.cancelOrderAdmin(id);
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/order/del/{ids}",method = RequestMethod.DELETE)
    public Result<Object> deleteOrder(@PathVariable("ids")String[] ids){
        for(String id : ids){
            orderService.deleteOrderById(id);
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/order/detail/{orderId}",method = RequestMethod.GET)
    public Result<Object> getOrderDetail(@PathVariable("orderId")String orderId){
        OrderDetail orderDetail = orderService.getOrderDetail(orderId);
        return new ResultUtil<Object>().setData(orderDetail);
    }
}
