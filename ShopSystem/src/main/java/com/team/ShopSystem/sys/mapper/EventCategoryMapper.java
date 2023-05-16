package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.EventCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
public interface EventCategoryMapper extends BaseMapper<EventCategory> {
    List<String> selectByEventId(@Param("eventId") Integer eventId);
}
