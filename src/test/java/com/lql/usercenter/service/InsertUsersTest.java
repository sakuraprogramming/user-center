package com.lql.usercenter.service;

import com.lql.usercenter.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class InsertUsersTest {

    @Resource
    private UserService userService;

    private ExecutorService executorService= new ThreadPoolExecutor
            (60,1000,10000, TimeUnit.MINUTES,
                    new ArrayBlockingQueue<>(10000));

    //@Test
    public void doInsertUsers2() {
        final int INSERT_NUM = 100000;
        int batchSize = 2500;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<CompletableFuture<Void>> futureList=new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            List<User> userList = new ArrayList<>();
            int j = 0;
            while (true) {
                j++;
                User user = new User();
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
                userList.add(user);
                if (j % batchSize == 0) {
                    break;
                }
            }
            //异步执行
            CompletableFuture<Void> future=CompletableFuture.runAsync(()->{
                System.out.println("current Thread name:"+Thread.currentThread().getName());
                userService.saveBatch(userList, batchSize);
            },executorService);
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }


    //@Test
    public void doInsertUsers() {
        final int INSERT_NUM = 100000;
        int batchSize = 1000;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
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
            userList.add(user);
        }
        userService.saveBatch(userList, batchSize);

        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());
    }

}
