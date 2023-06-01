package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 *用户订单，加了一些属性方便前端显示
 * @author xby
 * @since 2023-05-03
 */
@Data
@AllArgsConstructor
public class UserOrderPlus {
    private UserOrder userOrder;
    private Shop shop;
    private Goods goods;
    private DeliveryAddress deliveryAddress;
}
