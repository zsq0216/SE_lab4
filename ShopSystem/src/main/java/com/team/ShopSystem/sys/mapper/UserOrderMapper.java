package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-03
 */
public interface UserOrderMapper extends BaseMapper<UserOrder> {
    List<UserOrder> selectByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);
    List<UserOrder> selectByShopIdAndStatus(@Param("shopId") Integer shopId, @Param("status") Integer status);

}
