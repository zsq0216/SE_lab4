package com.team.ShopSystem.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Cart;

/**
 * @Author: Zhong Siqi
 * @Description: TODO
 * @DateTime: 2023/4/7 21:36
 **/
public interface ICartService extends IService<Cart> {
    Result<?> add2Cart(Integer userId, Integer goodsId);

    Result<?> deleteSingle(Integer userId, Integer goodsId);

    Result<?> deleteBatch(Integer userId, Integer goodsId);

    Result<?> deleteAll(Integer userId);
}
