package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.UserOrderTemplate;
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
public interface UserOrderTemplateMapper extends BaseMapper<UserOrderTemplate> {
    List<UserOrderTemplate> selectByTemplate(@Param("template") Integer template);
    UserOrderTemplate getTemplate();
}
