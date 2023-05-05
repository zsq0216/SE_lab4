package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */


public interface UserMapper extends BaseMapper<User> {

    Integer queryByUsername(@Param("username") String username);
    Integer queryByPhone(@Param("phone") String phone);
    Integer queryByIdNumber(@Param("idNumber") String idNumber);
    Integer queryByEmail(@Param("email") String email);
    Integer getUserIdByUsername(@Param("username") String username);
    User getById(@Param("id") Integer id);
    String getUsernameByUserId(@Param("id") Integer id);
}
