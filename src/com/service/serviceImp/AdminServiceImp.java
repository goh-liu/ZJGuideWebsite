package com.service.serviceImp;

import com.dao.AdminDao;
import com.dao.daoImp.AdminDaoImp;
import com.domain.*;
import com.service.AdminService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @autor goh_liu
 * @date 2020/1/2 - 12:33
 */
@Transactional
public class AdminServiceImp implements AdminService {
    private AdminDao adminDao = new AdminDaoImp();

    public AdminDao getAdminDao() {
        return adminDao;
    }

    public void setAdminDao(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    /**
     * 查看学长寄语
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<NoteDistrict> showNote(int startIndex,int pageSize) throws Exception {

        List<NoteDistrict> noteList = adminDao.showNote(startIndex, pageSize);
        return noteList;
    }

    /**
     * 查看热门学长寄语
     * @param startIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<NoteDistrict> showNoteWithHot(int startIndex, int pageSize) throws Exception{
        List<NoteDistrict> noteList = adminDao.showNoteWithHot(startIndex, pageSize);
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
        List<NoteObjection> noteList = adminDao.showNoteObjection(startIndex, pageSize);
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
        List<NoteToreport> noteList = adminDao.showNoteToreport(startIndex, pageSize);
        return noteList;
    }

    /**
     * 统计学长寄语的数量
     * @throws Exception
     */
    @Override
    public int showNoteDistricteCoun() throws Exception {
        int coun = adminDao.showNoteDistricteCoun();
        return coun;
    }
    /**
     * 统计学长寄语的异议数量
     * @throws Exception
     */
    @Override
    public int showNoteObjectionCoun() throws Exception {
        int coun = adminDao.showNoteObjectionCoun();
        return coun;
    }
    /**
     * 统计学长寄语的举报数量
     * @throws Exception
     */
    @Override
    public int showNoteToreportCoun() throws Exception {
        int coun = adminDao.showNoteToreportCoun();
        return coun;
    }

    /**
     * 统计志同道合（队伍）的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showTeamCoun(String databaseName) throws Exception {
        int coun;
        if ("TeamDistrictFull".equals(databaseName)){
            coun = adminDao.showTeamFullCoun();
        }else {
            coun = adminDao.showTeamCoun(databaseName);
        }
        return coun;
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
        List<TeamDistrict> teamList = adminDao.showTeam(startIndex, pageSize);
        return teamList;

    }

    /**
     * 查看志同道合（队伍成员）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public List<TeamMember> showTeamMenber(int startIndex, int pageSize) throws Exception {
        List<TeamMember> teamList = adminDao.showTeamMenber(startIndex, pageSize);
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
        List<TeamToreport> teamList = adminDao.showTeamToreport(startIndex, pageSize);
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
        List<TeamDistrict> teamList = adminDao.showTeamFull(startIndex, pageSize);
        return teamList;
    }

    /**
     * 查看用户的数量
     */
    @Override
    public int showUserCoun() throws Exception {
        int coun = adminDao.showUserCoun();
        return coun;
    }

    /**
     * 查看管理员的舒朗
     * @return
     * @throws Exception
     */
    @Override
    public int showIsAdminCoun() throws Exception {
        int coun = adminDao.showIsAdminCoun();
        return coun;
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
        List<User> userList = adminDao.showUser(startIndex, pageSize);
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
        List<User> userList = adminDao.showIsAdmin(startIndex, pageSize);
        return userList;
    }

    /**
     * 查看匿名友人（说说）的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showAonoDistricteCoun() throws Exception {
        int coun = adminDao.showAonoDistricteCoun();
        return coun;
    }

    /**
     * 查看匿名友人（评论）的数量
     * @return
     * @throws Exception
     */
    @Override
    public int showAnonCommentsCoun() throws Exception {
        int coun = adminDao.showAnonCommentsCoun();
        return coun;
    }

    /**
     * 查看匿名友人
     * @return
     * @throws Exception
     */
    @Override
    public List<AnonDistrict> showAnon(int startIndex, int pageSize) throws Exception {
        List<AnonDistrict> anonList = adminDao.showAono(startIndex, pageSize);
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
        List<AnonDistrict> anonList = adminDao.showAnonWithHot(startIndex, pageSize);
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
        List<AnonComments> anonList = adminDao.showAnonComments(startIndex, pageSize);
        return anonList;
    }

    /**
     * 删除寄语
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteNote(String deleteId) throws Exception {
        adminDao.deleteNote(deleteId);
    }

    /**
     * 删除寄语的异议
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteNoteObjection(String deleteId) throws Exception {
        adminDao.deleteNoteObjection(deleteId);
    }

    /**
     * 删除寄语的举报
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteNoteToreport(String deleteId) throws Exception {
        adminDao.deleteNoteToreport(deleteId);
    }

    /**
     * 删除志同道合中的队伍
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteTeam(String deleteId) throws Exception {
        adminDao.deleteTeam(deleteId);
    }

    /**
     * 删除志同道合的队伍中成员
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteTeamMember(String deleteId) throws Exception {
        adminDao.deleteTeamMember(deleteId);
    }

    /**
     * 删除志同道合的队伍中举报
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteTeamToreport(String deleteId) throws Exception {
        adminDao.deleteTeamToreport(deleteId);
    }

    /**
     * 删除匿名说说
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteAnon(String deleteId) throws Exception {
        adminDao.deleteAnon(deleteId);
    }

    /**
     * 删除匿名说说的评论
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteAnonComment(String deleteId) throws Exception {
        adminDao.deleteAnonComment(deleteId);
    }

    /**
     * 禁用用户
     * @param deleteId
     * @throws Exception
     */
    @Override
    public void deleteUser(String deleteId) throws Exception {
        adminDao.deleteUser(deleteId);
    }


}
