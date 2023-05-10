package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.EventFunds;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
public interface EventFundsMapper extends BaseMapper<EventFunds> {
    Float selectFundsByEventId(@Param("eventId")Integer eventId);
    EventFunds selectByEventId(@Param("eventId")Integer eventId);
}
