package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.MerchantOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-06
 */
public interface MerchantOrderMapper extends BaseMapper<MerchantOrder> {
    MerchantOrder selectByUserOrderId(@Param("orderId") Integer id);
}
