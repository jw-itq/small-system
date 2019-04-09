package cn.swm.service;

import cn.swm.pojo.front.Cart;
import cn.swm.pojo.front.CartProduct;

import java.util.List;

public interface CartService {

    /*
    private Long userId;

    private Long productId;

    private String checked;

    private int productNum;
     */

    int addCart(long userId,long itemId,int num);

    int deleteCartItem(long userId,long itemId);

    int editCheckAll(long userId,String checked);

    int cartEdit(long userId,long productId,String checked,int productNum);

    int delChecked(long userId);

    List<CartProduct> getCartList(long userId);
}
