package com.team.ShopSystem.common.vo;

public enum MsgEnum {
    SUCCESS(20000,"success"),
    FAIL(20001,"fail"),
    ERROR_USERNAMEORPASSWORD(20002,"用户名或密码错误"),
    ERROR_JWT(20003, "jwt无效，请重新登录"),
    ERROR_USERNAME(20004,"用户名已被注册过"),
    ERROR_PHONE(20005,"手机号已被注册过"),
    ERROR_IDNUMBER(20006,"身份证号已被注册过"),
    ERROR_EMAIL(20007,"邮箱已被注册过"),
    ERROR_CLASS(20008,"用户类错误"),
    ERROR_SHOPNAME(20009,"店铺名已被注册过"),
    ERROR_INSUFFICIENTFUNDS(20010,"账户余额不足"),
    ERROR_DELETESHOP(20011,"有尚未完成的订单"),
    ERROR_EVENT(20012,"商店已申请活动"),
    ERROR_SHOPFUND(20013,"商店注册资金不达标"),
    ERROR_SHOPSALES(20014,"商店销售额不达标"),
    ERROR_SHOPPROFIT(20015,"商店利润不达标"),
    ERROR_EVENTEND(20016,"活动已结束");
    private Integer code;
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    MsgEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
