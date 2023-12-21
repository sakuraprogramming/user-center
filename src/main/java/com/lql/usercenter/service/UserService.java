package com.lql.usercenter.service;

import com.lql.usercenter.common.BaseResponse;
import com.lql.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lql.usercenter.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.lql.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.lql.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户
 */
public interface UserService extends IService<User> {

    /**
     * @param userAccount   用户账号
     * @param userPassword  用户密码
     * @param checkPassword 用户校验密码
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount
     * @param userPassword
     * @return
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User getSafetyUser(User user);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     *
     * @param tagNameList
     * @return
     */
    List<User> searchUsersByTags(List<String> tagNameList);

    /**
     * 更新用户信息
     */
    int updateUser(User user, User loginUser);

    /**
     * 判断用户是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

    /**
     * 获取当前登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 判断用户是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    List<User> matchUsers(long num, User loginUser);
}
