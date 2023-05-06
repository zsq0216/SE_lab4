package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.MerchantOrder;
import com.team.ShopSystem.sys.entity.UserOrder;
import com.team.ShopSystem.sys.mapper.MerchantOrderMapper;
import com.team.ShopSystem.sys.mapper.UserOrderMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-05-06
 */
@Api(tags = {"商户订单接口列表"})
@RestController
@RequestMapping("/merchantOrder")
public class MerchantOrderController {
    @Autowired
    UserOrderMapper userOrderMapper;
    @Autowired
    ConstantsProperties constants;
    @Autowired
    MerchantOrderMapper merchantOrderMapper;
    @ApiOperation("发货")
    @PutMapping("/ship")
    public Result<?> ship(@RequestParam Integer orderId){
        UserOrder userOrder = userOrderMapper.selectById(orderId);
        userOrder.setStatus(constants.getShipped());
        userOrderMapper.updateById(userOrder);
        MerchantOrder merchantOrder = merchantOrderMapper.selectByUserOrderId(orderId);
        merchantOrder.setStatus(constants.getShipped());
        merchantOrderMapper.updateById(merchantOrder);
        return Result.success("成功发货");
    }
}
