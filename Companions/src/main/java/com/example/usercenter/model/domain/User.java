package com.example.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * the name of the user
     */
    private String username;

    /**
     * 
     */
    private String userAccount;

    /**
     * the link of the profile
     */
    private String avatarUrl;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String email;

    /**
     * 0 normal 1 pac 2 frozen
     */
    private Integer userStatus;
    private String tags;

    /**
     * 
     */
    private Date updateTime;
    /**
     * 0 ordinary people
     * 1 admin
     */
    private Integer userRole;

    @TableLogic
    private Integer isDelete;

    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}