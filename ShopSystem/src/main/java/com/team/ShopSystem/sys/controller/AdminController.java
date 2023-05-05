package com.team.ShopSystem.sys.controller;

import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Admin;
import com.team.ShopSystem.sys.mapper.AdminMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xby
 * @since 2023-04-15
 */
@RestController
@RequestMapping("/admin")
@Api(tags = {"管理员接口列表"})
public class AdminController {
    @Autowired
    AdminMapper adminMapper;
    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public Result<?> login(String pwd){
        Admin admin = adminMapper.get();
        if(Objects.equals(pwd,admin.getAdminPwd())) {
            return Result.success(admin,"管理员登录成功");
        }
        return Result.fail(MsgEnum.ERROR_USERNAMEORPASSWORD);
    }

    @ApiOperation("返回admin盈利账户")
    @PostMapping("/getProfitAccount")
    public Result<?> getProfitAccount(){
        Admin admin = adminMapper.get();
        return  Result.success(admin.getProfitAccount());
    }

    @ApiOperation("返回admin中间账户")
    @PostMapping("/getIntermediateAccount")
    public Result<?> getIntermediateAccount(){
        Admin admin = adminMapper.get();
        return  Result.success(admin.getIntermediateAccount());
    }
}

