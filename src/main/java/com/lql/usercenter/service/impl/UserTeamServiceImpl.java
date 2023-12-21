package com.lql.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lql.usercenter.model.domain.UserTeam;
import com.lql.usercenter.service.UserTeamService;
import com.lql.usercenter.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author hp
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2023-11-13 21:54:55
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




