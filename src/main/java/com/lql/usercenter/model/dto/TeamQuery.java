package com.lql.usercenter.model.dto;

import com.lql.usercenter.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 查询队伍信息
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TeamQuery extends PageRequest {

    private Long id;

    private List<Long> idList;

    /**
     * 队伍名称
     */
    private String name;

    /**
     * 搜索文本
     */
    private String searchText;

    /**
     * 描述
     */
    private String description;


    /**
     * 最大人数
     */
    private Integer maxNum;


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 0 - 公开，1 - 私有，2 - 加密
     */
    private Integer status;


}
