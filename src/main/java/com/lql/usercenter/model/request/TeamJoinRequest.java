package com.lql.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = -1696959957163554402L;

    private Long teamId;

    /**
     * 密码
     */
    private String password;

}
