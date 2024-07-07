package com.example.usercenter.common;

/**
 * 枚举，定义全局错误信息
 */
public enum InfoCode {

    SUCCESS(0,"ok"," "),
    PARAMS_ERROR(40000,"请求参数错误"," "),
    NULL_ERROR(40001, "请求数据为空", " "),
    NO_LOGIN(40101,"没有登录"," "),
    NO_AUTH(40101,"无权限"," ");


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    /**
     * code:状态码
     * message:状态码的意义
     * description：状态码仔细的描述，ToDo项
     *
     */

    private  final int code;
    private  final String message;
    private  final String description;

    InfoCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }



}
