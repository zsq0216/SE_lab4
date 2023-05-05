package com.team.ShopSystem.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Cart;
import com.team.ShopSystem.sys.entity.CartGoods;
import com.team.ShopSystem.sys.mapper.CartGoodsMapper;
import com.team.ShopSystem.sys.mapper.CartMapper;
import com.team.ShopSystem.sys.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Zhong Siqi
 * @Description: 购物车
 * @DateTime: 2023/4/7 21:41
 **/
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {
    @Autowired
    CartGoodsMapper cartGoodsMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public Result<?> add2Cart(Integer userId, Integer goodsId){
        Integer cartId=cartMapper.getCartByUserId(userId);
        if(cartGoodsMapper.queryById(cartId,goodsId)>0){
            cartGoodsMapper.updateQuantity(cartId,goodsId,1);
        }
        else{
            cartGoodsMapper.insert(new CartGoods(cartId,goodsId));
        }
        return Result.success("添加商品成功");
    }

    @Override
    public Result<?> deleteSingle(Integer userId, Integer goodsId){
        Integer cartId=cartMapper.getCartByUserId(userId);
        if(cartGoodsMapper.getQuantity(cartId,goodsId)==1){
            cartGoodsMapper.deleteBatch(cartId,goodsId);
        }
        cartGoodsMapper.updateQuantity(cartId,goodsId,-1);
        return Result.success("移除商品成功");
    }

    @Override
    public Result<?> deleteBatch(Integer userId, Integer goodsId){
        Integer cartId=cartMapper.getCartByUserId(userId);
        cartGoodsMapper.deleteBatch(cartId,goodsId);
        return Result.success("批量移除商品成功");
    }

    @Override
    public Result<?> deleteAll(Integer userId){
        Integer cartId=cartMapper.getCartByUserId(userId);
        cartGoodsMapper.deleteAll(cartId);
        return Result.success("清空购物车！");
    }

}
