package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author: Zhong Siqi
 * @Description: 收货地址
 * @DateTime: 2023/5/9 10:30
 **/
@TableName("ss_delivery_address")
@Data
@NoArgsConstructor
@Setter
@Getter
public class DeliveryAddress {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private String name;

    private String phoneNumber;

    private String address;
}
