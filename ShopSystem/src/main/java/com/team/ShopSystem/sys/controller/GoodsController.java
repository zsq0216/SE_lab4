package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.Goods;
import com.team.ShopSystem.sys.entity.GoodsUpdate;
import com.team.ShopSystem.sys.mapper.GoodsImageMapper;
import com.team.ShopSystem.sys.mapper.GoodsMapper;
import com.team.ShopSystem.sys.mapper.GoodsUpdateMapper;
import com.team.ShopSystem.sys.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

/**
 * @Author: Zhong Siqi
 * @Description: 商品操作
 * @DateTime: 2023/4/4 14:05
 **/

@Api(tags = {"商品接口列表"})
@RestController
@RequestMapping("/Goods")
public class GoodsController {

    @Autowired
    IGoodsService goodsService;

    @Resource
    GoodsMapper goodsMapper;

    @Resource
    GoodsImageMapper goodsImageMapper;

    @Resource
    GoodsUpdateMapper goodsUpdateMapper;

    @Autowired
    ConstantsProperties constants;

    @Value("${prop.upload-folder}")
    private String UPLOAD_FOLDER;
    @PostMapping("/addGoodsApply")
    @ApiOperation("申请上架商品")
    public Result<?> addGoodsApply(@RequestBody Goods goods, @RequestParam Integer shopId){
        return goodsService.addGoodsApply(goods,shopId);
    }

    @PutMapping("/remove")
    @ApiOperation("下架商品")
    public Result<?> removeGoods(@RequestParam Integer goodsId){return goodsService.removeGoods(goodsId);}

    @PutMapping("/addGoodsApprove")
    @ApiOperation("批准上架申请")
    public Result<?> addGoodsApprove(@RequestParam Integer goodsId){
        return goodsService.addGoodsApprove(goodsId);
    }

    @PutMapping("/addGoodsReject")
    @ApiOperation("拒绝上架申请")
    public Result<?> addGoodsReject(@RequestParam Integer goodsId){
        return goodsService.addGoodsReject(goodsId);
    }


    @PostMapping("/updateGoodsApply")
    @ApiOperation("申请修改商品")
    public Result<?> updateGoodsApply(@RequestParam Integer goodsId,@RequestBody GoodsUpdate newGoods){
        return goodsService.updateGoodsApply(goodsId,newGoods);
    }
    @PutMapping("/updateGoodsApprove")
    @ApiOperation("批准商品信息修改申请")
    public Result<?> updateGoodsApprove(@RequestParam Integer goodsId){//@RequestBody 将json转换为User对象
        return goodsService.updateGoodsApprove(goodsId);
    }

    @PutMapping("/updateGoodsReject")
    @ApiOperation("拒绝商品信息修改申请")
    public Result<?> updateGoodsReject(@RequestParam Integer goodsId){//@RequestBody 将json转换为User对象

        return goodsService.updateGoodsReject(goodsId);
    }
    @GetMapping("/showAddApply")
    @ApiOperation("显示待批准上架的商品,给admin")
    public Result<List<Goods>> showAddApply(){
        List<Goods> list = goodsMapper.getByStatus(constants.unapproved);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showUpdateApply")
    @ApiOperation("显示待批准修改的商品,给admin")
    public Result<List<Goods>> showUpdateApply(){
        List<Goods> list = goodsMapper.getByStatus(7);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showUpdateMessage")
    @ApiOperation("显示某个申请修改的信息，给admin或商户")
    public Result<GoodsUpdate> showUpdateMessage(@RequestParam Integer goodsId){
        GoodsUpdate goodsUpdate=goodsUpdateMapper.getById(goodsId);
        goodsUpdate.setImage(goodsImageMapper.getByUpdate(goodsUpdate.getId()));
        return Result.success(goodsUpdate);
    }

    @GetMapping("/showAddApproved")
    @ApiOperation("显示所有已批准上架的商品,给admin")
    public Result<List<Goods>> showAddApproved(){
        List<Goods> list = goodsMapper.getByStatus(1);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }
    @GetMapping("/showAddRejected")
    @ApiOperation("显示所有已拒绝上架的商品,给admin")
    public Result<List<Goods>> showAddRejected(){
        List<Goods> list = goodsMapper.getByStatus(2);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }
    @GetMapping("/showUpdateApproved")
    @ApiOperation("显示所有已批准修改的商品,给admin")
    public Result<List<Goods>> showUpdateApproved(){
        List<Goods> list = goodsMapper.getByStatus(8);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }
    @GetMapping("/showUpdateRejected")
    @ApiOperation("显示所有已拒绝修改的商品,给admin")
    public Result<List<Goods>> showUpdateRejected(){
        List<Goods> list = goodsMapper.getByStatus(9);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showShopAllGoods")
    @ApiOperation("显示某商店的所有状态的商品")
    public Result<List<Goods>> showAddRecord(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopId(shopId);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showAddRecord_0")
    @ApiOperation("显示某商店的上架申请记录之待审核")
    public Result<List<Goods>> showAddRecord_0(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,0);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showAddRecord_1")
    @ApiOperation("显示某商店的上架申请记录之申请成功")
    public Result<List<Goods>> showAddRecord_1(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,1);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showAddRecord_2")
    @ApiOperation("显示某商店的上架申请记录之申请失败")
    public Result<List<Goods>> showAddRecord_2(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,2);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showUpdateRecord_7")
    @ApiOperation("显示某商店的修改申请记录之待审核")
    public Result<List<Goods>> showUpdateRecord_7(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,7);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }
    @GetMapping("/showUpdateRecord_8")
    @ApiOperation("显示某商店的修改申请记录之成功")
    public Result<List<Goods>> showUpdateRecord_8(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,8);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }
    @GetMapping("/showUpdateRecord_9")
    @ApiOperation("显示某商店的修改申请记录之失败")
    public Result<List<Goods>> showUpdateRecord_9(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,9);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showDeleteRecord_5")
    @ApiOperation("显示某商店的已下架商品")
    public Result<List<Goods>> showDeleteRecord_5(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,5);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showAllApprovedGoods")
    @ApiOperation("显示所有上架商品，给用户看")
    public Result<List<Goods>> showAllApprovedGoods(){
        List<Goods> list = goodsMapper.getByStatus(constants.approved);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @GetMapping("/showShopApprovedGoods")
    @ApiOperation("显示某店铺上架商品，给用户看")
    public Result<List<Goods>> showShopApprovedGoods(@RequestParam Integer shopId){
        List<Goods> list = goodsMapper.getByShopStatus(shopId,constants.approved);
        for (Goods goods : list) {
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
        }
        return Result.success(list);
    }

    @PostMapping("/uploadImg")
    @ApiOperation("上传图片")
    public Result<String> upload(@RequestParam("files") MultipartFile file, HttpServletRequest request) {
        if (file == null) {
            return Result.fail("请选择要上传的图片");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return Result.fail("文件大小不能大于10M");
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase(Locale.ENGLISH).contains(suffix.toUpperCase(Locale.ENGLISH))) {
            return Result.fail("请选择jpg,jpeg,gif,png格式的图片");
        }
        String savePath = UPLOAD_FOLDER;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            return Result.fail("保存文件异常");
        }
        //返回文件名称
        return Result.success(filename);
    }
}
