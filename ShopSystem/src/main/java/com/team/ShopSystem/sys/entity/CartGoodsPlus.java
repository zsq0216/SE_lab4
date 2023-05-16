package com.team.ShopSystem.sys.entity;

import lombok.*;

/**
 * @Author: Zhong Siqi
 * @Description: 这个是用来在购物车里显示商品信息的
 * @DateTime: 2023/4/18 16:44
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartGoodsPlus{
    private Goods goods;

    private CartGoods cartGoods;
    private Event event;
}
