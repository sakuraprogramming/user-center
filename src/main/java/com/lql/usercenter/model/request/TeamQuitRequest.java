package com.lql.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamQuitRequest implements Serializable {

    private static final long serialVersionUID = -5969607861774392709L;
    private Long teamId;


}
