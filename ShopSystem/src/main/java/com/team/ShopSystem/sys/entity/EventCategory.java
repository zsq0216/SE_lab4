package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("ss_event_category")
@ApiModel(value = "活动商品种类对象", description = "")
@Data
@Getter
@Setter
@NoArgsConstructor
public class EventCategory {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer eventId;

    private String categoryName;

    public EventCategory(Integer eventId, String categoryName) {
        this.eventId = eventId;
        this.categoryName = categoryName;
    }
}
