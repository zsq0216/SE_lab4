package com.team.ShopSystem.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.ShopSystem.sys.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: TODO
 * @DateTime: 2023/4/4 14:32
 **/
public interface GoodsMapper extends BaseMapper<Goods> {
    Integer updateStatus(@Param("id")Integer id,@Param("status") Integer status);

    Integer insertGoods(Goods goods);
    List<Goods> getByStatus(@Param("status") Integer status);

    List<Goods> getByShopId(@Param("shopId") Integer shopId);

    List<Goods> getByShopStatus(@Param("shopId")Integer shopId,@Param("status") Integer status);

    Goods getById(@Param("id") Integer id);
}
