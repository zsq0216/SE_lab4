package com.team.ShopSystem.sys.service;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
public interface IUserService extends IService<User> {

    Result<?> login(User user);

    Result<?> addUser(User user);

    Result<?> updateUser(User user);
}
