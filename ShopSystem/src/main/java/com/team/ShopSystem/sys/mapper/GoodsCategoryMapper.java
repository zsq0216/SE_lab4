package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.GoodsCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-07
 */
public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {
    List<String> getByGoodsId(@Param("goodsId") Integer goodsId);

    List<Integer> getByCategory(String keyword);
}
