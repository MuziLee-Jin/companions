package com.example.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @Data 注解可以帮我们生成get and set 方法
 * @author 幻光
 */
@Data
public class BaseResponse<T> implements Serializable {
    private  int code;
    private  String message;
    private  T data;
    private  String description;
    public BaseResponse(int code, T data,String message) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
        this.message = null;
    }
    public BaseResponse(InfoCode infoCode){
        this.code = infoCode.getCode();
        this.message = infoCode.getMessage();
        this.description = infoCode.getDescription();
    }
    public BaseResponse(int code,String message,String description){
        this.code = code ;
        this.message =message;
        this.description = description;
    }
}
