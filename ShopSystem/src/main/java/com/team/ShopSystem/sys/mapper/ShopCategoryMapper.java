package com.team.ShopSystem.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.ShopSystem.sys.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: 连接商店和商品类别
 * @DateTime: 2023/3/17 23:41
 **/
public interface ShopCategoryMapper extends BaseMapper<ShopCategory> {
    List<String> getByShopId(@Param("shopId") Integer shopId);
}
