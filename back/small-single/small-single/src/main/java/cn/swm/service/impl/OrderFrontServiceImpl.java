package cn.swm.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.swm.common.exception.SmallException;
import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.*;
import cn.swm.pojo.*;
import cn.swm.pojo.front.CartProduct;
import cn.swm.pojo.front.Order;
import cn.swm.pojo.front.OrderInfo;
import cn.swm.pojo.front.PageOrder;
import cn.swm.service.OrderFrontService;
import cn.swm.utils.EmailUtil;
import cn.swm.utils.IDUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderFrontServiceImpl implements OrderFrontService {

    @Autowired
    private TbMemberMapper tbMemberMapper;

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Autowired
    private TbThanksMapper tbThanksMapper;

    @Autowired
    private EmailUtil emailUtil;

    @Value("${CART_PRE}")
    private String CART_PRE;

    @Value("${PAY_EXPIRE}")
    private Integer PAY_EXPIRE;

    @Value("${SERVER_URL}")
    private String SERVER_URL;

    @Value("${EMAIL_SENDER}")
    private String EMAIL_SENDER;

    /**
     * 添加订单表，添加订单商品表，添加订单物流信息表，删除提交订单中在购物车中的商品
     * @param orderInfo 订单数据
     * @return 返回一个订单号
     */
    @Override
    public long addOrder(OrderInfo orderInfo) {

        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(Long.valueOf(orderInfo.getUserId()));

        TbOrder tbOrder = new TbOrder();

        //生成订单号
        long orderId = IDUtil.getRandomId();
        tbOrder.setOrderId(String.valueOf(orderId));
        tbOrder.setUserId(tbMember.getId());
        tbOrder.setBuyerNick(tbMember.getUsername());
        tbOrder.setPayment(orderInfo.getOrderTotal());//总价格
        tbOrder.setCreateTime(new Date());
        tbOrder.setUpdateTime(new Date());
        //0：未付款 1：已付款 2：未发货 3：已发货 4：交易成功 5：交易失败
        tbOrder.setStatus(0);

        if(tbOrderMapper.insert(tbOrder)!=1){
            throw new SmallException("创建订单表的时候出错");
        }

        List<CartProduct> list = orderInfo.getGoodsList();
        for(CartProduct cartProduct : list){
            TbOrderItem tbOrderItem = new TbOrderItem();

            //生成订单商品id
            long orderItemId = IDUtil.getRandomId();
            tbOrderItem.setId(String.valueOf(orderItemId));
            tbOrderItem.setItemId(String.valueOf(cartProduct.getProductId()));
            tbOrderItem.setOrderId(String.valueOf(orderId));
            tbOrderItem.setNum(cartProduct.getProductNum().intValue());//商品购买的数量
            tbOrderItem.setPicPath(cartProduct.getProductImg());
            tbOrderItem.setPrice(cartProduct.getSalePrice());
            tbOrderItem.setTitle(cartProduct.getProductName());
            tbOrderItem.setTotalFee(cartProduct.getSalePrice().multiply(BigDecimal.valueOf(cartProduct.getProductNum())));//总价格

            if(tbOrderItemMapper.insert(tbOrderItem)!=1){
                throw new SmallException("创建订单商品id失败");
            }

            //删除购物车中含有该订单的商品
            try {
                List<String> jsonList = jedisClient.hvals(CART_PRE+":"+tbMember.getId());
                for(String json : jsonList){
                    CartProduct cart = new Gson().fromJson(json,CartProduct.class);
                    if(cart.getProductId().equals(cartProduct.getProductId())){
                        jedisClient.hdel(CART_PRE+":"+tbMember.getId(),cart.getProductId()+"");
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //物流信息表ordershipping
        TbOrderShipping tbOrderShipping = new TbOrderShipping();
        tbOrderShipping.setOrderId(String.valueOf(orderId));
        tbOrderShipping.setReceiverName(orderInfo.getUserName());
        tbOrderShipping.setReceiverAddress(orderInfo.getStreetName());
        tbOrderShipping.setReceiverPhone(orderInfo.getTel());
        tbOrderShipping.setCreated(new Date());
        tbOrderShipping.setUpdated(new Date());

        if(tbOrderShippingMapper.insert(tbOrderShipping)!=1){
            throw new SmallException("添加订单物流表失败");
        }

        //返回订单的id
        return orderId;
    }

    /**
     * 根据id查询订单的信息
     * @param orderId 订单的id
     * @return
     */
    @Override
    public Order getOrder(String orderId) {

        Order order = new Order();

        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(orderId);
        if(tbOrder==null){
            throw new SmallException("根据id获取订单表失败");
        }

        String validTime = judgeOrder(tbOrder);
        if(validTime!=null){
            order.setFinishDate(validTime);
        }

        //orderId
        order.setOrderId(Long.valueOf(tbOrder.getOrderId()));

        //orderStatus
        order.setOrderStatus(String.valueOf(tbOrder.getStatus()));

        //createDate
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String createDate = formatter.format(tbOrder.getCreateTime());
        order.setCreateDate(createDate);

        //payDate
        if(tbOrder.getPaymentTime()!=null){
            String payDate = formatter.format(tbOrder.getPaymentTime());
            order.setPayDate(payDate);
        }

        //closeDate
        if(tbOrder.getCloseTime()!=null){
            String closeDate = formatter.format(tbOrder.getCloseTime());
            order.setCloseDate(closeDate);
        }

        //finishDate
        if(tbOrder.getEndTime()!=null){
            String finishDate = formatter.format(tbOrder.getEndTime());
            order.setFinishDate(finishDate);
        }

        //address
        TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(orderId);
        TbAddress tbAddress = new TbAddress();
        tbAddress.setUserName(tbOrderShipping.getReceiverName());
        tbAddress.setTel(tbOrderShipping.getReceiverPhone());
        tbAddress.setStreetName(tbOrderShipping.getReceiverAddress());
        order.setAddressInfo(tbAddress);

        //orderTotal
        if(tbOrder.getPayment()==null){
            order.setOrderTotal(new BigDecimal(0));
        }else {
            order.setOrderTotal(tbOrder.getPayment());
        }

        //goodsList
        TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = tbOrderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<TbOrderItem> listItem = tbOrderItemMapper.selectByExample(tbOrderItemExample);
        List<CartProduct> listProduct = new ArrayList<>();

        for(TbOrderItem tbOrderItem : listItem){
            CartProduct cartProduct = new CartProduct();
            cartProduct.setProductName(tbOrderItem.getTitle());
            cartProduct.setProductId(Long.valueOf(tbOrderItem.getItemId()));
            cartProduct.setProductNum(Long.valueOf(tbOrderItem.getNum()));
            cartProduct.setSalePrice(tbOrderItem.getPrice());
            cartProduct.setProductImg(tbOrderItem.getPicPath());

            listProduct.add(cartProduct);
        }

        order.setGoodsList(listProduct);

        return order;
    }

    /**
     * 支付订单
     * @param tbThanks
     * @return
     */
    @Override
    public String payOrder(TbThanks tbThanks) {

        //设置主健
        long id = IDUtil.getRandomId();
        tbThanks.setId(id);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateFormat.format(new Date());
        tbThanks.setTime(time);
        //'显示状态 0待审核 1确认显示 2驳回 3通过不展示',
        tbThanks.setState(0);
        tbThanks.setDate(new Date());
        TbMember tbMember = tbMemberMapper.selectByPrimaryKey(Long.valueOf(tbThanks.getUserId()));
        if(tbMember!=null){
            tbThanks.setUsername(tbMember.getUsername());
        }
        //插入到数据库里面的的数据
        if(tbThanksMapper.insert(tbThanks)!=1){
            throw new SmallException("保存支付数据到数据库里面失败");
        }


        //设置订单为已经付款
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        tbOrder.setStatus(1);
        tbOrder.setUpdateTime(new Date());
        tbOrder.setPaymentTime(new Date());
        if(tbOrderMapper.updateByPrimaryKey(tbOrder)!=1){
            throw new SmallException("更新订单为已经付款的时候出错");
        }

        //这里我目前不知道有什么用
        //好吧，现在我知道了，不过这个四位随机标示我还是不知道，
        //先说说这个token吧，这个很重要，通过这个来验证用户是不是真的提交了支付，然后最后通过我来在邮箱中查看是否倒账
        //如果到帐了，我就可以进行发货等操作了，我在这里就只写一个功能吧
        String tokenName=UUID.randomUUID().toString();
        String token= UUID.randomUUID().toString();
        //这个我暂时不知道什么用，我暂时不会用
        /*if(tbThanks.getCustom()!=null&&tbThanks.getCustom()){
            //自定义金额生成四位数随机标识
            Random random = new Random();
            int end2 = random.nextInt(9999);
            //如果不足两位前面补0
            String str = String.format("%04d", end2);
            tbThanks.setPayNum(str);
        }else{
            // 从redis中取出num
            String key = "XMALL_XPAY_NUM_"+tbThanks.getPayType();//支付方式
            String value=jedisClient.get(key);
            // 初始化
            if(StringUtils.isBlank(value)){
                jedisClient.set(key,"0");
            }
            // 取出num
            String num  = String.valueOf(Integer.parseInt(jedisClient.get(key))+1);
            if(QRNUM.equals(Integer.valueOf(num))){
                jedisClient.set(key, "0");
            }else{
                // 更新记录num
                jedisClient.set(key, String.valueOf(num));
            }
            tbThanks.setPayNum(num);
        }*/
        //设置验证token键值对 tokenName:token
        jedisClient.set(tokenName,token);
        //过期时间是3天
        jedisClient.expire(tokenName,PAY_EXPIRE);

        //发送邮件通知确认
        //拼接邮件受到后的那个管理员界面的连接
        tbThanks = getAdminUrl(tbThanks,String.valueOf(tbThanks.getId()),tokenName,token);

        //完了之后就开始发邮件
        emailUtil.sendTemplateEmail(EMAIL_SENDER,"【SMall商城订单】待处理","email-admin",tbThanks);

        return tbThanks.getPayNum();
    }

    /**
     * 管理员邮箱显示的连接的调用方法，用来处理具体的订单，模拟回调，
     * 其实就是看有没有到帐，然后到帐了，就发获，反正方法执行只要能来到这里，就说明，这个订单已经结束了
     * @param tokenName 验证标示key
     * @param token 验证标示value
     * @param id 这是付款表的id
     * @param sendType 标示的是类型，其实在这里没有什么用
     * @return
     */
    @Override
    public int passPay(String tokenName, String token, String id, String sendType) {

        //验证tokenName
        if(StringUtils.isBlank(tokenName)||StringUtils.isBlank(token)){
            return -1;
        }

        //验证token
        String value = jedisClient.get(tokenName);
        if(!value.equals(token)){
            return -1;
        }

        //将tbthanks表里面的状态改变，其实没有，我这里没有，所以这个功能写注释
        TbThanks tbThanks = tbThanksMapper.selectByPrimaryKey(Long.valueOf(id));

        //将订单的状态设置为交易成功的状态
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(tbThanks.getOrderId());
        if(tbOrder!=null){
            tbOrder.setStatus(4);
            tbOrder.setEndTime(new Date());
            tbOrder.setUpdateTime(new Date());
            if(tbOrderMapper.updateByPrimaryKey(tbOrder)!=1){
                return 0;
            }
        }

        //完了之后给客户发送通知邮件
        if(StringUtils.isNotBlank(tbThanks.getEmail())&&EmailUtil.checkEmail(tbThanks.getEmail())){
            tbThanks.setTime(DateUtil.formatTime(tbThanks.getDate()));
            //这里本来要写状态判断，我这里不需要这个功能，忽略
            emailUtil.sendTemplateEmail(tbThanks.getEmail(),"【SMall商城】恭喜你，支付成功，请查看惊喜","pay-success",tbThanks);
        }
        return 1;
    }

    /**
     * 查询用户下面所有的订单，并分页
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageOrder getOrderList(String userId, int page, int size) {

        //分页
        if(page<=0){
            page = 1;
        }

        PageHelper.startPage(page,size);

        PageOrder pageOrder = new PageOrder();
        List<Order> list = new ArrayList<>();

        TbOrderExample tbOrderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = tbOrderExample.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userId));
        tbOrderExample.setOrderByClause("create_time DESC");
        List<TbOrder> tbOrderList = tbOrderMapper.selectByExample(tbOrderExample);

        for(TbOrder tbOrder : tbOrderList){

            //判断是不是超时间没有支付
            judgeOrder(tbOrder);

            Order order = new Order();
            //orderId
            order.setOrderId(Long.valueOf(tbOrder.getOrderId()));
            //orderStatus
            order.setOrderStatus(String.valueOf(tbOrder.getStatus()));
            //createDate
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String date = dateFormat.format(tbOrder.getCreateTime());
            order.setCreateDate(date);
            //address
            TbOrderShipping tbOrderShipping = tbOrderShippingMapper.selectByPrimaryKey(tbOrder.getOrderId());
            TbAddress tbAddress = new TbAddress();
            tbAddress.setUserName(tbOrderShipping.getReceiverName());
            tbAddress.setStreetName(tbOrderShipping.getReceiverAddress());
            tbAddress.setTel(tbOrderShipping.getReceiverPhone());
            order.setAddressInfo(tbAddress);
            //orderTotal
            if(tbOrder.getPayment()==null){
                order.setOrderTotal(new BigDecimal(0));
            }else {
                order.setOrderTotal(tbOrder.getPayment());
            }
            //goodsList
            TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
            TbOrderItemExample.Criteria criteria1 = tbOrderItemExample.createCriteria();
            criteria1.andOrderIdEqualTo(tbOrder.getOrderId());
            List<TbOrderItem> tbOrderItems = tbOrderItemMapper.selectByExample(tbOrderItemExample);
            List<CartProduct> cartProducts = new ArrayList<>();
            for(TbOrderItem tbOrderItem : tbOrderItems){
                CartProduct cartProduct = new CartProduct();
                cartProduct.setProductNum(Long.valueOf(tbOrderItem.getNum()));
                cartProduct.setProductImg(tbOrderItem.getPicPath());
                cartProduct.setProductId(Long.valueOf(tbOrderItem.getItemId()));
                cartProduct.setSalePrice(tbOrderItem.getPrice());
                cartProduct.setProductName(tbOrderItem.getTitle());

                cartProducts.add(cartProduct);
            }
            order.setGoodsList(cartProducts);
            list.add(order);
        }

        //分页
        PageInfo<Order> pageInfo = new PageInfo<>(list);
        pageOrder.setTotal(getMemberOrderCount(userId));
        pageOrder.setData(list);
        return pageOrder;
    }

    /**
     * 根据id删除订单，订单是唯一的，直接就可以删除
     * @param orderId
     * @return
     */
    @Override
    public int delOrder(String orderId) {

        if(tbOrderMapper.deleteByPrimaryKey(orderId)!=1){
            throw new SmallException("删除订单的时候出错");
        }

        TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
        TbOrderItemExample.Criteria criteria = tbOrderItemExample.createCriteria();
        criteria.andOrderIdEqualTo(orderId);
        List<TbOrderItem> list = tbOrderItemMapper.selectByExample(tbOrderItemExample);

        for(TbOrderItem tbOrderItem : list){
            if(tbOrderItemMapper.deleteByPrimaryKey(tbOrderItem.getId())!=1){
                throw new SmallException("删除订单商品表的时候出错");
            }
        }

        if(tbOrderShippingMapper.deleteByPrimaryKey(orderId)!=1){
            throw new SmallException("删除订单物流信息表的时候出错");
        }
        return 1;
    }

    /**
     * 取消订单，其实就是将订单的状态设置5，交易关闭就行了
     * @param orderId
     * @return
     */
    @Override
    public int cancelOrder(Long orderId) {
        TbOrder tbOrder = tbOrderMapper.selectByPrimaryKey(String.valueOf(orderId));
        if(tbOrder==null){
            throw new SmallException("根据id获取订单失败，在取消订单的时候");
        }
        tbOrder.setStatus(5);//交易关闭
        tbOrder.setCloseTime(new Date());
        if(tbOrderMapper.updateByPrimaryKey(tbOrder)!=1){
            throw new SmallException("取消订单失败");
        }
        return 1;
    }

    /**
     * 查询用户下面有多少个订单
     * @param userId
     * @return
     */
    private int getMemberOrderCount(String userId) {

        TbOrderExample tbOrderExample = new TbOrderExample();
        TbOrderExample.Criteria criteria = tbOrderExample.createCriteria();
        criteria.andUserIdEqualTo(Long.valueOf(userId));
        List<TbOrder> list = tbOrderMapper.selectByExample(tbOrderExample);
        if(list!=null){
            return list.size();
        }
        return 0;
    }

    /**
     * 拼接管理员的连接，是什么意思呢，就是给管理员发的邮件下面有操作的连接，需要在这里拼接
     * @param tbThanks 需要拼接的对象
     * @param id
     * @param tokenName
     * @param token
     * @return
     */
    public TbThanks getAdminUrl(TbThanks tbThanks,String id,String tokenName,String token){

        //审核通过
        String pass=SERVER_URL+"/pay/pass?sendType=0&tokenName="+tokenName+"&token="+token+"&id="+id;
        tbThanks.setPassUrl(pass);

        return tbThanks;
    }

    /**
     * 判断订单是不是超时没有支付,
     * 做法就是让当前时间减去创建订单的时间，要是大于1天了，那就是要设置状态关闭了
     * @param tbOrder 订单
     * @return 返回到期时间
     */
    public String judgeOrder(TbOrder tbOrder){

        String result = null;
        //0，表示未支付
        if(tbOrder.getStatus()==0){
            //获取时间差
            long diff = System.currentTimeMillis()-tbOrder.getCreateTime().getTime();
            //转换城天
            long days = diff/(1000*60*60*24);
            //判断是不是超过一天
            if(days>=1){
                //设置失效
                tbOrder.setStatus(5);
                tbOrder.setCloseTime(new Date());
                if(tbOrderMapper.updateByPrimaryKey(tbOrder)!=1){
                    throw new SmallException("设置订单失效的时候出错");
                }
            }else {
                //返回到期时间
                long time = tbOrder.getCreateTime().getTime()+1000*60*60*24;
                result = time+"";
            }
        }

        return result;
    }
}
