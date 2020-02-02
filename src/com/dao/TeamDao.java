package com.dao;

import com.domain.TeamDistrict;
import com.domain.TeamMember;
import com.domain.TeamToreport;

import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/28 - 14:50
 */
public interface TeamDao {
    /**
     * 用户创建队伍
     * @param teamDistrict
     * @throws Exception
     */
    void createTeam(TeamDistrict teamDistrict) throws Exception;

    /**
     * 保存加入队伍的队员的信息
     * @param teamMenber
     * @throws Exception
     */
    void saveTeamMember(TeamMember teamMenber) throws Exception;

    /**
     * 根据类型统计当前类型下有多少个队伍
     * @param teamType
     * @throws Exception
     */
    int teamOfTypeCoun(String teamType) throws Exception;

    /**
     * 共有多少个队伍
     * @throws Exception
     */
    int allTeamCoun() throws Exception;

    /**
     * 分页显示当前类别的队伍
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<TeamDistrict> showTeam(String teamType, int startIndex, int pageSize) throws Exception;

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
     * @return
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
     * 用户已读被举报消息
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
