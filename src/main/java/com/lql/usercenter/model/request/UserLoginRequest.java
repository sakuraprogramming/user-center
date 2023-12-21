package com.lql.usercenter.model.request;


import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 */
@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 1805454357475345075L;
    private String userAccount;
     private String userPassword;

}
