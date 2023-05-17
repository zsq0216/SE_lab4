package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.UserOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-05-03
 */
public interface UserOrderMapper extends BaseMapper<UserOrder> {
    List<UserOrder> selectByUserIdAndStatus(@Param("userId") Integer userId, @Param("status") Integer status);
    List<UserOrder> selectByShopIdAndStatus(@Param("shopId") Integer shopId, @Param("status") Integer status);
    Integer queryByShopId(@Param("shopId") Integer shopId);
    Integer getMonthSalesByShopId(@Param("shopId")Integer shopId, @Param("date") Date date);
    List<Integer> getGoodsId(@Param("userId") Integer userId);
    List<Integer> getGoodsIdByUser(Integer userId,Integer goodsId);
    List<Integer> getUserByGoods(Integer userId,Integer goodsId);
    List<UserOrder> selectByUserIdAndEventId(@Param("userId")Integer userId,@Param("eventId")Integer eventId);
}
