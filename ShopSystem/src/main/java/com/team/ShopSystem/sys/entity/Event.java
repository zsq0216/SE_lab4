package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
@TableName("ss_event")
@ApiModel(value = "Event对象", description = "")
@Getter
@Setter
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private LocalDate startDate;

    private LocalDate endDate;

    private Float funds;

    private Float amount;

    private Float discount;

    private Float shopFund;

    private Integer shopSales;

    private Integer shopProfit;

    private Boolean status;

    @TableField(exist = false)
    private List<String> category;
}
