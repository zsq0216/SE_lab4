package com.team.ShopSystem.sys.service;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.MerchantOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.team.ShopSystem.sys.entity.UserOrder;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xby
 * @since 2023-05-06
 */
public interface IMerchantOrderService extends IService<MerchantOrder> {
    Result<?> pay(UserOrder userOrder);
}
