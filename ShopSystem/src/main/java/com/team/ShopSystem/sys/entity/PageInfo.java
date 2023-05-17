package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageInfo {
    List<UserOrderPlus> userOrderPlusList;

    Long total;
    Long pages;
}
