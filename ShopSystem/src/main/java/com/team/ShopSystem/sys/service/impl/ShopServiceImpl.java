package com.team.ShopSystem.sys.service.impl;

import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
import com.team.ShopSystem.sys.service.IGoodsService;
import com.team.ShopSystem.sys.service.IShopService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.team.ShopSystem.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {
    @Resource
    ShopMapper shopMapper;
    @Resource
    ShopCategoryMapper shopCategoryMapper;
    @Autowired
    ConstantsProperties constants;
    @Autowired
    UserMapper userMapper;
    @Autowired
    IUserService userService;
    @Autowired
    TransferRecordsMapper transferRecordsMapper;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Result<?> addShop(Shop shop){
        if(shopMapper.queryByIdNumber(shop.getIdNumber())>0) {
            return Result.fail(MsgEnum.ERROR_IDNUMBER);
        }
        if(shopMapper.queryByShopName(shop.getShopName())>0) {
            return Result.fail(MsgEnum.ERROR_SHOPNAME);
        }
        if(shop.getFund() > (userMapper.getById(shop.getUserId())).getAccount()){
            return Result.fail(MsgEnum.ERROR_INSUFFICIENTFUNDS);
        }
        shopMapper.insert(shop);
        Integer id = shopMapper.getId();
        shop.setId(id);
        User user = userMapper.getById(shop.getUserId());
        user.setAccount(user.getAccount() - shop.getFund());
        userMapper.updateById(user);
        transferRecordsMapper.insert(new TransferRecords("user_"+shop.getUserId(),shop.getFund(),"shop_"+shop.getId()));
        transferRecordsMapper.insert(new TransferRecords("shop_"+shop.getId(),shop.getFund(),"admin_intermediate"));
        Admin admin = adminMapper.get();
        admin.setIntermediateAccount(admin.getIntermediateAccount()+shop.getFund());
        adminMapper.updateById(admin);
        for (String categoryName : shop.getCategory()) {
            shopCategoryMapper.insert(new ShopCategory(null,categoryName,shop.getId()));
        }
        return Result.success("等待管理员审核");
    }

    @Override
    @Transactional
    public Result<?> approveShop(Shop shop){//其中必须有idNumber和fund
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate",shop.getFund(),"admin_profit"));
        shopMapper.updateStatus(shop.getId(),constants.getApproved());
        Admin admin = adminMapper.get();
        admin.setIntermediateAccount(admin.getIntermediateAccount()- shop.getFund());
        admin.setProfitAccount(admin.getProfitAccount()+shop.getFund());
        adminMapper.updateById(admin);
        return Result.success("审核通过");
    }

    @Override
    public Result<?> disapproveShop(Shop shop){
        shopMapper.updateStatus(shop.getId(), constants.getRejected());
        Admin admin = adminMapper.get();
        admin.setIntermediateAccount(admin.getIntermediateAccount()- shop.getFund());
        adminMapper.updateById(admin);
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate",shop.getFund(),"user_"+shop.getUserId()));
        User user = userMapper.getById(shop.getUserId());
        user.setAccount(user.getAccount()+ shop.getFund());
        userMapper.updateById(user);
        return Result.success("审核不通过");
    }

    @Override
    public Result<List<Shop>> showUserShop(Integer userId){
        List<Shop> shownShop = shopMapper.getByUserId(userId);
        for (Shop shop : shownShop) {
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
        }
        return  Result.success(shownShop);
    }

    @Override
    public Result<?> deleteShop(Shop shop) {
        //商品订单未完成判断，返回ERROR_DELETESHOP（但现在还没有下单操作，不需要）

        shopMapper.updateStatus(shop.getId(),constants.getApplyDeleted());
        return Result.success("申请删除商店成功");
    }

    @Override
    public Result<?> approveDeleteShop(Shop shop) {
        shopMapper.updateStatus(shop.getId(),constants.getDeleted());
        List<Goods> goods = goodsMapper.getByShopId(shop.getId());
        for (Goods good : goods) {
            goodsService.removeGoods(good.getId());
        }
        User user = userMapper.getById(shop.getUserId());
        transferRecordsMapper.insert(new TransferRecords("shop_"+shop.getId(),shop.getAccount(),"user_"+user.getId()));
        user.setAccount(user.getAccount()+shop.getAccount());
        userService.updateById(user);
        return Result.success("删除商店成功");
    }
}
