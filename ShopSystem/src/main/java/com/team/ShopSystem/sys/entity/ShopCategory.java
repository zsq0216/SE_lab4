package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Zhong Siqi
 * @Description: 连接商店和商品类别
 * @DateTime: 2023/3/17 23:29
 **/

@TableName("ss_shop_category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCategory {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String categoryName;

    private Integer shopId;
}
