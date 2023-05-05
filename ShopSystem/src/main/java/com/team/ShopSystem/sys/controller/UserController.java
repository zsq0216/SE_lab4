package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.User;
import com.team.ShopSystem.sys.mapper.UserMapper;
import com.team.ShopSystem.sys.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


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
}
