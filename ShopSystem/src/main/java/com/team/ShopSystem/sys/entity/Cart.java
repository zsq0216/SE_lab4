package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: Zhong Siqi
 * @Description: 购物车类
 * @DateTime: 2023/4/7 20:54
 **/
@TableName("ss_cart")
@Data
@AllArgsConstructor
public class Cart {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Float totalPrice;
}
