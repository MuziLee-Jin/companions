package com.example.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.usercenter.common.InfoCode;
import com.example.usercenter.exception.BusinessException;
import com.example.usercenter.mapper.UserMapper;
import com.example.usercenter.model.domain.User;
import com.example.usercenter.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.example.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author huanguang
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-03-10 11:36:49
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;
    /**
     * apply salt to protect the password
     */
    private static final String SALT = "huanguang";

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验,使用commons-lang3，接收多个参数进行校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword)){
            return -1;
        }
        if(userAccount.length()<4){
            return -1;
        }
        if(userPassword.length()<8 || checkPassword.length()<8){
            return -1;
        }
        //if account has special characters

        String isValid = "[\\\\u00A0\\\\s\\\"`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“'。，、？]";
        Matcher matcher = Pattern.compile(isValid).matcher(userAccount);
        // find the special characters,so does not match
        if(!matcher.find()){
            return -1;
        }
        if(!userPassword.equals(checkPassword)){
            return -1;
        }  //no duplication
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        long count  = userMapper.selectCount(queryWrapper);
        if(count > 0){
            return -1;
        }

        String newPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }
        return user.getId();
    }

    @Override
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1.检验字段是否合格，合格再去数据库查询
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<8){
            return null;
        }
        //2.查询数据库用户
        String newPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", newPassword);
        User user  = userMapper.selectOne(queryWrapper);
        if (user == null){
            log.info("user login in failed,maybe be the reason is the dismatch of the userAccount ");
            return null;
        }
        //3.用户脱敏
        User handledUser = satefyUser(user);

        //4.记录用户的登陆状态，在登录时会得到一个session状态，成功后会得到一个另一个成功的session，同时传给前端一个涉及cookie的命令
        //前端再次请求时，域名+cookie，cookie->session
        request.getSession().setAttribute(USER_LOGIN_STATE,user);
        return handledUser;
    }

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    @Override
    public User satefyUser(User user){
        if(user == null){
            return null;
        }
        User handledUser = new User();
        handledUser.setId(0L);
        handledUser.setUsername(user.getUsername());
        handledUser.setUserAccount(user.getUserAccount());
        handledUser.setAvatarUrl(user.getAvatarUrl());
        handledUser.setGender(0);
        handledUser.setPhone(user.getPhone());
        handledUser.setUserStatus(user.getUserStatus());
        handledUser.setUpdateTime(user.getUpdateTime());
        handledUser.setCreateTime(user.getCreateTime());
        handledUser.setUserRole(user.getUserRole());
        handledUser.setTags(user.getTags());
        return  handledUser;

    }

    /**
     * user log out
     * @param request
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * user can use the func to search the relative users by their tags
     * @param tagList
     * @return
     */
    @Override
    public List<User> searchUserByTags(List<String> tagList) {
        if(CollectionUtils.isEmpty(tagList)){
            throw new BusinessException(InfoCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        List<User> userList = userMapper.selectList(queryWrapper);
        Gson gson = new Gson();

        return userList.stream().filter(user-> {
            String tagstr = user.getTags();
            Set<String> tagNameSet = gson.fromJson(tagstr, new TypeToken<Set<String>>() {
            }.getType());
            for (String tag : tagList) {
                if (!tagNameSet.contains(tag)) {
                    return false;
                }
            }
            return true;
        }).map(this::satefyUser).collect(Collectors.toList());



        //在内存中运算tags逻辑

        //拼接 and 查询
//        for(String tagItem: tagList){
//            //自动添加模糊匹配 %
//            queryWrapper = queryWrapper.like("tags",tagItem);
//        }
//        List<User> userList = userMapper.selectList(queryWrapper);
//        userList.forEach(user -> {
//            satefyUser(user);
//        });
         //userList.forEach(this::safetyUser);safetyUser函数接受的正好是我们遍历的每个对象 可以这样写
//        return userList.stream().map(this::satefyUser).collect(Collectors.toList());
    }
}




