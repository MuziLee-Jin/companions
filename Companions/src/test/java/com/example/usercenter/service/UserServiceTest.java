package com.example.usercenter.service;

import com.example.usercenter.model.domain.User;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * the test of the user
 * @author 幻光
 */

@SpringBootTest
public class UserServiceTest {
    @Resource
    private UserService userService;
//    void testAddUser(){
//        User user = new User();
//        user.setUsername("幻光");
//        user.setUserAccount("123");
//        user.setAvatarUrl("https://p26-passport.byteacctimg.com/img/user-avatar/a465a3d4ac86e6dbfa7f0232f138ebf2~100x100.awebp");
//        user.setGender(0);
//        user.setUserPassword("123");
//        user.setPhone("123");
//        user.setEmail("123");
//        user.setUpdateTime(new Date());
//        user.setCreateTime(new Date());
//
//        boolean result = userService.save(user);
//        System.out.println(user.getId());
//        Assertions.assertTrue(result);
//
//    }

    @Test
    void userRegister() {
        String userAccount = "huanguang";
        String userPassword = "12345678";
        String checkPassword = "12345678";
        long result = userService.userRegister(userAccount,userPassword,checkPassword);
        Assertions.assertEquals(-1,result);
    }
    @Test
    void testSearchUserByTags(){
        List<String> tagList = Arrays.asList("java");
        List<User> list = userService.searchUserByTags(tagList);
        Assert.assertNotNull(list);
    }
}