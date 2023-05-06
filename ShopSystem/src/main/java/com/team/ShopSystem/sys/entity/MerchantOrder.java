package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2023-05-06
 */
@TableName("ss_merchant_order")
@ApiModel(value = "MerchantOrder对象", description = "")
@Data
@NoArgsConstructor
@Getter
@Setter
public class MerchantOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userOrderId;

    private Integer goodsId;

    private Float unitPrice;

    private Integer quantity;

    private Float totalPrice;

    private LocalDate date;

    private Integer addressId;

    private Integer status;

    public MerchantOrder(Integer userOrderId, Integer goodsId, Float unitPrice, Integer quantity, Float totalPrice, LocalDate date, Integer addressId) {
        this.userOrderId = userOrderId;
        this.goodsId = goodsId;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.date = date;
        this.addressId = addressId;
    }
}
