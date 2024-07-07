package com.example.usercenter.utils;

import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.InfoCode;

/**
 * 定义一个工具类
 * 帮助我们去生成成功或者失败的返回对象
 */
public class ResultUtils {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0,data,"ok");
    }
    public static <T> BaseResponse<T> Error(T data){
        return new BaseResponse<>(1,data,"not ok");
    }
    public static  BaseResponse Error(InfoCode infoCode){
        return new BaseResponse<>(infoCode);
    }
    public static  BaseResponse Error(int code,String message, String description){
        return new BaseResponse(code,message,description);
    }
}
