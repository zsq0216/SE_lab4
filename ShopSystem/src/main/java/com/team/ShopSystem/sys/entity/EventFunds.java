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
 * 活动资金对象
 * @author xby
 * @since 2023-05-09
 */
@TableName("ss_event_funds")
@ApiModel(value = "活动资金对象", description = "")
@Data
@Getter
@Setter
@NoArgsConstructor
public class EventFunds {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    public EventFunds(Integer eventId, Float funds) {
        this.eventId = eventId;
        this.funds = funds;
    }

    private Integer eventId;

    private Float funds;

}
