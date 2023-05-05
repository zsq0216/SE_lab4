package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xby
 * @since 2023-04-15
 */
@TableName("ss_admin")
@Getter
@Setter
public class Admin{
    private String adminPwd;

    private Float profitAccount;

    private Float intermediateAccount;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

}
