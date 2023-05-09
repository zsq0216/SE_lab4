package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
@Api(tags = {"商城活动接口列表"})
@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    TransferRecordsMapper transferRecordsMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    EventCategoryMapper eventCategoryMapper;
    @Autowired
    EventFundsMapper eventFundsMapper;
    @Autowired
    EventApplyMapper eventApplyMapper;
    @Autowired
    ConstantsProperties constants;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @ApiOperation("开启活动")
    @PostMapping("/openEvent")
    public Result<?> openEvent(@RequestBody Event event, @RequestParam String sdate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(sdate, formatter);
        Admin admin = adminMapper.get();
        if(admin.getProfitAccount() < event.getFunds()){
            return Result.fail(MsgEnum.ERROR_INSUFFICIENTFUNDS);
        }
        transferRecordsMapper.insert(new TransferRecords("admin_profit",event.getFunds(),"admin_intermediate",date,"launch the mall promotion"));
        admin.setIntermediateAccount(admin.getIntermediateAccount() + event.getFunds());
        admin.setProfitAccount(admin.getProfitAccount() - event.getFunds());
        adminMapper.updateById(admin);
        eventMapper.insert(event);
        Integer lastId = eventMapper.getLastId();
        List<String> categoryList = event.getCategory();
        for (String category : categoryList) {
            eventCategoryMapper.insert(new EventCategory(lastId,category));
        }
        eventFundsMapper.insert(new EventFunds(lastId,event.getFunds()));
        return Result.success("成功开启活动");
    }

    @ApiOperation("申请活动")
    @PutMapping("/applyEvent")
    public Result<?> applyEvent(@RequestParam Integer shopId,@RequestParam Integer eventId){
        if(eventApplyMapper.queryByShopId(shopId) > 0){
            return Result.fail(MsgEnum.ERROR_EVENT);
        }
        eventApplyMapper.insert(new EventApply(eventId,shopId));
        return Result.success("申请活动成功");
    }

    @ApiOperation("允许商店活动申请")
    @PutMapping("/approveApply")
    public Result<?> approveApply(@RequestParam Integer applyId){
        EventApply eventApply = eventApplyMapper.selectById(applyId);
        eventApply.setStatus(constants.getApproved());
        eventApplyMapper.updateById(eventApply);
        List<String> eventCategory = eventCategoryMapper.selectByEventId(eventApply.getId());
        List<Goods> goods = goodsMapper.getByShopId(eventApply.getShopId());
        for (Goods good : goods) {
            List<String> goodCategory = goodsCategoryMapper.getByGoodsId(good.getId());
            int i,j;
            for (i = 0;i<goodCategory.size();i++) {
                for(j = 0;j<eventCategory.size();j++){
                    if(goodCategory.get(i).equals(eventCategory.get(j))){
                        break;
                    }
                }
                if(j!=eventCategory.size()){
                    break;
                }
            }
            if(i!=goodCategory.size()){
                good.setEventId(eventApply.getId());
                goodsMapper.updateById(good);
            }
        }
        return Result.success("允许商店活动");
    }

    @ApiOperation("拒绝商店活动申请")
    @PutMapping("/disapproveApply")
    public Result<?> disapproveApply(@RequestParam Integer applyId){
        EventApply eventApply = eventApplyMapper.selectById(applyId);
        eventApply.setStatus(constants.getRejected());
        eventApplyMapper.updateById(eventApply);
        return Result.success("拒绝商店活动");
    }

    @ApiOperation("显示所有活动")
    @GetMapping("/show")
    public Result<?> show(){
        return Result.success(eventMapper.show(),"显示所有商城活动");
    }

    //@ApiOperation("显示参加某一活动的所有商品")
}
