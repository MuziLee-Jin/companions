package com.example.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author huanguang
 */

/**
 *
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = -3243887192162880427L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;


}
