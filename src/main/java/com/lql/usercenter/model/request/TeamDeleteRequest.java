package com.lql.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamDeleteRequest implements Serializable {

    private static final long serialVersionUID = -256788237292858464L;

    private Long teamId;


}
