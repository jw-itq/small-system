package cn.swm.service.impl;

import cn.swm.common.exception.SmallException;
import cn.swm.mapper.TbOrderItemMapper;
import cn.swm.mapper.TbOrderMapper;
import cn.swm.mapper.TbOrderShippingMapper;
import cn.swm.pojo.*;
import cn.swm.pojo.common.DataTableResult;
import cn.swm.pojo.dto.OrderDetail;
import cn.swm.service.OrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    /**
     * 查询所有的订单
     * @return
     */
    @Override
    public DataTableResult getOrderList(int draw, int start, int length, String search, String orderCol, String orderDir) {
        DataTableResult result = new DataTableResult();

        PageHelper.startPage(start/length+1,length);
        result.setDraw(draw);
        List<TbOrder> list = tbOrderMapper.selectByMulti("%"+search+"%",orderCol,orderDir);

        PageInfo pageInfo = new PageInfo(list);
        result.setRecordsFiltered((int) pageInfo.getTotal());
        result.setRecordsTotal(cancelOrder());

        result.setData(list);
        return result;
    }

    /**
     * 获取订单的总数
     * @return
     */
    @Override
    public long getOrderCount() {
        TbOrderExample tbOrderExample = new TbOrderExample();
        Long result = tbOrderMapper.countByExample(tbOrderExample);
        if(result==null){
            throw new SmallException("获取订单总数失败");
        }
        return result;
    }

    /**
     * 订单发货
     * @param orderId 订单的id
     * @param shippingName 物流名字
     * @param shippingCode 物流编号
     * @param postFee 运费
     * @return
     */
    @Override
    public int orderDeliver(String orderId, String shippingName, String shippingCode, BigDecimal postFee) {
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        //之前忘记设置常量了 将就这样看吧 0、未付款，1、已付款，2、未发货，3、已发货，4、交易成功，5、交易关闭
        tbOrder.setStatus(3);
        tbOrder.setUpdateTime(new Date());
        tbOrder.setShippingName(shippingName);
        tbOrder.setShippingCode(shippingCode);
        tbOrder.setPostFee(postFee);
        tbOrder.setConsignTime(new Date());
        tbOrderMapper.updateByPrimaryKey(tbOrder);
        return 1;
    }

    /**
     * 添加订单的备注
     * @param orderId
     * @param message
     * @return
     */
    @Override
    public int orderRemark(String orderId, String message) {
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        tbOrder.setBuyerMessage(message);
        tbOrder.setUpdateTime(new Date());
        tbOrderMapper.updateByPrimaryKey(tbOrder);
        return 1;
    }

    /**
     * 取消订单,取消订单就是将订单的状态设置为5
     * @param orderId
     * @return
     */
    @Override
    public int cancelOrderAdmin(String orderId) {
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        tbOrder.setStatus(5);
        tbOrder.setUpdateTime(new Date());
        tbOrder.setCloseTime(new Date());
        tbOrderMapper.updateByPrimaryKey(tbOrder);
        return 1;
    }

    /**
     * 根据id删除订单的信息,要删除3张表的信息：订单表，订单商品表，订单物流表
     * @param orderId
     * @return
     */
    @Override
    public int deleteOrderById(String orderId) {
        if(tbOrderMapper.deleteByPrimaryKey(orderId)!=1){
            throw new SmallException("删除订单表失败");
        }

        TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = tbOrderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<TbOrderItem> list = tbOrderItemMapper.selectByExample(tbOrderItemExample);
        for(TbOrderItem tbOrderItem : list){
            if(tbOrderItemMapper.deleteByPrimaryKey(tbOrderItem.getId())!=1){
                throw new SmallException("删除订单商品表失败");
            }
        }

        if(tbOrderShippingMapper.deleteByPrimaryKey(orderId)!=1){
            throw new SmallException("删除订单物流表失败");
        }
        return 1;
    }

    /**
     * 根据订单的id获得订单的详细信息，包括订单表，订单对应的商品表，订单的物流表
     * @param orderId
     * @return
     */
    @Override
    public OrderDetail getOrderDetail(String orderId) {
        OrderDetail result = new OrderDetail();

        try {
            TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
            result.setTbOrder(tbOrder);

            TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
            List<TbOrderItem> orderItemList = tbOrderItemMapper.selectByExample(tbOrderItemExample);
            result.setTbOrderItem(orderItemList);

            TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(orderId);
            result.setTbOrderShipping(tbOrderShipping);
        }catch (Exception e){
            log.error(e.toString());
        }

        return result;
    }

    /**
     * 在查询的时候就判断所有的订单哪些应该要取消，超时没有支付的订单应该取消
     * @return
     */
    public int cancelOrder(){
        TbOrderExample tbOrderExample = new TbOrderExample();
        List<TbOrder> list = tbOrderMapper.selectByExample(tbOrderExample);
        for(TbOrder tbOrder : list){
            judgeOrder(tbOrder);
        }
        return 1;
    }

    /**
     * 判断订单是否超时未支付
     *
     *                         if(data==0){
     *                             return "<span class=\"label label-defant radius td-status\">待支付</span>";
     *                         }else if(data==1){
     *                             return "<span class=\"label label-warning radius td-status\">已支付</span>";
     *                         } else if(data==2){
     *                             return "<span class=\"label label-primary radius td-status\">待发货</span>";
     *                         }else if(data==3){
     *                             return "<span class=\"label label-secondary radius td-status\">已发货</span>";
     *                         }else if(data==4){
     *                             return "<span class=\"label label-success radius td-status\">交易成功</span>";
     *                         }else if(data==5){
     *                             return "<span class=\"label label-danger radius td-status\">交易关闭</span>";
     *                         }else if(data==6){
     *                             return "<span class=\"label label-defant radius td-status\">支付失败</span>";
     *                         }else{
     *                             return "<span class=\"label label-warning radius td-status\">其它态</span>";
     *                         }
     * @param tbOrder
     */
    private String judgeOrder(TbOrder tbOrder) {
        String result = null;
        //待支付状态
        if(tbOrder.getStatus()==0){
            //判断是否超过了一天
            long diff = System.currentTimeMillis()-tbOrder.getCreateTime().getTime();
            long days = diff/(1000*60*60*24);
            if(days>=1){
                //设置失效
                tbOrder.setStatus(5);
                tbOrder.setCloseTime(new Date());
                if(tbOrderMapper.updateByPrimaryKey(tbOrder)!=1){
                    throw new SmallException("设置订单失效失败");
                }
            }else {
                //返回到期的时间
                long time = tbOrder.getCreateTime().getTime()+1000*60*60*24;
                result = String.valueOf(time);
            }
        }
        return result;
    }
}
