package com.team.ShopSystem.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.team.ShopSystem.sys.entity.Cart;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Zhong Siqi
 * @Description: TODO
 * @DateTime: 2023/4/9 20:49
 **/
public interface CartMapper extends BaseMapper<Cart> {
    Integer getCartByUserId(@Param("userId") Integer userId);
}
