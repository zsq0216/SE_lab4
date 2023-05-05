package com.team.ShopSystem.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.ShopSystem.sys.entity.GoodsUpdate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: 修改商品信息
 * @DateTime: 2023/4/10 10:59
 **/
public interface GoodsUpdateMapper extends BaseMapper<GoodsUpdate> {

    Integer insertGoods(GoodsUpdate goodsUpdate);
    GoodsUpdate getById(@Param("id") Integer id);

    Integer queryByGoodsId(@Param("id") Integer id);

    void deleteByGoodsId(@Param("id") Integer id);

    List<GoodsUpdate> getByShopId(@Param("shopId") Integer shopId);
}
