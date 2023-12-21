package com.lql.usercenter.service;

import com.lql.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;


@SpringBootTest

public class UserServiceTest {
    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user=new User();
        user.setUsername("testSakura");
        user.setUserAccount("123");
        user.setAvatarUrl("https://tupian.qqw21.com/article/UploadPic/2020-3/20203142216575349.jpg");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount="sakura";
        String userPassword="12345678";
        String checkPassword="12345678";
        long result = userService.userRegister(userAccount, userPassword, checkPassword,"");
        System.out.println("结果为:"+result);
    }

    @Test
    public void testSearchByTags(){
        List<String> tagList= Arrays.asList("java","python");
        List<User> users = userService.searchUsersByTags(tagList);
        Assertions.assertNotNull(users);
    }

}