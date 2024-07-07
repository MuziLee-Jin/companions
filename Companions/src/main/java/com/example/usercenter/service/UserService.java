package com.example.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.usercenter.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author huanguang
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-03-10 11:36:49
*/
public interface UserService extends IService<User> {
    /**
     *用户注册的注释
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * 如果有问题就返回-1，没问题返回ID
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     *  实现用户的登陆
     * @param userAccount
     * @param userPassword
     * @return
     */
    User doLogin(String userAccount, String userPassword, HttpServletRequest request) ;

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User satefyUser(User user);


    /**
     * 用户注销，取到request，然后得到用户保存的session，
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    List<User> searchUserByTags(List<String> tagList);


}
