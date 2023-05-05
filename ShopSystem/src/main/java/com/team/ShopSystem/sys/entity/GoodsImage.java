package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Zhong Siqi
 * @Description: 图片类
 * @DateTime: 2023/4/6 17:44
 **/
@TableName("ss_goods_image")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodsImage {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer goodsId;

    private String imageAddress;

    private Integer status;
}
