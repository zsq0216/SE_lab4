package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("ss_event_apply")
@ApiModel(value = "申请活动对象", description = "")
@Getter
@Setter
@NoArgsConstructor
public class EventApply {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer eventId;

    private Integer shopId;
    private Integer status;

    public EventApply(Integer eventId, Integer shopId) {
        this.eventId = eventId;
        this.shopId = shopId;
    }
}
