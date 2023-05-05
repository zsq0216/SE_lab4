package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
public interface ShopMapper extends BaseMapper<Shop> {
    Integer queryByIdNumber(@Param("idNumber") String idNumber);

    Integer queryByShopName(@Param("shopName") String shopName);

    Integer updateStatus(@Param("id") int id,@Param("status") Integer status);

    List<Shop> getByStatus(@Param("status") Integer status);

    List<Shop> getByUserId(@Param("userId") Integer userId);

    Integer getId();

    Shop getById(@Param("id") Integer id);
    String getShopNameByShopId(@Param("id") Integer id);
}
