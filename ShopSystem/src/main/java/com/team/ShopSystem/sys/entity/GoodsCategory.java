package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 *商品类别
 * @author xby
 * @since 2023-05-07
 */
@TableName("ss_goods_category")
@Data
@ApiModel(value = "商品类别对象", description = "")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GoodsCategory {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String categoryName;

    private Integer goodsId;

}
