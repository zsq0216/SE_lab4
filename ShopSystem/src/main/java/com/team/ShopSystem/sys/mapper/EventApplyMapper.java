package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.EventApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
public interface EventApplyMapper extends BaseMapper<EventApply> {
    Integer queryByShopId(@Param("shopId")Integer shopId);
}
