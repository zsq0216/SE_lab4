package com.team.ShopSystem.sys.service;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Shop;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
public interface IShopService extends IService<Shop> {

    Result<?> addShop(Shop shop);

    Result<?> approveShop(Shop shop);

    Result<?> disapproveShop(Shop shop);

    Result<List<Shop>> showUserShop(Integer userId);

    Result<?> deleteShop(Shop shop);

    Result<?> approveDeleteShop(Shop shop);
}
