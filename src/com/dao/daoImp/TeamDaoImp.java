package com.dao.daoImp;

import com.dao.TeamDao;
import com.domain.TeamDistrict;
import com.domain.TeamMember;
import com.domain.TeamToreport;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/28 - 14:50
 */
public class TeamDaoImp extends HibernateDaoSupport implements TeamDao {

    /**
     * 用户创建队伍
     * @param teamDistrict
     * @throws Exception
     */
    @Override
    public void createTeam(TeamDistrict teamDistrict) throws Exception {
        this.getHibernateTemplate().save(teamDistrict);
    }

    /**
     * 保存加入队伍的队员的信息
     * @param teamMenber
     * @throws Exception
     */
    @Override
    public void saveTeamMember(TeamMember teamMenber) throws Exception {
        this.getHibernateTemplate().save(teamMenber);
    }

    /**
     * 根据类型显示当前类型下有多少个队伍
     * @param teamType
     * @throws Exception
     */
    @Override
    public int teamOfTypeCoun(String teamType) throws Exception {
        String hql = "select count(*) from TeamDistrict t where t.teamType = ?";
        List counList = this.getHibernateTemplate().find(hql, teamType);
        Long teamOfTypeCoun = (Long)counList.get(0);
        return teamOfTypeCoun.intValue();
    }
    /**
     * 共有多少个队伍
     * @throws Exception
     */
    @Override
    public int allTeamCoun() throws Exception {
        String hql = "select count(*) from TeamDistrict";
        List counList = this.getHibernateTemplate().find(hql);
        Long teamOfTypeCoun = (Long)counList.get(0);
        return teamOfTypeCoun.intValue();
    }

    /**
     * 分页显示当前类型下的队伍
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamDistrict> showTeam(String teamType, int startIndex, int pageSize) throws Exception {
        List<TeamDistrict> teamList = (List<TeamDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                // String hql = "from TeamDistrict t where t.teamType = ? and t.teamPeopleNum > t.currPeopleNum order by t.createTime desc";
                String hql = "from TeamDistrict t where t.teamType = ? order by t.createTime desc";
                Query query = session.createQuery(hql);
                query.setParameter(0, teamType);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });

        return teamList;
    }

    /**
     * 用户点击“举报”按钮
     * @param teamToreport
     * @throws Exception
     */
    @Override
    public void clickToreport(TeamToreport teamToreport) throws Exception {
        this.getHibernateTemplate().save(teamToreport);
    }

    /**
     * 查看我的消息
     * @throws Exception
     * @param uid 登陆用户ID
     */
    @Override
    public Map showMyMessages(String uid) throws Exception {
        Map myMessageMap = new HashMap();
        //返回加入申请（未审核）
        String hql = "from TeamMember t where t.teamCaptainUid = ? and t.consentJoin = 'false' order by joinTime desc ";
        List<TeamMember> teamMemberList = (List<TeamMember>) this.getHibernateTemplate().find(hql,uid);
        //返回加入申请（审核）
        String hql1 = "from TeamMember t where t.teamMemberUid = ? and t.consentJoin in('agree','disagree') order by joinTime desc ";
        List<TeamMember> teamMemberList1 = (List<TeamMember>) this.getHibernateTemplate().find(hql1,uid);
        //将teamMemberList1中的对象放入teamMemberList中
        for (TeamMember teamMember : teamMemberList1) {
            teamMemberList.add(teamMember);
        }
        //返回举报消息
        String hql2 = "select new TeamToreport(toreportId,teamId,toreportText) from TeamToreport t where t.teamCaptainUid = ? and t.isRead = 'false'";
        List<TeamToreport> teamToreportList = (List<TeamToreport>) this.getHibernateTemplate().find(hql2, uid);
        myMessageMap.put("teamMemberList",teamMemberList);
        myMessageMap.put("teamToreportList",teamToreportList);

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
        TeamDistrict teamDistrict = this.getHibernateTemplate().get(TeamDistrict.class, teamId);
        return teamDistrict;
    }

    /**
     * 用户审核加入队伍的申请
     * @param teamMemberId
     * @param audit
     * @throws Exception
     */
    @Override
    public void auditApply(String teamMemberId, String audit) throws Exception {
        TeamMember teamMember = this.getHibernateTemplate().get(TeamMember.class, teamMemberId);
        teamMember.setConsentJoin(audit);
        this.getHibernateTemplate().update(teamMember);
        if("agree".equals(audit)){
            changCurrPeopleNum(teamMember.getTeamId());
        }
    }

    /**
     * 用户读了被举报的消息
     * @param toreportId
     * @throws Exception
     */
    @Override
    public void toreportIsRead(String toreportId) throws Exception {
        TeamToreport teamToreport = this.getHibernateTemplate().get(TeamToreport.class, toreportId);
        teamToreport.setIsRead("true");
        this.getHibernateTemplate().update(teamToreport);
    }


    /**
     * 用户审核加入队伍申请后，对该队伍的当前人数加1
     * @param teamId
     */
    public void changCurrPeopleNum(String teamId){
        TeamDistrict teamDistrict = this.getHibernateTemplate().get(TeamDistrict.class, teamId);
        teamDistrict.setCurrPeopleNum(teamDistrict.getCurrPeopleNum()+1);
        this.getHibernateTemplate().update(teamDistrict);
    }

    /**
     * 查看我的队伍
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamDistrict> showMyTeam(String uid) throws Exception {
        String hql = "select new TeamMember(teamId) from TeamMember t where t.teamMemberUid = ? and consentJoin = 'true' order by t.joinTime desc ";
        List<TeamMember> list = (List<TeamMember>) this.getHibernateTemplate().find(hql, uid);
        //根据队伍ID显示队伍的详细信息
        String myTeamIds = null;
        for (TeamMember teamMember : list) {
            if (myTeamIds == null){
                myTeamIds = "'"+teamMember.getTeamId()+"'";
            }else {
                myTeamIds = myTeamIds+",'"+teamMember.getTeamId()+"'";
            }
        }
        String hql1 = "select new TeamDistrict(counter,teamId,teamName,teamType) from TeamDistrict t where t.teamId in("+myTeamIds+")";
        List<TeamDistrict> myTeamList = (List<TeamDistrict>) this.getHibernateTemplate().find(hql1);
        return myTeamList;
    }

}
