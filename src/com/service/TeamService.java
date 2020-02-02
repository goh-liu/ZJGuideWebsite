package com.service;

import com.domain.PageModel;
import com.domain.TeamDistrict;
import com.domain.TeamMember;
import com.domain.TeamToreport;

import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/28 - 14:47
 */
public interface TeamService {

    /**
     * 用户创建队伍
     * @param teamDistrict
     */
    void createTeam(TeamDistrict teamDistrict) throws Exception;

    /**
     * 保存加入队伍的队员的信息
     * @param teamMenber
     * @throws Exception
     */
    void saveTeamMember(TeamMember teamMenber) throws Exception;

    /**
     * 按照类型显示队伍
     * @param teamType
     * @return
     * @throws Exception
     */
    PageModel showTeam(int curNum,String teamType) throws Exception;

    /**
     * 用户点击“举报”按钮
     * @param teamToreport
     * @throws Exception
     */
    void clickToreport(TeamToreport teamToreport) throws Exception;

    /**
     * 查看我的消息
     * @param uid
     * @return
     * @throws Exception
     */
    Map showMyMessages(String uid) throws Exception;

    /**
     * 根据teamId显示队伍信息
     * @param teamId
     * @throws Exception
     */
    TeamDistrict showTeamByTeamId(String teamId) throws Exception;

    /**
     * 用户审核加入队伍申请
     * @param teamMemberId
     * @param audit
     * @throws Exception
     */
    void auditApply(String teamMemberId, String audit) throws Exception;

    /**
     * 用户已读被举报
     * @param toreportId
     * @throws Exception
     */
    void toreportIsRead(String toreportId) throws Exception;

    /**
     * 查看我的队伍
     * @param uid
     * @return
     * @throws Exception
     */
    List<TeamDistrict> showMyTeam(String uid) throws Exception;
}
