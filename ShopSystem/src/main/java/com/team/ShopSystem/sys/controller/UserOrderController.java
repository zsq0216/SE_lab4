package com.team.ShopSystem.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.config.ConstantsProperties;
import com.team.ShopSystem.sys.entity.*;
import com.team.ShopSystem.sys.mapper.*;
import com.team.ShopSystem.sys.service.IMerchantOrderService;
import com.team.ShopSystem.sys.service.IUserOrderService;
import io.swagger.annotations.Api;
import com.team.ShopSystem.common.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
    GoodsImageMapper goodsImageMapper;
    @Autowired
    IUserOrderService userOrderService;
    @Autowired
    EventFundsMapper eventFundsMapper;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    EventApplyMapper eventApplyMapper;
    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    ShopCategoryMapper shopCategoryMapper;
    @Autowired
    CartMapper cartMapper;
    @Autowired
    CartGoodsMapper cartGoodsMapper;
    @Autowired
    DeliveryAddressMapper deliveryAddressMapper;

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
        Map<Integer,Float> price_amount = new HashMap<>();
        for (UserOrder userOrder : userOrders) {
            order_amount += userOrder.getTotalPrice();
            if(price_amount.containsKey(userOrder.getEventId())){
                float amount = price_amount.get(userOrder.getEventId());
                amount =amount + (goodsMapper.selectById(userOrder.getGoodsId()).getPrice() * userOrder.getQuantity() - userOrder.getTotalPrice());
                price_amount.put(userOrder.getEventId(),amount);
            }else{
                price_amount.put(userOrder.getEventId(),goodsMapper.selectById(userOrder.getGoodsId()).getPrice() * userOrder.getQuantity() - userOrder.getTotalPrice());
            }
        }
        User user = userMapper.selectById(userOrders.get(0).getUserId());
        if(order_amount > user.getAccount()) {
            return Result.fail(MsgEnum.ERROR_INSUFFICIENTFUNDS);
        }
        Integer[] events = price_amount.keySet().toArray(new Integer[0]);//所有参加的活动
        int i;
        for (i=0; i< events.length; i++) {
            if(events[i] == 0){
                continue;
            }
            if(price_amount.get(events[i])>eventFundsMapper.selectFundsByEventId(events[i])){
                Event event = eventMapper.selectById(events[i]);
                if(event.getStatus() == constants.getRejected()){
                    List<UserOrder> userOrderList = userOrderMapper.selectByUserIdAndEventId(userOrders.get(0).getUserId(),events[i]);
                    for (UserOrder userOrder : userOrderList) {
                        Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
                        userOrder.setUnitPrice(goods.getPrice());
                        userOrder.setTotalPrice(userOrder.getQuantity() * goods.getPrice());
                        userOrderMapper.updateById(userOrder);
                    }
                    return Result.fail(MsgEnum.ERROR_EVENTEND,events[i]);
                }
                float left_funds = eventFundsMapper.selectFundsByEventId(events[i]);
                transferRecordsMapper.insert(new TransferRecords("admin_intermediate",left_funds,"admin_profit",LocalDate.now(),"remaining funds refunded"));
                Admin admin = adminMapper.get();
                admin.setIntermediateAccount(admin.getIntermediateAccount() - left_funds);
                admin.setProfitAccount(admin.getProfitAccount() + left_funds);
                adminMapper.updateById(admin);
                EventFunds eventFunds = eventFundsMapper.selectByEventId(event.getId());
                eventFunds.setFunds(0.0f);
                eventFundsMapper.updateById(eventFunds);
                event.setStatus(constants.getRejected());
                eventMapper.updateById(event);
                List<EventApply> eventApplies = eventApplyMapper.selectByEventId(event.getId());
                for (EventApply eventApply : eventApplies) {
                    eventApply.setStatus(constants.getRejected());
                    eventApplyMapper.updateById(eventApply);
                }
                return Result.fail(MsgEnum.ERROR_EVENTEND,events[i]);
            }
        }
        for (i=0; i< events.length; i++) {
            if(events[i] == 0){
                continue;
            }
            EventFunds eventFunds = eventFundsMapper.selectByEventId(events[i]);
            eventFunds.setFunds(eventFunds.getFunds() - price_amount.get(events[i]));
            eventFundsMapper.updateById(eventFunds);
        }
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

    @ApiOperation("确认收货")
    @PutMapping("/receive")
    public Result<?> receive(@RequestBody UserOrder userOrder,@RequestParam String stime){
        Admin admin = adminMapper.get();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate time = LocalDate.parse(stime, formatter);
        userOrder.setStatus(constants.getFinished());
        userOrderMapper.updateById(userOrder);
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate", (float) ((userOrder.getTotalPrice())*0.05),"admin_profit",time,"confirm receipt of goods"));
        admin.setIntermediateAccount((float) (admin.getIntermediateAccount() - userOrder.getTotalPrice()));
        admin.setProfitAccount((float) (admin.getProfitAccount() + (userOrder.getTotalPrice())*0.05));
        adminMapper.updateById(admin);
        transferRecordsMapper.insert(new TransferRecords("admin_intermediate",(float)(userOrder.getTotalPrice()*0.95),"shop_"+userOrder.getShopId(),time,"confirm receipt of goods"));
        Shop shop = shopMapper.getById(userOrder.getShopId());
        shop.setAccount((float) (shop.getAccount() + userOrder.getTotalPrice()*0.95));
        if(userOrder.getEventId()>0) {
            float amount = goodsMapper.selectById(userOrder.getGoodsId()).getPrice() * userOrder.getQuantity() - userOrder.getTotalPrice();
            transferRecordsMapper.insert(new TransferRecords("admin_intermediate",amount,"shop_"+userOrder.getShopId(),time,"promotional subsidy"));
            admin.setIntermediateAccount(admin.getIntermediateAccount() - amount);
            adminMapper.updateById(admin);
            shop.setAccount(shop.getAccount() + amount);
            shopMapper.updateById(shop);
        }
        shopMapper.updateById(shop);

        Goods goods = goodsMapper.getById(userOrder.getGoodsId());
        goods.setSales(goods.getSales()+userOrder.getQuantity());
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
        User user = userMapper.selectById(userOrder.getUserId());
        user.setAccount(user.getAccount() + userOrder.getTotalPrice());
        userMapper.updateById(user);
        return Result.success("退货退款成功");
    }

    @ApiOperation("返回所有待支付订单")
    @GetMapping("/showUnpaidOrder")
    public Result<?> showUnpaidOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getUnpaid());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回所有已支付订单")
    @GetMapping("/showPaidOrder")
    public Result<?> showPaidOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getPaid());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回所有待收货订单")
    @GetMapping("/showShippedOrder")
    public Result<?> showShippedOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getShipped());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回所有已完成订单")
    @GetMapping("/showFinishedOrder")
    public Result<?> showFinishedOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getFinished());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回所有已撤销订单")
    @GetMapping("/showCancelledOrder")
    public Result<?> showCancelledOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getCancelled());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回所有待退款订单")
    @GetMapping("/showRefundingOrder")
    public Result<?> showRefundingOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getRefunding());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回所有已退款订单")
    @GetMapping("/showRefundedOrder")
    public Result<?> showRefundedOrder(@RequestParam Integer userId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("status",constants.getRefunded());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回某一店铺所有待退款订单")
    @GetMapping("/showShopRefundingOrder")
    public Result<?> showShopRefundingOrder(@RequestParam Integer shopId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("shop_id", shopId);
        wrapper.eq("status",constants.getRefunding());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
    }

    @ApiOperation("返回某一店铺所有待发货订单")
    @GetMapping("/showShopUnshippedOrder")
    public Result<?> showShopUnshippedOrder(@RequestParam Integer shopId,@RequestParam Integer pageNo,@RequestParam Integer pageSize){
        Page<UserOrder> page = new Page<>(pageNo,pageSize);
        QueryWrapper<UserOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("shop_id", shopId);
        wrapper.eq("status",constants.getPaid());
        userOrderService.page(page,wrapper);
        List<UserOrder> userOrders = page.getRecords();
        List<UserOrderPlus> userOrderPluses = new ArrayList<UserOrderPlus>();
        for (UserOrder userOrder : userOrders) {
            Goods goods = goodsMapper.selectById(userOrder.getGoodsId());
            goods.setImage(goodsImageMapper.getByGoodsId(goods.getId()));
            goods.setCategory(goodsCategoryMapper.getByGoodsId(goods.getId()));
            Shop shop = shopMapper.selectById(userOrder.getShopId());
            shop.setCategory(shopCategoryMapper.getByShopId(shop.getId()));
            DeliveryAddress deliveryAddress = deliveryAddressMapper.selectById(userOrder.getAddressId());
            userOrderPluses.add(new UserOrderPlus(userOrder,shop,goods,deliveryAddress));
        }
        PageInfo pageInfo = new PageInfo(userOrderPluses,page.getTotal(),page.getPages());
        return Result.success(pageInfo);
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
