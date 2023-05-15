package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.DeliveryAddress;
import com.team.ShopSystem.sys.entity.User;
import com.team.ShopSystem.sys.mapper.DeliveryAddressMapper;
import com.team.ShopSystem.sys.mapper.UserMapper;
import com.team.ShopSystem.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Api(tags = {"用户接口列表"})
@RestController
@RequestMapping("/user")
//@CrossOrigin 跨域处理
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DeliveryAddressMapper deliveryAddressMapper;
    @PostMapping
    @ApiOperation("用户注册")
    public Result<?> addUser(@RequestBody User user){//@RequestBody 将json转换为User对象
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.addUser(user);
        //密码不能以名文出现在数据库中，需要进行加密处理
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<?> login(@RequestBody User user){
        return userService.login(user);
    }

    @PutMapping("/update")
    @ApiOperation("用户修改信息")
    public Result<?> updateUser(@RequestBody User user){//@RequestBody 将json转换为User对象
        User u = userMapper.getById(user.getId());
        if(u.getUsername().equals(user.getUsername())){
            user.setUsername(null);
        }
        if(u.getPhone().equals(user.getPhone())){
            user.setPhone(null);
        }
        if(u.getEmail().equals(user.getEmail())){
            user.setEmail(null);
        }
        user.setPassword(null);
        user.setIdNumber(null);
        Result<?> result = userService.updateUser(user);
        if(result.getCode() == 20000){
            userService.updateById(user);
        }
        return result;
    }

    @PostMapping("/getAccount")
    public Result<?> getAccount(@RequestBody User user){
        User u = userMapper.getById(user.getId());
        return Result.success(u.getAccount(),"返回账户余额成功");
    }

    @PutMapping("/topUpAccount")
    @ApiOperation("账户充值")
    public Result<?> topUpAccount(@RequestParam Float money,@RequestParam Integer userId){
        User user = userMapper.getById(userId);
        user.setAccount(user.getAccount()+money);
        userService.updateById(user);
        return Result.success(user.getAccount(),"账户充值成功");
    }

    @PostMapping("/getUserInfo")
    @ApiOperation("根据id查用户信息")
    public Result<User> getUserById(@RequestBody User u){
        User user = userMapper.selectById(u.getId());
        return Result.success(user);
    }

    @PostMapping("/addDeliveryAddress")
    @ApiOperation("添加收货地址")
    public Result<?> addDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress,Boolean isDefault){
        if(isDefault){
            deliveryAddress.setIsDefault(true);
            deliveryAddressMapper.clearDefault(deliveryAddress.getUserId());
            deliveryAddressMapper.insert(deliveryAddress);
        } else{
            deliveryAddress.setIsDefault(false);
            deliveryAddressMapper.insert(deliveryAddress);
        }
        return Result.success("添加成功");
    }

    @PutMapping("/setDefaultDeliveryAddress")
    @ApiOperation("修改默认收货地址")
    public Result<List<DeliveryAddress>> setDefaultDeliveryAddress(Integer id,Integer userId){
        deliveryAddressMapper.clearDefault(userId);
        deliveryAddressMapper.setDefault(id);
        return Result.success("修改成功");
    }
    @DeleteMapping("/deleteDeliveryAddress")
    @ApiOperation("删除收货地址")
    public Result<?> deleteDeliveryAddress(@RequestParam Integer id){
        deliveryAddressMapper.deleteById(id);
        return Result.success("删除成功");
    }
    @GetMapping("/showPersonalDeliveryAddress")
    @ApiOperation("显示个人收货地址")
    public Result<List<DeliveryAddress>> showPersonalDeliveryAddress(@RequestParam Integer userId){
        List<DeliveryAddress> list=deliveryAddressMapper.getByUserId(userId);
        Collections.sort(list);
        return Result.success(list);
    }
    @PutMapping("/updateDeliveryAddress")
    @ApiOperation("修改收货地址")
    public Result<List<DeliveryAddress>> updateDeliveryAddress(@RequestBody DeliveryAddress deliveryAddress){
        deliveryAddressMapper.updateById(deliveryAddress);
        return Result.success("修改成功");
    }
}