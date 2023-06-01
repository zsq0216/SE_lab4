package com.team.ShopSystem.sys.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
/**
*活动页
 */
@Data
@AllArgsConstructor
public class PageInfo {
    List<UserOrderPlus> userOrderPlusList;

    Long total;
    Long pages;
}
