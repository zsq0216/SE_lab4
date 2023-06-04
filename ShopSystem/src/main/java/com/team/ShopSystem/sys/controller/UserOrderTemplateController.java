package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
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
    @Autowired
    ShopCategoryMapper shopCategoryMapper;
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    EventCategoryMapper eventCategoryMapper;
    @Autowired
    GoodsImageMapper goodsImageMapper;


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
            Shop shop = shopMapper.selectById(userOrderTemplate.getShopId());
            List<String> categoryList = shopCategoryMapper.getByShopId(shop.getId());
            shop.setCategory(categoryList);
            Goods goods = goodsMapper.selectById(userOrderTemplate.getGoodsId());
            categoryList = goodsCategoryMapper.getByGoodsId(goods.getId());
            goods.setCategory(categoryList);
            List<String> images=goodsImageMapper.getByGoodsId(goods.getId());
            goods.setImage(images);
            Event event = eventMapper.selectById(userOrderTemplate.getEventId());
            categoryList = eventCategoryMapper.selectByEventId(event.getId());
            userOrderTemplatePlusList.add(new UserOrderTemplatePlus(userOrderTemplate,shop,goods,event));
        }
        return Result.success(userOrderTemplatePlusList,"成功获得临时订单");
    }
}
