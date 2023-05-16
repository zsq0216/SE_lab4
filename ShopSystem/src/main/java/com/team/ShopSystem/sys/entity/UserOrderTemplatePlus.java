package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOrderTemplatePlus {
    private UserOrderTemplate userOrderTemplate;
    private Shop shop;
    private Goods goods;
}
