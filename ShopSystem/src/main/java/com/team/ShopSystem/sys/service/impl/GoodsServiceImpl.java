package com.team.ShopSystem.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Goods;
import com.team.ShopSystem.sys.entity.GoodsImage;
import com.team.ShopSystem.sys.entity.GoodsUpdate;
import com.team.ShopSystem.sys.mapper.*;
import com.team.ShopSystem.sys.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Zhong Siqi
 * @Description: 商品类
 * @DateTime: 2023/4/4 14:03
 **/
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CartGoodsMapper cartGoodsMapper;

    @Autowired
    GoodsImageMapper goodsImageMapper;

    @Resource
    GoodsUpdateMapper goodsUpdateMapper;

    @Override
    public Result<?> addGoodsApply(Goods goods,Integer shopId){
        goods.setShopId(shopId);
        goods.setStatus(0);
        goodsMapper.insertGoods(goods);
        for(String image:goods.getImage()){
            goodsImageMapper.insert(new GoodsImage(null,goods.getId(),image,0));
        }
        return Result.success("等待管理员审核");
    }

    @Override
    public Result<?> removeGoods(Integer goodsId){
        goodsMapper.updateStatus(goodsId,5);
        if(cartGoodsMapper.queryByGoodsId(goodsId)>0){
            cartGoodsMapper.setRemoved(goodsId);
        }
        return Result.success("下架成功");
    }

    @Override
    public Result<?> addGoodsApprove(Integer goodsId){
        goodsMapper.updateStatus(goodsId,1);
        return Result.success("审核通过");
    }

    @Override
    public Result<?> addGoodsReject(Integer goodsId) {
        goodsMapper.updateStatus(goodsId,2);
        return Result.success("已拒绝上架申请");
    }

    @Override
    public Result<?> updateGoodsApply(Integer goodsId,GoodsUpdate newGoods){
        newGoods.setId(goodsId);
        if(goodsUpdateMapper.queryByGoodsId(goodsId)>0){
            return Result.fail(MsgEnum.FAIL);
        }
        goodsUpdateMapper.insertGoods(newGoods);
        for(String image:newGoods.getImage()){
            goodsImageMapper.insert(new GoodsImage(null,newGoods.getId(),image,1));
        }
        goodsMapper.updateStatus(goodsId,7);
        return Result.success("等待管理员审核");
    }

    @Override
    public Result<?> updateGoodsApprove(Integer goodsId){
        GoodsUpdate goodsUpdate=goodsUpdateMapper.getById(goodsId);
        Goods goods=goodsUpdate2goods(goodsUpdate);
        goodsMapper.updateById(goods);
        goodsUpdateMapper.deleteById(goodsUpdate);
        goodsImageMapper.deleteByGoodsId(goodsId,0);
        goodsImageMapper.updateStatus(goodsId,0);
        return Result.success("修改信息成功");
    }
    @Override
    public Result<?> updateGoodsReject(Integer goodsId) {
        goodsUpdateMapper.deleteByGoodsId(goodsId);
        goodsMapper.updateStatus(goodsId,9);
        goodsImageMapper.deleteByGoodsId(goodsId,1);
        return Result.success("已拒绝修改申请");
    }

    private Goods goodsUpdate2goods(GoodsUpdate goodsUpdate){
        Goods goods=new Goods();
        goods.setId(goodsUpdate.getId());
        goods.setGoodsName(goodsUpdate.getGoodsName());
        goods.setShopId(goodsUpdate.getShopId());
        goods.setDescription(goodsUpdate.getDescription());
        goods.setPrice(goodsUpdate.getPrice());
        goods.setStatus(8);
        return goods;
    }

}
