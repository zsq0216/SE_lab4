package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * @Author: Zhong Siqi
 * @Description: 商品类
 * @DateTime: 2023/4/4 13:51
 **/

@TableName("ss_goods")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Goods {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer shopId;
    private String goodsName;

    @TableField(exist = false)
    private List<String> image;

    private String description;

    private float price;

    private Integer status;
}
