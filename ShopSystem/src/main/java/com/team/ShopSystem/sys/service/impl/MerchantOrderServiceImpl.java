package com.team.ShopSystem.sys.service.impl;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.MerchantOrder;
import com.team.ShopSystem.sys.entity.UserOrder;
import com.team.ShopSystem.sys.mapper.MerchantOrderMapper;
import com.team.ShopSystem.sys.service.IMerchantOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xby
 * @since 2023-05-06
 */
@Service
public class MerchantOrderServiceImpl extends ServiceImpl<MerchantOrderMapper, MerchantOrder> implements IMerchantOrderService {
    @Autowired
    MerchantOrderMapper merchantOrderMapper;

    @Override
    public Result<?> pay(UserOrder userOrder) {
        merchantOrderMapper.insert(new MerchantOrder(userOrder.getId(),userOrder.getGoodsId(),userOrder.getUnitPrice(),userOrder.getQuantity(),userOrder.getTotalPrice(),userOrder.getDate(),userOrder.getAddressId()));
        return Result.success("成功向商户订单表插入一条数据");
    }
}
