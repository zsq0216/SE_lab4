package com.team.ShopSystem.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.team.ShopSystem.common.vo.MsgEnum.FAIL;
import static com.team.ShopSystem.common.vo.MsgEnum.SUCCESS;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String message;
    private T data;

    public static<T> Result<T> success(){
        return new Result<>(SUCCESS.getCode(),SUCCESS.getMessage(),null);
    }

    public static<T> Result<T> success(T data){
        return new Result<>(SUCCESS.getCode(),SUCCESS.getMessage(),data);
    }

    public static<T> Result<T> success(T data, String message){
        return new Result<>(SUCCESS.getCode(),message,data);
    }

    public static<T> Result<T> success(String message){
        return new Result<>(SUCCESS.getCode(),message,null);
    }

    public static<T> Result<T> fail(){
        return new Result<>(FAIL.getCode(), FAIL.getMessage(),null);
    }

    public static<T> Result<T> fail(MsgEnum msgEnum){
        return new Result<>(msgEnum.getCode(),msgEnum.getMessage(),null);
    }
    public static<T> Result<T> fail(String message){
        return new Result<>(FAIL.getCode(), message,null);
    }

}