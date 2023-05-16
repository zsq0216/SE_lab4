package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.UserOrder;
import com.team.ShopSystem.sys.entity.UserOrderPlus;
import com.team.ShopSystem.sys.entity.UserOrderTemplate;
import com.team.ShopSystem.sys.entity.UserOrderTemplatePlus;
import com.team.ShopSystem.sys.mapper.CartMapper;
import com.team.ShopSystem.sys.mapper.GoodsMapper;
import com.team.ShopSystem.sys.mapper.ShopMapper;
import com.team.ShopSystem.sys.mapper.UserOrderTemplateMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

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
@Api(tags = {"用户临时订单接口列表"})
@RestController
@RequestMapping("/userOrderTemplate")
public class UserOrderTemplateController {
    @Autowired
    UserOrderTemplateMapper userOrderTemplateMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    GoodsMapper goodsMapper;

    @ApiOperation("确认选择商品")
    @PostMapping("/order")
    public Result<?> order(@RequestBody List<UserOrderTemplate> userOrderTemplateList ){
        UserOrderTemplate userOrderTemplate = userOrderTemplateMapper.getTemplate();
        Integer tem;
        if(userOrderTemplate == null ){
            tem = 1;
        }
        else{
            tem = userOrderTemplate.getTemplate() + 1;
        }
        for (UserOrderTemplate userOrderTem : userOrderTemplateList) {
            userOrderTem.setTemplate(tem);
            userOrderTemplateMapper.insert(userOrderTem);
        }
        return Result.success(tem,"成功存储临时订单");
    }

    @ApiOperation("返回临时订单")
    @GetMapping("/ret")
    public Result<?> ret(@RequestParam Integer template){
        List<UserOrderTemplate> userOrderTemplateList = userOrderTemplateMapper.selectByTemplate(template);
        List<UserOrderTemplatePlus> userOrderTemplatePlusList = new ArrayList<>();
        for (UserOrderTemplate userOrderTemplate : userOrderTemplateList) {
            userOrderTemplatePlusList.add(new UserOrderTemplatePlus(userOrderTemplate,shopMapper.selectById(userOrderTemplate.getShopId()),goodsMapper.selectById(userOrderTemplate.getGoodsId())));
        }
        return Result.success(userOrderTemplatePlusList,"成功获得临时订单");
    }
}
