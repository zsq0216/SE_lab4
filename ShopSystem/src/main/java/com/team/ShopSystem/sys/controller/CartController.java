package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.CartGoods;
import com.team.ShopSystem.sys.entity.CartGoodsPlus;
import com.team.ShopSystem.sys.entity.Goods;
import com.team.ShopSystem.sys.entity.User;
import com.team.ShopSystem.sys.mapper.CartGoodsMapper;
import com.team.ShopSystem.sys.mapper.CartMapper;
import com.team.ShopSystem.sys.mapper.GoodsImageMapper;
import com.team.ShopSystem.sys.mapper.GoodsMapper;
import com.team.ShopSystem.sys.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhong Siqi
 * @Description: 购物车api
 * @DateTime: 2023/4/7 21:07
 **/

@Api(tags = {"购物车接口列表"})
@RestController
@RequestMapping("/cart")
public class CartController {

    @Resource
    CartGoodsMapper cartGoodsMapper;

    @Resource
    GoodsMapper goodsMapper;

    @Autowired
    ICartService cartService;

    @Resource
    CartMapper cartMapper;
    @Resource
    GoodsImageMapper goodsImageMapper;
    @GetMapping("/showList")
    @ApiOperation("显示某用户购物车中的所有未下架商品")
    public Result<List<CartGoodsPlus>> showList(@RequestParam Integer userId){
        Integer cartId=cartMapper.getCartByUserId(userId);
        List<CartGoods> list = cartGoodsMapper.getByCartId(cartId);
        List<CartGoodsPlus> goodsList=new ArrayList<CartGoodsPlus>();
        for(CartGoods cartGoods:list){
            Goods goods=goodsMapper.getById(cartGoods.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(cartGoods.getGoodsId()));
            goodsList.add(new CartGoodsPlus(goods,cartGoods));
        }
        return Result.success(goodsList);
    }


    @PostMapping("/add2cart")
    @ApiOperation("添加商品到购物车")
    public Result<?> add2cart(@RequestParam Integer userId,@RequestParam Integer goodsId){
        return cartService.add2Cart(userId, goodsId);
    }

    @PutMapping("/deleteSingle")
    @ApiOperation("删除单个购物车商品")
    public Result<?> deleteSingle(@RequestParam Integer userId,@RequestParam Integer goodsId){
        return cartService.deleteSingle(userId, goodsId);
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation("删除同种购物车商品")
    public Result<?> deleteBatch(@RequestParam Integer userId,@RequestParam Integer goodsId){
        return cartService.deleteBatch(userId, goodsId);
    }

    @DeleteMapping("/deleteMulti")
    @ApiOperation("批量删除购物车商品(删除多种）")
    public Result<?> deleteMulti(@RequestParam Integer userId,@RequestParam List<Integer> goodsIdList){
        for(Integer goodsId:goodsIdList){
            cartService.deleteBatch(userId, goodsId);
        }
        return Result.success("删除成功");
    }

    @DeleteMapping("/deleteAll")
    @ApiOperation("清空购物车商品")
    public Result<?> deleteBatch(@RequestBody User user){
        return cartService.deleteAll(user.getId());
    }
}
