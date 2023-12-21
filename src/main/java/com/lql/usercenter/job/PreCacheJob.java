//package com.lql.usercenter.job;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.lql.usercenter.model.domain.User;
//import com.lql.usercenter.service.UserService;
//import lombok.extern.slf4j.Slf4j;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.TimeUnit;
//
//@Component
//@Slf4j
//public class PreCacheJob {
//
//    @Resource
//    private UserService userService;
//    @Resource
//    private RedisTemplate<String, Object> redisTemplate;
//    @Resource
//    private RedissonClient redissonClient;
//    private final List<Long> mainUserLists = Arrays.asList(1L, 3L);
//
//    @PostConstruct
//    @Scheduled(cron = "0 4 * * * *")
//    public void doCacheRecommendUser() {
//        RLock lock = redissonClient.getLock("sakura:precachejob:docache:lock");
//        try {
//            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
//                for (Long userID : mainUserLists) {
//                    QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//                    Page<User> userPage = userService.page(new Page<>(1, 20), queryWrapper);
//                    String redisKey = String.format("sakura:user:recommend:%s", userID);
//                    ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
//                    try {
//                        valueOperations.set(redisKey, userPage);
//                    } catch (Exception e) {
//                        log.error("redis set error", e);
//                    }
//                }
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }finally {
//            if (lock.isHeldByCurrentThread()) {
//                lock.unlock();
//            }
//        }
//    }
//}
