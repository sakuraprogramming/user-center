package com.lql.usercenter.service;

import com.lql.usercenter.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lql.usercenter.model.domain.User;
import com.lql.usercenter.model.dto.TeamQuery;
import com.lql.usercenter.model.request.TeamDeleteRequest;
import com.lql.usercenter.model.request.TeamJoinRequest;
import com.lql.usercenter.model.request.TeamQuitRequest;
import com.lql.usercenter.model.request.TeamUpdateRequest;
import com.lql.usercenter.model.vo.TeamUserVO;

import java.util.List;

/**
* @author hp
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2023-11-13 21:53:06
*/
public interface TeamService extends IService<Team> {

    /**
     * 添加队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 查询队伍
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery,boolean isAdmin);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest,User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest,User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 解散队伍
     * @param teamDeleteRequest
     * @param loginUser
     * @return
     */
    boolean deleteTeam(TeamDeleteRequest teamDeleteRequest, User loginUser);
}
