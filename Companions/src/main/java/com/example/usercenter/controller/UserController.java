package com.example.usercenter.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter.common.BaseResponse;
import com.example.usercenter.common.InfoCode;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.model.domain.request.UserLoginRequest;
import com.example.usercenter.model.domain.request.UserRegisterRequest;
import com.example.usercenter.service.UserService;
import com.example.usercenter.utils.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 编写restful风格的api，返回值为json格式
 * interface for user
 * @author  huanguang
 */
@RestController
@RequestMapping("/user")
public class  UserController {
    @Resource
    private UserService userService; //把资源引入到这个controller类里

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null) {
            //return ResultUtils.Error(null);
            throw new BusinessException(InfoCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return ResultUtils.Error(null);
        }

        long id =  userService.userRegister(userAccount, userPassword, checkPassword) ;
        return ResultUtils.success(id);
    }

    /**
     * 参数很长时尽量不要使用get,Http get方法提交的数据大小长度并没有限制，Http协议规范没有对URL长度进行限制
     * 目前说的get长度有限制，是特定的浏览器及服务器对它的限制。
     * 没有requestbody，mvc不知道怎么把前端传过来的参数和下面进行关联
     * @param userLoginRequest
     * @param request
     * @return
     */

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){

        if (userLoginRequest == null) {
            return ResultUtils.Error(null);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return ResultUtils.Error(null);
        }
        User user =  userService.doLogin(userAccount, userPassword,request) ;
        return ResultUtils.success(user);

    }

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request){

        if (request == null) {
            return ResultUtils.Error(null);
        }
        int result =  userService.userLogout(request);
        return ResultUtils.success(result);
    }

    /**
     *
     * @param username
     * @param request
     * @return
     * 查找功能，这边使用模糊查找功能
     *
     */
    @GetMapping("/search")
    public BaseResponse<List<User>> userSearch(String username, HttpServletRequest request){
        if(!isAdmin(request).getData()){
            throw  new BusinessException(InfoCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            queryWrapper.like( "username",username);
        }
        List<User> userList =  userService.list(queryWrapper);
        List<User> result =  userList.stream().map(user -> userService.satefyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(result);
        //从 userList 中获取一个 Stream 对象。
        //使用 peek() 方法对 Stream 中的每个元素进行操作。
        //在 peek() 方法中，使用 lambda 表达式将每个用户的密码设置为 null。
        //使用 collect() 方法将 Stream 中的元素收集到一个 List 中。

    }
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if(currentUser == null){
            return ResultUtils.Error(null);
        }
        long userId = currentUser.getId();
        User safetyUser =  userService.satefyUser(userService.getById(userId));
        return ResultUtils.success(safetyUser);
    }

    /**
     * 删除功能 先判断是否是管理员身份，如果不是管理员身份，则返回false
     * 如果是管理员身份，但是id查询到为负值，则判断为没有这个用户，返回false
     * 此处的删除为逻辑删除，实际上sql的执行语句不是删除而是更新
     * @param id
     * @param request
     * @return
     */

    @PostMapping("/delete")
    public BaseResponse<Boolean> userDelete(@RequestBody long id, HttpServletRequest request){
        if(!isAdmin(request).getData()){
            return ResultUtils.Error(false);
        }
        if(id<=0)
        {
            return ResultUtils.Error(false);
        }
       boolean result =  userService.removeById(id);
        return ResultUtils.success(result);

    }

    /**
     *
     * 判断是否是有权限的管理员 其中1代表管理员 0代表普通用户
     * ADMIN_ROLE的解释含义已经存放到了constant常量方法里面了
     * 因为查询和操作都会用到这个代码，因此将它提取出来用作一个公共方法
     * @param request
     * @return
     */
    public BaseResponse<Boolean> isAdmin(HttpServletRequest request){

        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);//访问静态常量应该直接使用类，不使用类的实例
        User user =  (User) userObj;
        if(user == null || user.getUserRole() != ADMIN_ROLE){
            return ResultUtils.Error(false);
        }
        return ResultUtils.success(true);
    }
}
