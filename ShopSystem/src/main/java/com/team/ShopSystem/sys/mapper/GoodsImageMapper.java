package com.team.ShopSystem.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.ShopSystem.sys.entity.GoodsImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: 映射到存图片的表
 * @DateTime: 2023/4/6 17:35
 **/
public interface GoodsImageMapper extends BaseMapper<GoodsImage>{
    List<String> getByGoodsId(@Param("goodsId") Integer goodsId);

    List<String> getByUpdate(@Param("goodsId") Integer goodsId);

    void updateStatus(@Param("goodsId") Integer goodsId,@Param("status") Integer status);

    void deleteByGoodsId(@Param("goodsId") Integer goodsId,@Param("status") Integer status);
}