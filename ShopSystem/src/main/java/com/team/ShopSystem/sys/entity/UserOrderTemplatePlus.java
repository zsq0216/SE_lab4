package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
/**
 * 临时订单,加了一些属性方便前端显示
 * @author xby
 * @since 2023-05-09
 */
@Data
@AllArgsConstructor
public class UserOrderTemplatePlus {
    private UserOrderTemplate userOrderTemplate;
    private Shop shop;
    private Goods goods;
    private Event event;
}
