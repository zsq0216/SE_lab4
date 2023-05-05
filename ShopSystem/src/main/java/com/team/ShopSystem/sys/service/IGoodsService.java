package com.team.ShopSystem.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Goods;
import com.team.ShopSystem.sys.entity.GoodsUpdate;

/**
 * @Author: Zhong Siqi
 * @Description: 商品
 * @DateTime: 2023/4/4 14:02
 **/
public interface IGoodsService extends IService<Goods> {


    Result<?> addGoodsApply(Goods goods,Integer shopId);

    Result<?> removeGoods(Integer goodsId);

    Result<?> addGoodsApprove(Integer goodsId);

    Result<?> updateGoodsApply(Integer goodsId, GoodsUpdate newGoods);

    Result<?> updateGoodsApprove(Integer goodsId);

    Result<?> addGoodsReject(Integer goodsId);

    Result<?> updateGoodsReject(Integer goodsId);
}
