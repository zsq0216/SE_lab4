package com.team.ShopSystem.sys.entity;

import lombok.*;
/**
 *用户订单
 * @author xby
 * @since 2023-05-03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventApplyPlus {
    private EventApply eventApply;
    private Event event;
    private Shop shop;
}
