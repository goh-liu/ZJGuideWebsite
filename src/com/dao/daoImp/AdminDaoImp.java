package com.dao.daoImp;

import com.dao.AdminDao;
import com.domain.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2020/1/2 - 12:34
 */
public class AdminDaoImp extends HibernateDaoSupport implements AdminDao {

    /**
     * 查看学长寄语
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showNote(int startIndex,int pageSize) throws Exception {
        List<NoteDistrict> noteList = (List<NoteDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from NoteDistrict n order by n.publishedTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return noteList;
    }

    /**
     * 查看热门学长寄语
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showNoteWithHot(int startIndex, int pageSize) throws Exception {
        List<NoteDistrict> noteList = (List<NoteDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from NoteDistrict n order by n.usefulCoun desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return noteList;
    }

    /**
     * 查看学长寄语异议
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteObjection> showNoteObjection(int startIndex, int pageSize) throws Exception {
        List<NoteObjection> noteList = (List<NoteObjection>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from NoteObjection n order by n.objectionTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return noteList;
    }

    /**
     * 查看学长寄语举报
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteToreport> showNoteToreport(int startIndex, int pageSize) throws Exception {
        List<NoteToreport> noteList = (List<NoteToreport>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from NoteToreport n order by n.toreportTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return noteList;
    }

    /**
     * 统计学长寄语的数量
     * @throws Exception
     */
    @Override
    public int showNoteDistricteCoun() throws Exception {
        String hql = "select count(*) from NoteDistrict";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 统计学长寄语的数量
     * @throws Exception
     */
    @Override
    public int showNoteObjectionCoun() throws Exception {
        String hql = "select count(*) from NoteObjection";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 统计学长寄语的数量
     * @throws Exception
     */
    @Override
    public int showNoteToreportCoun() throws Exception {
        String hql = "select count(*) from NoteToreport";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 统计志同道合（队伍）的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showTeamCoun(String databaseName) throws Exception {
        String hql = "select count(*) from "+databaseName;
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 查看志同道合（队伍）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamDistrict> showTeam(int startIndex, int pageSize) throws Exception {
        List<TeamDistrict> teamList = (List<TeamDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from TeamDistrict t order by t.createTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return teamList;
    }

    @Override
    public List<TeamMember> showTeamMenber(int startIndex, int pageSize) throws Exception {
        List<TeamMember> teamList = (List<TeamMember>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from TeamMember t order by t.joinTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return teamList;
    }

    /**
     * 查看志同道合（举报）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamToreport> showTeamToreport(int startIndex, int pageSize) throws Exception {
        List<TeamToreport> teamList = (List<TeamToreport>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from TeamToreport t order by t.toreportTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return teamList;
    }

    /**
     * 查看志同道合（已满队伍）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamDistrict> showTeamFull(int startIndex, int pageSize) throws Exception {
        List<TeamDistrict> teamList = (List<TeamDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from TeamDistrict t where t.teamPeopleNum = t.currPeopleNum order by t.createTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return teamList;
    }

    /**
     * 查看志同道合（已满队伍数量）
     * @return
     * @throws Exception
     */
    @Override
    public int showTeamFullCoun() throws Exception {
        String hql = "select count(*) from TeamDistrict t where t.teamPeopleNum = t.currPeopleNum order by t.createTime desc";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 查看用户的数量
     */
    @Override
    public int showUserCoun() throws Exception {
        String hql = "select count(*) from User";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 查看管理员的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showIsAdminCoun() throws Exception {
        String hql = "select count(*) from User where isAdmin = '是'";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 查看用户
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<User> showUser(int startIndex, int pageSize) throws Exception {
        List<User> userList = (List<User>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from User";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return userList;
    }

    /**
     * 查看管理员
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<User> showIsAdmin(int startIndex, int pageSize) throws Exception {
        List<User> userList = (List<User>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from User where isAdmin = '是'";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return userList;
    }

    /**
     * 查看匿名友人（说说）的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showAonoDistricteCoun() throws Exception {
        String hql = "select count(*) from AnonDistrict";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 查看匿名友人（评论）的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showAnonCommentsCoun() throws Exception {
        String hql = "select count(*) from AnonComments";
        List list = this.getHibernateTemplate().find(hql);
        Long coun = (Long) list.get(0);
        return coun.intValue();
    }

    /**
     * 查看匿名友人
     * @return
     * @throws Exception
     */
    @Override
    public List<AnonDistrict> showAono(int startIndex, int pageSize) throws Exception {
        List<AnonDistrict> anonList = (List<AnonDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from AnonDistrict a order by a.deliveryTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return anonList;
    }

    /**
     * 查看匿名友人（热门）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<AnonDistrict> showAnonWithHot(int startIndex, int pageSize) throws Exception {
        List<AnonDistrict> anonList = (List<AnonDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from AnonDistrict a order by a.likeCoun desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return anonList;
    }

    /**
     * 查看匿名友人（评论）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<AnonComments> showAnonComments(int startIndex, int pageSize) throws Exception {
        List<AnonComments> anonList = (List<AnonComments>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                String hql = "from AnonComments a order by a.commentTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });
        return anonList;
    }

    /**
     * 删除寄语
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteNote(String deleteId) throws Exception {
        //删除该寄语关联数据
        this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                //删除寄语的举报表中的数据
                Query query1 = session.createQuery("delete from NoteToreport where noteId = ?");
                //删除寄语的有用表中的数据
                Query query2 = session.createQuery("delete from NoteUseful where noteId = ?");
                //删除寄语的异议表中的数据
                Query query3 = session.createQuery("delete from NoteObjection where noteId = ?");
                query1.setParameter(0,deleteId);
                query2.setParameter(0,deleteId);
                query3.setParameter(0,deleteId);
                query1.executeUpdate();
                query2.executeUpdate();
                query3.executeUpdate();
                return null;
            }
        });
        //删除寄语
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, deleteId);
        this.getHibernateTemplate().delete(noteDistrict);
    }

    /**
     * 删除寄语的异议
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteNoteObjection(String deleteId) throws Exception {
        NoteObjection noteObjection = this.getHibernateTemplate().get(NoteObjection.class, deleteId);
        this.getHibernateTemplate().delete(noteObjection);
        //更改寄语的异议个数统计
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, noteObjection.getNoteId());
        noteDistrict.setObjectionCoun(noteDistrict.getObjectionCoun()-1);
        this.getHibernateTemplate().update(noteDistrict);
    }

    /**
     * 删除寄语的举报
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteNoteToreport(String deleteId) throws Exception {
        NoteToreport noteToreport = this.getHibernateTemplate().get(NoteToreport.class, deleteId);
        this.getHibernateTemplate().delete(noteToreport);
        //更改寄语的举报个数统计
        NoteDistrict noteDistrict = this.getHibernateTemplate().get(NoteDistrict.class, noteToreport.getNoteId());
        noteDistrict.setObjectionCoun(noteDistrict.getObjectionCoun()-1);
        this.getHibernateTemplate().update(noteDistrict);
    }

    /**
     * 删除志同道合中的队伍
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteTeam(String deleteId) throws Exception {
        //删除志同道合关联数据
        this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                //删除队伍中的成员
                Query query1 = session.createQuery("delete from TeamMember where teamId = ?");
                //删除队伍的举报
                Query query2 = session.createQuery("delete from TeamToreport where teamId = ?");
                query1.setParameter(0,deleteId);
                query2.setParameter(0,deleteId);
                query1.executeUpdate();
                query2.executeUpdate();
                return null;
            }
        });
        //删除志同道合队伍
        TeamDistrict teamDistrict = this.getHibernateTemplate().get(TeamDistrict.class, deleteId);
        this.getHibernateTemplate().delete(teamDistrict);
    }

    /**
     * 删除志同道合的队伍中成员
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteTeamMember(String deleteId) throws Exception {
        TeamMember teamMember = this.getHibernateTemplate().get(TeamMember.class, deleteId);
        boolean b = teamMember.getTeamCaptainUid().equalsIgnoreCase(teamMember.getTeamMemberUid());
        if (!b){
            this.getHibernateTemplate().delete(teamMember);
            //更改志同道合的成员个数统计
            TeamDistrict teamDistrict = this.getHibernateTemplate().get(TeamDistrict.class, teamMember.getTeamId());
            teamDistrict.setCurrPeopleNum(teamDistrict.getCurrPeopleNum()-1);
            this.getHibernateTemplate().update(teamDistrict);
            System.out.println("dfdsfds");
        }else {
            throw new Exception("isTeamCaptain");
        }

    }

    /**
     * 删除志同道合的队伍中举报
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteTeamToreport(String deleteId) throws Exception {
        TeamToreport teamToreport = this.getHibernateTemplate().get(TeamToreport.class, deleteId);
        this.getHibernateTemplate().delete(teamToreport);
    }

    /**
     * 删除匿名说说
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteAnon(String deleteId) throws Exception {
        //删除志同道合关联数据
        this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                //删除匿名说说中的评论
                Query query1 = session.createQuery("delete from AnonComments where anonID = ?");
                //删除匿名说说中的点赞
                Query query2 = session.createQuery("delete from AnonLike where anonID = ?");
                //删除匿名说说的图片
                Query query3 = session.createQuery("delete from AnonPrice where anonID = ?");
                query1.setParameter(0,deleteId);
                query2.setParameter(0,deleteId);
                query3.setParameter(0,deleteId);
                query1.executeUpdate();
                query2.executeUpdate();
                query3.executeUpdate();
                return null;
            }
        });
        //删除匿名说说
        AnonDistrict anonDistrict = this.getHibernateTemplate().get(AnonDistrict.class, deleteId);
        this.getHibernateTemplate().delete(anonDistrict);
    }

    /**
     * 删除匿名说说的评论
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteAnonComment(String deleteId) throws Exception {
        AnonComments anonComments = this.getHibernateTemplate().get(AnonComments.class, deleteId);
        this.getHibernateTemplate().delete(anonComments);
    }

    /**
     * 禁用用户
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteUser(String deleteId) throws Exception {
        User user = this.getHibernateTemplate().get(User.class, deleteId);
        user.setStatus(0);
        this.getHibernateTemplate().update(user);

    }


}
