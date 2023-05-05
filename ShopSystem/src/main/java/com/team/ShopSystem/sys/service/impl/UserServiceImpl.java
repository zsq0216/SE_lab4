package com.team.ShopSystem.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.team.ShopSystem.common.vo.MsgEnum;
import com.team.ShopSystem.common.vo.Result;
import com.team.ShopSystem.sys.entity.Cart;
import com.team.ShopSystem.sys.entity.User;
import com.team.ShopSystem.sys.mapper.CartMapper;
import com.team.ShopSystem.sys.mapper.UserMapper;
import com.team.ShopSystem.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xby
 * @since 2023-03-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CartMapper cartMapper;

    @Override
    @Transactional
    public Result<?> addUser(User user) {
        Result<?> result = detectUser(user);
        if(result.getCode() != 20000){
            return result;
        }
        this.baseMapper.insert(user);
        Integer userId=userMapper.getUserIdByUsername(user.getUsername());
        cartMapper.insert(new Cart(null,userId,0.0f));
        return Result.success(userId, "新增用户成功");
    }

    @Override
    public Result<?> updateUser(User user) {
        Result<?> result = detectUser(user);
        if(result.getCode() != 20000){
            return result;
        }
        return Result.success("修改信息成功");
    }

    @Override
    public Result<?> login(@RequestBody User user) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);
        if (loginUser != null) {
            if (user.getRoleId() != loginUser.getRoleId()) {
                return Result.fail(MsgEnum.ERROR_CLASS);
            }
            if (passwordEncoder.matches(user.getPassword(), loginUser.getPassword())) {
                return Result.success(loginUser.getId(), "登录成功");
            }
        }
        return Result.fail(MsgEnum.ERROR_USERNAMEORPASSWORD);
    }

    private Result<?> detectUser(User user){
        if (this.baseMapper.queryByUsername(user.getUsername()) > 0) {
            return Result.fail(MsgEnum.ERROR_USERNAME);
        }
        if (this.baseMapper.queryByPhone(user.getPhone()) > 0) {
            return Result.fail(MsgEnum.ERROR_PHONE);
        }
        if (this.baseMapper.queryByIdNumber(user.getIdNumber()) > 0) {
            return Result.fail(MsgEnum.ERROR_IDNUMBER);
        }
        if (this.baseMapper.queryByEmail(user.getEmail()) > 0) {
            return Result.fail(MsgEnum.ERROR_EMAIL);
        }
        return Result.success();
    }
}