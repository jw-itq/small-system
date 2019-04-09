package cn.swm.service.impl;

import cn.swm.common.jedis.JedisClient;
import cn.swm.mapper.TbItemMapper;
import cn.swm.pojo.TbItem;
import cn.swm.pojo.front.Cart;
import cn.swm.pojo.front.CartProduct;
import cn.swm.service.CartService;
import cn.swm.utils.DtoUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Value("${CART_PRE}")
    private String CART_PRE;

    /**
     * 添加购物车的流程；；；
     * 1,首先去缓存中去查找，如果能找到，就把存在的购物车的商品的数量进行相加，
     * 2，如果不能找到就去数据库中查找对应的商品信息，将这个商品的信息和数量存储到redis缓存中，
     *      在这个其中就包含有一种情况，就是并发的情况，比如多个用户操作同一个商品的时候，
     *      这个时候，就用到了redis里面哈希值存储的特性，这个里面可以根据用户id来区分大的类别，然后根据具体的id就找到了商品id了
     *      所以这里就必须使用redis的hash存储
     *
     * @param userId 用户的id
     * @param itemId 商品的id
     * @param num 选择商品的数量
     * @return
     */
    @Override
    public int addCart(long userId, long itemId, int num) {
        //查询缓存中是否存在
        //hash: "key:用户id" field:"商品id" value:"商品信息"
        //如果哈希表含有给定字段，返回 1 。 如果哈希表不含有给定字段，或 key 不存在，返回 0
        Boolean hexists = jedisClient.hexists(CART_PRE+":"+userId,itemId+"");
        //如果存在那么就数量上的相加
        if(hexists){
            String json = jedisClient.hget(CART_PRE+":"+userId,itemId+"");
            if(json!=null){
                CartProduct cartProduct = new Gson().fromJson(json,CartProduct.class);
                cartProduct.setProductNum(cartProduct.getProductNum()+num);
                jedisClient.hset(CART_PRE+":"+userId,itemId+"",new Gson().toJson(cartProduct));
            }else {
                return 0;
            }
            return 1;
        }

        //如果不存在，就去数据库里面查询到商品信息了，然后再添加缓存
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        if(tbItem==null){
            return 0;
        }

        //我要存储的是cartproduct对象，所以这里需要转换一下
        CartProduct cartProduct = DtoUtil.TbItem2CartProduct(tbItem);
        cartProduct.setProductNum((long) num);
        cartProduct.setChecked("1");

        //存储到缓存中去
        jedisClient.hset(CART_PRE+":"+userId,itemId+"",new Gson().toJson(cartProduct));
        return 0;
    }

    /**
     * 删除用户购物车里面的数据,其实就是删除缓存,购物车就是放在缓存里面，一旦我提交订单的时候，应该说也要删除一次缓存
     *
     * @param userId
     * @param itemId
     * @return
     */
    @Override
    public int deleteCartItem(long userId,long itemId) {
        //应该可以直接删除调redis里面的缓存
        try {
            jedisClient.hdel(CART_PRE+":"+userId,itemId+"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 改变缓存中是否要全选的商品，反正就是要改变所有的checked状态
     * @param userId 用户的id
     * @param checked true表示全选，false表示全都不选
     * @return
     */
    @Override
    public int editCheckAll(long userId, String checked) {
        //hval 会返回redis哈希表中所有的域
        List<String> jsonList = jedisClient.hvals(CART_PRE+":"+userId);

        for(String json : jsonList){
            CartProduct cartProduct = new Gson().fromJson(json,CartProduct.class);
            if(checked.equals("true")){
                cartProduct.setChecked(1+"");
            }else if(checked.equals("false")){
                cartProduct.setChecked(0+"");
            }else {
                return 0;
            }
            jedisClient.hset(CART_PRE+":"+userId,cartProduct.getProductId()+"",new Gson().toJson(cartProduct));
        }
        return 1;
    }

    /**
     * 修改购物车缓存，
     * 为什么会有这个方法呢，就是因为在购物车列表中，用户随时会取消/或者改变购物数量，
     * 这个时候就一定要跟后台的redis缓存做同步
     * @param userId 用户的id，用户是redis缓存中的域对象，
     * @param productId 商品的id 它是域对象里面的一个属性，所以就能很方便存储对象
     * @param checked 表示这个商品是否有效，最后提交订单的时候，将抛弃状态为0的商品
     * @param productNum 商品的数量
     * @return
     */
    @Override
    public int cartEdit(long userId, long productId, String checked, int productNum) {
        String json = jedisClient.hget(CART_PRE+":"+userId,productId+"");
        if(json==null){
            return 0;
        }

        CartProduct cartProduct = new Gson().fromJson(json,CartProduct.class);
        cartProduct.setChecked(checked);
        cartProduct.setProductNum((long) productNum);
        jedisClient.hset(CART_PRE+":"+userId,productId+"",new Gson().toJson(cartProduct));
        return 1;
    }

    /**
     * 因为之前就已经在我勾选商品的时候改变了里面的状态，既然能够走到这里来，
     * 就说明我只需要晓得需要删除的用户的id就可以晓得我要删除的里面的状态了
     * @param userId 用户的id
     * @return
     */
    @Override
    public int delChecked(long userId) {

        List<String> jsonList = jedisClient.hvals(CART_PRE+":"+userId);

        for(String json : jsonList){
            CartProduct cartProduct = new Gson().fromJson(json,CartProduct.class);
            //这里是1就说明是选择的，就是勾选了的
            if(cartProduct.getChecked().equals("1")){
                jedisClient.hdel(CART_PRE+":"+userId,cartProduct.getProductId()+"");
            }
        }
        return 1;
    }

    /**
     * 获取购物商品列表的时候就是根据用户的id去缓存中去查询
     * @param userId 用户id
     * @return
     */
    @Override
    public List<CartProduct> getCartList(long userId) {
        try {
            List<String> list = jedisClient.hvals(CART_PRE+":"+userId);
            List<CartProduct> result = new ArrayList<>();
            for(String product : list){
                CartProduct cartProduct = new Gson().fromJson(product,CartProduct.class);
                result.add(cartProduct);
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
