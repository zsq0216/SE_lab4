package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.TransferRecords;
import com.team.ShopSystem.sys.mapper.ShopMapper;
import com.team.ShopSystem.sys.mapper.TransferRecordsMapper;
import com.team.ShopSystem.sys.mapper.UserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-04-17
 */
@RestController
@RequestMapping("/transferRecords")
@Api(tags = {"转账流水接口列表"})
public class TransferRecordsController {
    @Autowired
    TransferRecordsMapper transferRecordsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ShopMapper shopMapper;

    @PostMapping("/user")
    @ApiOperation("获取用户流水")
    public Result<?> getUser(@RequestParam Integer id){
        String name = "user_" + id;
        List<TransferRecords> recordsList = transferRecordsMapper.get(name);
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList,"成功获取用户流水");
    }

    @PostMapping("/shop")
    @ApiOperation("获取商店流水")
    public Result<?> getShop(@RequestParam Integer id){
        String name = "shop_" + id;
        List<TransferRecords> recordsList = transferRecordsMapper.get(name);
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList,"成功获取商店流水");
    }

    @PostMapping("/adminIntermediate")
    @ApiOperation("获取管理员中间账户流水")
    public Result<?> getAdminIntermediate(){
        List<TransferRecords> recordsList = transferRecordsMapper.get("admin_intermediate");
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList);
    }

    @PostMapping("/adminProfit")
    @ApiOperation("获取管理员盈利账户流水")
    public Result<?> getAdminProfit(){
        List<TransferRecords> recordsList = transferRecordsMapper.get("admin_profit");
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList);
    }

    private TransferRecords alterName(TransferRecords record) {
        if(record.getTransferName().startsWith("user_")){
            Integer userId = Integer.parseInt(record.getTransferName().substring(5));
            record.setTransferName("user_"+userMapper.getUsernameByUserId(userId));
        }
        else if(record.getTransferName().startsWith("shop_")){
            Integer shopId = Integer.parseInt(record.getTransferName().substring(5));
            record.setTransferName("shop_"+shopMapper.getShopNameByShopId(shopId));
        }
        if(record.getReceiveName().startsWith("user_")){
            Integer userId = Integer.parseInt(record.getReceiveName().substring(5));
            record.setReceiveName("user_"+userMapper.getUsernameByUserId(userId));
        }
        else if(record.getReceiveName().startsWith("shop_")){
            Integer shopId = Integer.parseInt(record.getReceiveName().substring(5));
            record.setReceiveName("shop_"+shopMapper.getShopNameByShopId(shopId));
        }
        return record;
    }
}
