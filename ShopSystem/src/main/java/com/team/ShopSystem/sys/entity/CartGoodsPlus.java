package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Zhong Siqi
 * @Description: 这个是用来在购物车里显示商品信息的
 * @DateTime: 2023/4/18 16:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartGoodsPlus{
    private Goods goods;

    private CartGoods cartGoods;
    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public CartGoods getCartGoods() {
        return cartGoods;
    }

    public void setCartGoods(CartGoods cartGoods) {
        this.cartGoods = cartGoods;
    }
}
