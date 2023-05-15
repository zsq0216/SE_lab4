package com.team.ShopSystem.sys.entity;

import lombok.*;

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
