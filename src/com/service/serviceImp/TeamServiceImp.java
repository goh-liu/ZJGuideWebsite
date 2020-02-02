package com.service.serviceImp;

import com.dao.TeamDao;
import com.domain.PageModel;
import com.domain.TeamDistrict;
import com.domain.TeamMember;
import com.domain.TeamToreport;
import com.service.TeamService;
import com.utils.MD5Utils;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @autor goh_liu
 * @date 2019/12/28 - 14:48
 */
@Transactional
public class TeamServiceImp implements TeamService {
    //自动注入DAO
    private TeamDao teamDao;
    public void setTeamDao(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    /**
     * 用户创建队伍
     * @param teamDistrict
     * @throws Exception
     */
    @Override
    public void createTeam(TeamDistrict teamDistrict) throws Exception {
        teamDao.createTeam(teamDistrict);
        //保存队伍中创建人的信息
        TeamMember teamMember = new TeamMember();
        //保存队长的ID
        String teamId = teamDistrict.getTeamId();
        String uid = teamDistrict.getUid();
        String teamMenberId = MD5Utils.md5(teamId + uid);
        teamMember.setTeamMemberId(teamMenberId);
        teamMember.setTeamId(teamId);
        teamMember.setTeamCaptainUid(uid);
        teamMember.setTeamMemberUid(uid);
        teamMember.setConsentJoin("true");
        teamMember.setJoinTime(new Date());
        // 调用保存队伍中的队员信息的方法
        saveTeamMember(teamMember);
    }

    /**
     * 保存队伍中的队员信息
     * @param teamMenber
     * @throws Exception
     */
    @Override
    public void saveTeamMember(TeamMember teamMenber) throws Exception {
        teamDao.saveTeamMember(teamMenber);
    }

    /**
     * 按照类型显示队伍
     * @param teamType
     * @return
     * @throws Exception
     */
    @Override
    public PageModel showTeam(int curNum,String teamType) throws Exception {
        int totalRecords = teamDao.teamOfTypeCoun(teamType);//返回该类型下的队伍数
        int allTeamCoun = teamDao.allTeamCoun();//返回总队伍数
        PageModel pm = new PageModel(curNum, totalRecords, 8);
        List<TeamDistrict> teamList = teamDao.showTeam(teamType, pm.getStartIndex(), pm.getPageSize());
        pm.setList(teamList);

        HashMap map = new HashMap();
        map.put("selected",teamType);//选中的类型
        map.put("currTypeTeamCoun",totalRecords);//该分类的队伍数
        map.put("allTeamCoun",allTeamCoun);//所有队伍数

        pm.setMap(map);
        //关联url,后面的?是为了连接页码
        String url = "team_showTeam.action?teamType="+teamType+"&";
        pm.setUrl(url);
        return pm;
    }

    /**
     * 用户点击“举报”按钮
     * @param teamToreport
     * @throws Exception
     */
    @Override
    public void clickToreport(TeamToreport teamToreport) throws Exception {
        teamDao.clickToreport(teamToreport);
    }

    /**
     * 查看我的消息
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public Map showMyMessages(String uid) throws Exception {
        Map myMessageMap = teamDao.showMyMessages(uid);
        return myMessageMap;
    }

    /**
     * 根据teamId显示队伍信息
     * @param teamId
     * @return
     * @throws Exception
     */
    @Override
    public TeamDistrict showTeamByTeamId(String teamId) throws Exception {
        TeamDistrict teamDistrict = teamDao.showTeamByTeamId(teamId);
        return teamDistrict;
    }

    /**
     * 用户审核加入队伍申请
     * @param teamMemberId
     * @param audit
     * @throws Exception
     */
    @Override
    public void auditApply(String teamMemberId, String audit) throws Exception {
        teamDao.auditApply(teamMemberId,audit);
    }

    /**
     * 用户已读被举报消息
     * @param toreportId
     * @throws Exception
     */
    @Override
    public void toreportIsRead(String toreportId) throws Exception {
        teamDao.toreportIsRead(toreportId);
    }

    /**
     * 查看我的队伍
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamDistrict> showMyTeam(String uid) throws Exception {
        List<TeamDistrict> myTeamList = teamDao.showMyTeam(uid);
        return myTeamList;
    }


}
