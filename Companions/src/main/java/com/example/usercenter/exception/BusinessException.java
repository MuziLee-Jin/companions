package com.example.usercenter.exception;

import com.example.usercenter.common.InfoCode;

/**
 * 业务异常 继承 运行时异常
 */
public class BusinessException extends  RuntimeException{
    private  final int code;
    private  final String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }
    public BusinessException(InfoCode infoCode){
        super(infoCode.getMessage());
        this.code = infoCode.getCode();
        this.description= infoCode.getDescription();
    }
}
