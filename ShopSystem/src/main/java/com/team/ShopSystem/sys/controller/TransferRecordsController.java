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

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
    public List<Result<?>> getUser(@RequestParam Integer id){
        String name = "user_" + id;
        Float expenditure = 0.0f;
        List<TransferRecords> recordsList = transferRecordsMapper.get(name);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals(name)){
                expenditure += record.getAmount();
            }
            else if(record.getReceiveName().equals(name)){
                expenditure -= record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(expenditure,"成功获取用户支出"),Result.success(recordsList,"成功获取用户流水")).collect(Collectors.toList());
    }

    @PostMapping("/userWithinOneMonth")
    @ApiOperation("获取用户近一月流水")
    public List<Result<?>> getUserWithinOneMonth(@RequestParam Integer id){
        String name = "user_" + id;
        Float expenditure = 0.0f;

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange(name,startDate,endDate);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals(name)){
                expenditure += record.getAmount();
            }
            else if(record.getReceiveName().equals(name)){
                expenditure -= record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(expenditure,"成功获取用户支出"),Result.success(recordsList,"成功获取用户流水")).collect(Collectors.toList());
    }

    @PostMapping("/userWithinOneWeek")
    @ApiOperation("获取用户近一周流水")
    public List<Result<?>> getUserWithinOneWeek(@RequestParam Integer id){
        String name = "user_" + id;
        Float expenditure = 0.0f;

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange(name,startDate,endDate);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals(name)){
                expenditure += record.getAmount();
            }
            else if(record.getReceiveName().equals(name)){
                expenditure -= record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(expenditure,"成功获取用户支出"),Result.success(recordsList,"成功获取用户流水")).collect(Collectors.toList());
    }

    @PostMapping("/shop")
    @ApiOperation("获取商店流水")
    public List<Result<?>> getShop(@RequestParam Integer id){
        String name = "shop_" + id;
        Float profit = 0.0f;
        List<TransferRecords> recordsList = transferRecordsMapper.get(name);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals(name)){
                profit -= record.getAmount();
            }
            else if(record.getReceiveName().equals(name)){
                profit += record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(profit,"成功获取商店盈利"),Result.success(recordsList,"成功获取商店流水")).collect(Collectors.toList());
    }

    @PostMapping("/shopWithinOneMonth")
    @ApiOperation("获取商店近一月流水")
    public List<Result<?>> getShopWithinOneMonth(@RequestParam Integer id){
        String name = "shop_" + id;
        Float profit = 0.0f;

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange(name,startDate,endDate);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals(name)){
                profit -= record.getAmount();
            }
            else if(record.getReceiveName().equals(name)){
                profit += record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(profit,"成功获取商店盈利"),Result.success(recordsList,"成功获取商店流水")).collect(Collectors.toList());
    }

    @PostMapping("/shopWithinOneWeek")
    @ApiOperation("获取商店近一周流水")
    public List<Result<?>> getShoprWithinOneWeek(@RequestParam Integer id){
        String name = "shop_" + id;
        Float profit = 0.0f;

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange(name,startDate,endDate);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals(name)){
                profit -= record.getAmount();
            }
            else if(record.getReceiveName().equals(name)){
                profit += record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(profit,"成功获取商店盈利"),Result.success(recordsList,"成功获取商店流水")).collect(Collectors.toList());
    }

    @PostMapping("/adminIntermediate")
    @ApiOperation("获取管理员中间账户流水")
    public Result<?> getAdminIntermediate(){
        List<TransferRecords> recordsList = transferRecordsMapper.get("admin_intermediate");
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList,"成功获取管理员中间账户流水");
    }

    @PostMapping("/adminIntermediateWithinOneMonth")
    @ApiOperation("获取管理员中间账户近一月流水")
    public Result<?> getAdminIntermediateWithinOneMonth(){

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange("admin_intermediate",startDate,endDate);
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList,"成功获取管理员中间账户流水");
    }

    @PostMapping("/adminIntermediateWithinOneWeek")
    @ApiOperation("获取管理员中间账户近一周流水")
    public Result<?> getAdminIntermediateWithinOneWeek(){

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange("admin_intermediate",startDate,endDate);
        for (TransferRecords record : recordsList) {
            record = alterName(record);
        }
        return Result.success(recordsList,"成功获取管理员中间账户流水");
    }

    @PostMapping("/adminProfit")
    @ApiOperation("获取管理员盈利账户流水")
    public List<Result<?>> getAdminProfit(){
        Float profit = 0.0f;
        List<TransferRecords> recordsList = transferRecordsMapper.get("admin_profit");
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals("admin_profit")){
                profit -= record.getAmount();
            }
            else if(record.getReceiveName().equals("admin_profit")){
                profit += record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(profit,"成功获取商城盈利"),Result.success(recordsList,"成功获取管理员盈利账户流水")).collect(Collectors.toList());
    }

    @PostMapping("/adminProfitWithinOneMonth")
    @ApiOperation("获取管理员盈利账户近一月流水")
    public List<Result<?>> getAdminProfitWithinOneMonth(){
        Float profit = 0.0f;

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange("admin_profit",startDate,endDate);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals("admin_profit")){
                profit -= record.getAmount();
            }
            else if(record.getReceiveName().equals("admin_profit")){
                profit += record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(profit,"成功获取商城盈利"),Result.success(recordsList,"成功获取管理员盈利账户流水")).collect(Collectors.toList());
    }

    @PostMapping("/adminProfitWithinOneWeek")
    @ApiOperation("获取管理员盈利账户近一周流水")
    public List<Result<?>> getAdminProfitWithinOneWeek(){
        Float profit = 0.0f;

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -7);
        Date startDate = calendar.getTime();

        List<TransferRecords> recordsList = transferRecordsMapper.getByDateRange("admin_profit",startDate,endDate);
        for (TransferRecords record : recordsList) {
            if(record.getTransferName().equals("admin_profit")){
                profit -= record.getAmount();
            }
            else if(record.getReceiveName().equals("admin_profit")){
                profit += record.getAmount();
            }
            record = alterName(record);
        }
        return Stream.of(Result.success(profit,"成功获取商城盈利"),Result.success(recordsList,"成功获取管理员盈利账户流水")).collect(Collectors.toList());
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
