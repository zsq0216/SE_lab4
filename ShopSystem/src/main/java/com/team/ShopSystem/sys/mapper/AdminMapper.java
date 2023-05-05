package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-04-15
 */
public interface AdminMapper extends BaseMapper<Admin> {
    Admin get();
}
