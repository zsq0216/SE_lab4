package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2023-05-09
 */
@TableName("ss_user_order_template")
@ApiModel(value = "UserOrderTemplate对象", description = "")
@Data
@Getter
@Setter
@NoArgsConstructor
public class UserOrderTemplate{


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer template;

    private Integer shopId;

    private Integer goodsId;

    private Integer userId;

    private Float unitPrice;

    private Integer quantity;

    private Float totalPrice;

    private Integer eventId;
    private Boolean source;

}
