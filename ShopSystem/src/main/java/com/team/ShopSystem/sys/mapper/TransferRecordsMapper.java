package com.team.ShopSystem.sys.mapper;

import com.team.ShopSystem.sys.entity.TransferRecords;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xby
 * @since 2023-04-17
 */
public interface TransferRecordsMapper extends BaseMapper<TransferRecords> {
    List<TransferRecords> get(@Param("name") String name);
}
