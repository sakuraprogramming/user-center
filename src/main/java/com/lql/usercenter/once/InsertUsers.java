package com.lql.usercenter.once;
import java.util.Date;

import com.lql.usercenter.mapper.UserMapper;
import com.lql.usercenter.model.domain.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;

//@Component
public class InsertUsers {

    //@Resource
    UserMapper userMapper;

   // @Scheduled(initialDelay = 5000,fixedRate = Long.MAX_VALUE)
    public void doInsertUsers(){
        final int INSERT_NUM=100;
        StopWatch stopWatch=new StopWatch();
        stopWatch.start();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user=new User();
            user.setUsername("假用户");
            user.setUserAccount("fakeSakura");
            user.setAvatarUrl("https://img1.imgtp.com/2023/10/03/9UN0GkdU.jpg");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("123456798");
            user.setEmail("456798321@163.com");
            user.setTags("[]");
            user.setProfile("平平无奇");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("111111");
            userMapper.insert(user);
        }
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }

    public static void main(String[] args) {
        new InsertUsers().doInsertUsers();
    }


}
