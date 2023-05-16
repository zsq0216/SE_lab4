package com.team.ShopSystem.sys.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GoodsPlus {
    private Goods goods;
    private Event event;
}
