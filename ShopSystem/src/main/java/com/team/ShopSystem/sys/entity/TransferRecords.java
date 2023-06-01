package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * 转账流水记录
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
    private LocalDate time;
    private String comments;

    public TransferRecords(String transferName, Float amount, String receiveName,LocalDate time,String comments) {
        this.transferName = transferName;
        this.amount = amount;
        this.receiveName = receiveName;
        this.time = time;
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferRecords that = (TransferRecords) o;
        return Objects.equals(id, that.id) && Objects.equals(transferName, that.transferName) && Objects.equals(amount, that.amount) && Objects.equals(receiveName, that.receiveName) && Objects.equals(deleted, that.deleted);
    }

}
