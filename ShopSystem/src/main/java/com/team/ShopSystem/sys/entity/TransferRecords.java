package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * <p>
 * 
 * </p>
 *
 * @author xby
 * @since 2023-04-17
 */
@TableName("ss_transfer_records")
@ApiModel(value = "TransferRecords对象", description = "")
@Getter
@Setter
@AllArgsConstructor
public class TransferRecords{
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String transferName;

    private Float amount;

    private String receiveName;

    private Integer deleted;

    public TransferRecords(String transferName, Float amount, String receiveName) {
        this.transferName = transferName;
        this.amount = amount;
        this.receiveName = receiveName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferRecords that = (TransferRecords) o;
        return Objects.equals(id, that.id) && Objects.equals(transferName, that.transferName) && Objects.equals(amount, that.amount) && Objects.equals(receiveName, that.receiveName) && Objects.equals(deleted, that.deleted);
    }

}
