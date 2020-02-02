package com.dao;

import com.domain.*;

import java.util.List;

/**
 * @autor goh_liu
 * @date 2020/1/2 - 12:34
 */
public interface AdminDao {

    /**
     * 查看学长寄语
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<NoteDistrict> showNote(int startIndex, int pageSize) throws Exception;

    /**
     * 查看热门学长寄语
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<NoteDistrict> showNoteWithHot(int startIndex, int pageSize) throws Exception;

    /**
     * 查看学长寄语异议
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<NoteObjection> showNoteObjection(int startIndex, int pageSize) throws Exception;

    /**
     * 查看学长寄语举报
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<NoteToreport> showNoteToreport(int startIndex, int pageSize) throws Exception;

    /**
     * 统计学长寄语的数量
     * @throws Exception
     */
    int showNoteDistricteCoun() throws Exception;
    /**
     * 统计学长寄语的异议数量
     * @throws Exception
     */
    int showNoteObjectionCoun() throws Exception;
    /**
     * 统计学长寄语的举报数量
     * @throws Exception
     */
    int showNoteToreportCoun() throws Exception;

    /**
     * 统计志同道合（队伍）的数量
     * @return
     * @throws Exception
     */
    int showTeamCoun(String databaseName) throws Exception;

    /**
     * 查看志同道合（队伍）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<TeamDistrict> showTeam(int startIndex, int pageSize) throws Exception;

    /**
     * 查看志同道合（队伍成员）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<TeamMember> showTeamMenber(int startIndex, int pageSize) throws Exception;

    /**
     * 查看志同道合（举报）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<TeamToreport> showTeamToreport(int startIndex, int pageSize) throws Exception;

    /**
     * 查看志同道合（已满队伍）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<TeamDistrict> showTeamFull(int startIndex, int pageSize) throws Exception;

    /**
     * 查看志同道合（已满队伍数量）
     * @return
     * @throws Exception
     */
    int showTeamFullCoun() throws Exception;

    /**
     * 查看用户的数量
     */
    int showUserCoun() throws Exception;

    /**
     * 查看管理员的数量
     */
    int showIsAdminCoun() throws Exception;

    /**
     * 查看用户
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<User> showUser(int startIndex, int pageSize) throws Exception;

    /**
     * 查看管理员
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<User> showIsAdmin(int startIndex, int pageSize) throws Exception;

    /**
     * 查看匿名友人（说说）的数量
     * @return
     * @throws Exception
     */
    int showAonoDistricteCoun() throws Exception;

    /**
     * 查看匿名友人（评论）的数量
     * @return
     * @throws Exception
     */
    int showAnonCommentsCoun() throws Exception;

    /**
     * 查看匿名友人
     * @return
     * @throws Exception
     */
    List<AnonDistrict> showAono(int startIndex, int pageSize) throws Exception;

    /**
     * 查看匿名友人（热门）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<AnonDistrict> showAnonWithHot(int startIndex, int pageSize) throws Exception;

    /**
     * 查看匿名友人（评论）
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    List<AnonComments> showAnonComments(int startIndex, int pageSize) throws Exception;

    /**
     * 删除寄语
     * @param deleteId
     * @throws Exception
     */
    void deleteNote(String deleteId) throws Exception;

    /**
     * 删除寄语的异议
     * @param deleteId
     * @throws Exception
     */
    void deleteNoteObjection(String deleteId) throws Exception;

    /**
     * 删除寄语的举报
     * @param deleteId
     * @throws Exception
     */
    void deleteNoteToreport(String deleteId) throws Exception;

    /**
     * 删除志同道合中的队伍
     * @param deleteId
     * @throws Exception
     */
    void deleteTeam(String deleteId) throws Exception;

    /**
     * 删除志同道合的队伍中成员
     * @param deleteId
     * @throws Exception
     */
    void deleteTeamMember(String deleteId) throws Exception;

    /**
     * 删除志同道合的队伍中举报
     * @param deleteId
     * @throws Exception
     */
    void deleteTeamToreport(String deleteId) throws Exception;

    /**
     * 删除匿名说说
     * @param deleteId
     * @throws Exception
     */
    void deleteAnon(String deleteId) throws Exception;

    /**
     * 删除匿名说说的评论
     * @param deleteId
     * @throws Exception
     */
    void deleteAnonComment(String deleteId) throws Exception;

    /**
     * 禁用用户
     * @param deleteId
     * @throws Exception
     */
    void deleteUser(String deleteId) throws Exception;
}
