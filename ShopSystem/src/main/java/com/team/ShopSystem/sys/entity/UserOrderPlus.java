package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserOrderPlus {
    private UserOrder userOrder;
    private Shop shop;
    private Goods goods;
}
