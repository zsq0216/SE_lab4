package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
import com.team.ShopSystem.sys.service.IMerchantOrderService;
import io.swagger.annotations.Api;
import com.team.ShopSystem.common.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-05-03
 */
@Api(tags = {"用户订单接口列表"})
@RestController
@RequestMapping("/userOrder")
public class UserOrderController {
    @Autowired
    UserOrderMapper userOrderMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    TransferRecordsMapper transferRecordsMapper;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    ConstantsProperties constants;
    @Autowired
    ShopMapper shopMapper;
    @Autowired
    IMerchantOrderService merchantOrderService;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    MerchantOrderMapper merchantOrderMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartGoodsMapper cartGoodsMapper;

    @ApiOperation("购物车下单操作")
    @PostMapping("/orderFromCart")
    public Result<?> orderFromCart(@RequestBody List<UserOrder> userOrders){
        Integer userId=userOrders.get(0).getUserId();
        Integer cartId=cartMapper.getCartByUserId(userId);
        for (UserOrder userOrder : userOrders) {
            Integer goodsId=userOrder.getGoodsId();
            cartGoodsMapper.deleteBatch(cartId,goodsId);
            userOrderMapper.insert(userOrder);
        }
        return Result.success("下单成功");
    }
    @ApiOperation("直接下单操作")
    @PostMapping("/orderDirect")
    public Result<?> orderDirect(@RequestBody UserOrder userOrder){
        userOrderMapper.insert(userOrder);
        return Result.success("下单成功");
    }

    @ApiOperation("支付订单操作")
    @PostMapping("/pay")
    public Result<?> pay(@RequestBody List<UserOrder> userOrders){
        float order_amount = 0f;
        for (UserOrder userOrder : userOrders) {
            order_amount += userOrder.getTotalPrice();
        }
        User user = userMapper.getById(userOrders.get(0).getUserId());
        if(order_amount > user.getAccount()){
            return Result.fail(MsgEnum.ERROR_INSUFFICIENTFUNDS);
        }
        else{
            transferRecordsMapper.insert(new TransferRecords("user_"+user.getId(),order_amount,"admin_intermediate",userOrders.get(0).getDate(),"pay order"));
            user.setAccount(user.getAccount() - order_amount);
            userMapper.updateById(user);
            Admin admin = adminMapper.get();
            admin.setIntermediateAccount(admin.getIntermediateAccount() + order_amount);
            adminMapper.updateById(admin);
            for (UserOrder userOrder : userOrders) {
                userOrder.setStatus(constants.getPaid());
                userOrderMapper.updateById(userOrder);
                merchantOrderService.pay(userOrder);
            }
            return Result.success("支付成功");
        }
    }

    @ApiOperation("确认收货")
    @PutMapping("/receive")
    public Result<?> receive(@RequestBody UserOrder userOrder,@RequestParam String stime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(stime, formatter);
        userOrder.setStatus(constants.getFinished());
        userOrderMapper.updateById(userOrder);
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate", (float) ((userOrder.getTotalPrice())*0.05),"admin_profit",time,"confirm receipt of goods"));
        Admin admin = adminMapper.get();
        admin.setIntermediateAccount((float) (admin.getIntermediateAccount() - userOrder.getTotalPrice()));
        admin.setProfitAccount((float) (admin.getProfitAccount() + (userOrder.getTotalPrice())*0.05));
        adminMapper.updateById(admin);
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate",(float)(userOrder.getTotalPrice()*0.95),"shop_"+userOrder.getShopId(),time,"confirm receipt of goods"));
        Shop shop = shopMapper.getById(userOrder.getShopId());
        shop.setAccount((float) (shop.getAccount() + userOrder.getTotalPrice()*0.95));
        shopMapper.updateById(shop);
        Goods goods = goodsMapper.getById(userOrder.getGoodsId());
        goods.setSales(goods.getSales()+1);
        goodsMapper.updateById(goods);
        MerchantOrder merchantOrder = merchantOrderMapper.selectByUserOrderId(userOrder.getId());
        merchantOrder.setStatus(constants.getFinished());
        merchantOrderMapper.updateById(merchantOrder);
        return Result.success("收货成功");
    }

    @ApiOperation("撤销订单")
    @PutMapping("/cancel")
    public Result<?> cancel(@RequestParam Integer orderId){
        UserOrder userOrder = userOrderMapper.selectById(orderId);
        userOrder.setStatus(constants.getCancelled());
        userOrderMapper.updateById(userOrder);
        return Result.success("撤销订单成功");
    }

    @ApiOperation("申请退货退款")
    @PutMapping("/refunding")
    public Result<?> refunding(@RequestParam Integer orderId){
        UserOrder userOrder = userOrderMapper.selectById(orderId);
        userOrder.setStatus(constants.getRefunding());
        userOrderMapper.updateById(userOrder);
        MerchantOrder merchantOrder = merchantOrderMapper.selectByUserOrderId(orderId);
        merchantOrder.setStatus(constants.getRefunding());
        merchantOrderMapper.updateById(merchantOrder);
        return Result.success("申请退货退款成功");
    }

    @ApiOperation("允许退货退款")
    @PutMapping("/refunded")
    public Result<?> refunded(@RequestBody UserOrder userOrder,@RequestParam String stime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(stime, formatter);
        userOrder.setStatus(constants.getRefunded());
        userOrderMapper.updateById(userOrder);
        MerchantOrder merchantOrder = merchantOrderMapper.selectByUserOrderId(userOrder.getId());
        merchantOrder.setStatus(constants.getRefunded());
        merchantOrderMapper.updateById(merchantOrder);
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate",userOrder.getTotalPrice(),"user_"+userOrder.getUserId(),time,"return and refund"));
        Admin admin = adminMapper.get();
        admin.setIntermediateAccount(admin.getIntermediateAccount() - userOrder.getTotalPrice());
        adminMapper.updateById(admin);
        User user = userMapper.getById(userOrder.getUserId());
        user.setAccount(user.getAccount() + userOrder.getTotalPrice());
        userMapper.updateById(user);
        return Result.success("退货退款成功");
    }

    @ApiOperation("返回所有待支付订单")
    @GetMapping("/showUnpaidOrder")
    public Result<?> showUnpaidOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getUnpaid()));
    }

    @ApiOperation("返回所有已支付订单")
    @GetMapping("/showPaidOrder")
    public Result<?> showPaidOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getPaid()));
    }

    @ApiOperation("返回所有待收货订单")
    @GetMapping("/showShippedOrder")
    public Result<?> showShippedOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getShipped()));
    }

    @ApiOperation("返回所有已完成订单")
    @GetMapping("/showFinishedOrder")
    public Result<?> showFinishedOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getFinished()));
    }

    @ApiOperation("返回所有已撤销订单")
    @GetMapping("/showCancelledOrder")
    public Result<?> showCancelledOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getCancelled()));
    }

    @ApiOperation("返回所有待退款订单")
    @GetMapping("/showRefundingOrder")
    public Result<?> showRefundingOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getRefunding()));
    }

    @ApiOperation("返回所有已退款订单")
    @GetMapping("/showRefundedOrder")
    public Result<?> showRefundedOrder(@RequestParam Integer userId){
        return Result.success(userOrderMapper.selectByUserIdAndStatus(userId,constants.getRefunded()));
    }

    @ApiOperation("返回某一店铺所有待退款订单")
    @GetMapping("/showShopRefundingOrder")
    public Result<?> showShopRefundingOrder(@RequestParam Integer shopId){
        return Result.success(userOrderMapper.selectByShopIdAndStatus(shopId,constants.getRefunding()));
    }

    @ApiOperation("删除订单")
    @PutMapping("/deleteFinishedOrder")
    public Result<?> deleteFinishedOrder(@RequestParam Integer orderId){
        UserOrder userOrder = userOrderMapper.selectById(orderId);
        userOrder.setDeleted(1);
        userOrderMapper.updateById(userOrder);
        return Result.success("成功删除订单");
    }
}
