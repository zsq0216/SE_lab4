package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: TODO
 * @DateTime: 2023/4/10 21:31
 **/
@TableName("ss_goods_update")
@Data
@Getter
@Setter
public class GoodsUpdate{
    private Integer id;

    private Integer shopId;
    private String goodsName;

    @TableField(exist = false)
    private List<String> image;

    private String description;

    private float price;

    private Integer status;
}
