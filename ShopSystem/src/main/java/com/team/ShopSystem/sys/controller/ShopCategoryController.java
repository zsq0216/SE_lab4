package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.ShopCategory;
import com.team.ShopSystem.sys.mapper.ShopCategoryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/shopCategory")
@Api(tags = {"商店商品类别接口列表"})
public class ShopCategoryController {
    @Autowired
    ShopCategoryMapper shopCategoryMapper;
    @ApiOperation("获得所有商品种类")
    @PostMapping("/getCategory")
    public Result<?> getCategory(){
        List<String> categoryList = shopCategoryMapper.getCategory();
        Set<String> set = new HashSet<>(categoryList);
        categoryList.clear();
        categoryList.addAll(set);
        return Result.success(categoryList,"成功获得所有商品种类");
    }
}
