package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.Shop;
import com.team.ShopSystem.sys.entity.User;
import com.team.ShopSystem.sys.mapper.ShopCategoryMapper;
import com.team.ShopSystem.sys.mapper.ShopMapper;
import com.team.ShopSystem.sys.service.IShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
@Api(tags = {"店铺接口列表"})
@RestController
@RequestMapping("/shop")
public class ShopController {
    @Autowired
    IShopService shopService;
    @Resource
    ShopCategoryMapper shopCategoryMapper;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    ConstantsProperties constants;

    @GetMapping("/all")
    @ApiOperation("显示所有店铺及其状态")
    public Result<List<Shop>> getAllShop(){
        List<Shop> allShop = shopService.list();
        for (Shop shop : allShop) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        return Result.success(allShop);
    }

    @PostMapping("/add")
    @ApiOperation("申请开店")
    public Result<?> addShop(@RequestBody Shop shop){//@RequestBody 将json转换为User对象
        return shopService.addShop(shop);
    }

    @PutMapping("/approve")
    @ApiOperation("批准申请")
    public Result<?> approveShop(@RequestBody Shop shop){//@RequestBody 将json转换为User对象
        return shopService.approveShop(shop);
    }

    @PutMapping("/disapprove")
    @ApiOperation("拒绝申请")
    public Result<?> disapproveShop(@RequestBody Shop shop){//@RequestBody 将json转换为User对象
        return shopService.disapproveShop(shop);
    }

    @PostMapping("/delete")
    @ApiOperation("删除商店")
    public Result<?> deleteShop(@RequestBody Shop shop){
        return shopService.deleteShop(shop);
    }

    @PostMapping("/approveDelete")
    @ApiOperation("批准删除商店")
    public Result<?> approveDeleteShop(@RequestBody Shop shop){
        Result<?> result = shopService.approveDeleteShop(shop);
        shop.setAccount(0F);
        shopService.updateById(shop);
        return result;
    }

    @PostMapping("/disapproveDelete")
    @ApiOperation("拒绝删除商店")
    public Result<?> disapproveDeleteShop(@RequestBody Shop shop){
        shopMapper.updateStatus(shop.getId(),constants.getRejectDeleted());
        return Result.success("拒绝删除商店成功");
    }

    @PostMapping("/showUser")
    @ApiOperation("显示该商家所有的店铺")
    public Result<List<Shop>> showUserShop(@RequestBody User user){
        int userId = user.getId();
        return shopService.showUserShop(userId);
    }

    @GetMapping("/showShopByShopId")
    @ApiOperation("通过shopId获得shop的信息")
    public Result<Shop> showShopByShopId(@RequestParam Integer shopId){
        Shop shop = shopMapper.getById(shopId);
        return Result.success(shop);
    }

    @PostMapping("/showByStatus0_3")
    @ApiOperation("显示所有待批准的店")
    public Result<List<Shop>> showShopByStatus0_3(){
        List<Shop> list = shopMapper.getByStatus(constants.getUnapproved());
        for (Shop shop : list) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        List<Shop> list2 = shopMapper.getByStatus(constants.getApplyDeleted());
        for (Shop shop : list2) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        list.addAll(list2);
        return Result.success(list);
    }

    @PostMapping("/showByStatus1_4")
    @ApiOperation("显示所有同意批准的店")
    public Result<List<Shop>> showShopByStatus1_4(){
        List<Shop> list = shopMapper.getByStatus(constants.getApproved());
        for (Shop shop : list) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        List<Shop> list2 = shopMapper.getByStatus(constants.getDeleted());
        for (Shop shop : list2) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        list.addAll(list2);
        return Result.success(list);
    }

    @PostMapping("/showByStatus2_5")
    @ApiOperation("显示所有拒绝批准的店")
    public Result<List<Shop>> showShopByStatus2_5(){
        List<Shop> list = shopMapper.getByStatus(constants.getRejected());
        for (Shop shop : list) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        List<Shop> list2 = shopMapper.getByStatus(constants.getRejectDeleted());
        for (Shop shop : list2) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        list.addAll(list2);
        return Result.success(list);
    }

}
