package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Zhong Siqi
 * @Description: 购物车商品关系
 * @DateTime: 2023/4/9 20:20
 **/
@TableName("ss_cart_goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartGoods {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    protected Integer cartId;

    protected Integer goodsId;

    protected Integer goodsQuantity;

    protected Boolean isRemoved;

    public CartGoods(Integer cartId, Integer goodsId) {
        this.cartId=cartId;
        this.goodsId=goodsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer cartGoodsId) {
        this.id = cartGoodsId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsQuantity() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(Integer goodsQuantity) {
        this.goodsQuantity = goodsQuantity;
    }

    public Boolean getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Boolean removed) {
        isRemoved = removed;
    }

}
