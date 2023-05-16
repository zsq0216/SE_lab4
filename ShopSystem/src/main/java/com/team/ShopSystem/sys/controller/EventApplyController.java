package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-05-09
 */
@RestController
@RequestMapping("/eventApply")
@Api(tags = {"活动申请接口列表"})
public class EventApplyController {
    @Autowired
    ConstantsProperties constants;
    @Autowired
    EventApplyMapper eventApplyMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    EventCategoryMapper eventCategoryMapper;
    @Autowired
    ShopCategoryMapper shopCategoryMapper;
    @ApiOperation("显示所有待批准活动申请")
    @GetMapping("/getApplying")
    public Result<?> getApplying(){
        List<EventApply> eventApplyList = eventApplyMapper.selectByStatus(constants.getUnapproved());
        List<EventApplyPlus> eventApplyPluses = new ArrayList<>();
        for (EventApply eventApply : eventApplyList) {
            List<String> categoryList = eventCategoryMapper.selectByEventId(eventApply.getEventId());
            Event event = eventMapper.selectById(eventApply.getEventId());
            event.setCategory(categoryList);
            Shop shop = shopMapper.selectById(eventApply.getShopId());
            categoryList = shopCategoryMapper.getByShopId(eventApply.getShopId());
            shop.setCategory(categoryList);
            eventApplyPluses.add(new EventApplyPlus(eventApply,event,shop));
        }
        return Result.success(eventApplyPluses,"成功获得所有待批准活动申请");
    }
    @ApiOperation("显示所有已批准活动申请")
    @GetMapping("/getApproved")
    public Result<?> getApproved(){
        List<EventApply> eventApplyList = eventApplyMapper.selectByStatus(constants.getApproved());
        List<EventApplyPlus> eventApplyPluses = new ArrayList<>();
        for (EventApply eventApply : eventApplyList) {
            List<String> categoryList = eventCategoryMapper.selectByEventId(eventApply.getEventId());
            Event event = eventMapper.selectById(eventApply.getEventId());
            event.setCategory(categoryList);
            Shop shop = shopMapper.selectById(eventApply.getShopId());
            categoryList = shopCategoryMapper.getByShopId(eventApply.getShopId());
            shop.setCategory(categoryList);
            eventApplyPluses.add(new EventApplyPlus(eventApply,event,shop));
        }
        return Result.success(eventApplyPluses,"成功获得所有已批准活动申请");
    }
    @ApiOperation("显示所有已拒绝活动申请")
    @GetMapping("/getRejected")
    public Result<?> getRejected(){
        List<EventApply> eventApplyList = eventApplyMapper.selectByStatus(constants.getRejected());
        List<EventApplyPlus> eventApplyPluses = new ArrayList<>();
        for (EventApply eventApply : eventApplyList) {
            List<String> categoryList = eventCategoryMapper.selectByEventId(eventApply.getEventId());
            Event event = eventMapper.selectById(eventApply.getEventId());
            event.setCategory(categoryList);
            Shop shop = shopMapper.selectById(eventApply.getShopId());
            categoryList = shopCategoryMapper.getByShopId(eventApply.getShopId());
            shop.setCategory(categoryList);
            eventApplyPluses.add(new EventApplyPlus(eventApply,event,shop));
        }
        return Result.success(eventApplyPluses,"成功获得所有已拒绝活动申请");
    }
}
