package com.lql.usercenter.model;

import lombok.Data;

@Data
public class CodeLoginKey {
    private Long id;
    private String eventKey;
    private String openId;

    public CodeLoginKey(String eventKey, String openId) {
        this.eventKey = eventKey;
        this.openId = openId;
    }
}