package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * <p>
 * 
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
@TableName("ss_user")
@Data
@NoArgsConstructor
@Setter
@Getter
public class User{
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String idNumber;
    private String email;
    private String phone;
    private Integer deleted;
    private Integer roleId;
    private Float account;
}
