package com.team.ShopSystem.sys.entity;

import lombok.*;
/**
 *商品类，加了一些属性方便前端显示
 * @author xby
 * @since 2023-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoodsPlus {
    private Goods goods;
    private Event event;
}
