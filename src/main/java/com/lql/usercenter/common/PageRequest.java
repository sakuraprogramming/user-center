package com.lql.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用的分页请求参数
 */
@Data
public class PageRequest implements Serializable {


    private static final long serialVersionUID = -908328234906214824L;
    /**
     * 页面大小
     */
    protected int pageSize=10;

    /**
     * 当前页号
     */
    protected int pageNum=1;

}
