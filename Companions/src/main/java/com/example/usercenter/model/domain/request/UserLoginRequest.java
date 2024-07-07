package com.example.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登陆 请求体
 * @author huanguang
 */

/**
 *
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3411272346571593546L;
    private String userAccount;
    private String userPassword;


}
