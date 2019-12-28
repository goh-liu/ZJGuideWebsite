package com.dao;

import com.domain.NoteDistrict;
import com.domain.NoteObjection;
import com.domain.NoteToreport;
import com.domain.NoteUseful;

import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/21 - 9:36
 */
public interface NoteDao {
    /**
     * 发表学长寄语
     */
    void published(NoteDistrict noteDistrict) throws Exception;

    /**
     * 计算当前数据库中有多少条寄语
     * @return
     * @throws Exception
     */
    int counOfNote() throws Exception;

    /**
     * 计算当前数据库中有多少条热门寄语
     * @return
     * @throws Exception
     */
    int counOfNote_hot() throws Exception;

    /**
     * 查看单条寄语
     * @param noteId
     */
    NoteDistrict showOne(String noteId) throws Exception;

    /**
     * 查看最新的寄语
     * @param startIndex
     * @param pageSize
     * @return
     */
    List<NoteDistrict> showNew(int startIndex, int pageSize) throws Exception;

    /**
     * 查看热门寄语
     * @throws Exception
     */
    List<NoteDistrict> showHot(int startIndex, int PageSize) throws Exception;

    /**
     * 用户点击“有用”按钮
     * @param noteUseful
     * @throws Exception
     */
    void clickUseful(NoteUseful noteUseful,String usefuled) throws Exception;

    /**
     * 根据usefulIds查询寄语的“有用”记录
     * @throws Exception
     */
    List<NoteUseful> findUsefulByUsefulId(String usefulIds) throws Exception;

    /**
     * 用户点击“有用”按钮，note_district表中的usefulcoun字段的数值加1
     * @param noteUseful
     * @throws Exception
     */
    void changeUsefulCoun(NoteUseful noteUseful,String usefuled) throws Exception;

    /**
     * 用户点击“提出异议”按钮
     * @param noteObjection
     * @throws Exception
     */
    void clickObjection(NoteObjection noteObjection) throws Exception;

    /**
     * 用户点击“提出异议”按钮，note_district表中的objectionCoun字段的数值加1
     * @param noteObjection
     * @throws Exception
     */
    void changeObjectionCoun(NoteObjection noteObjection) throws Exception;

    /**
     * 根据objectionIds查询寄语的“提出异议”记录
     * @throws Exception
     */
    List<NoteObjection> findObjectionByObjectionId(String objectionIds) throws Exception;

    /**
     * 用户点击“举报”按钮
     * @param noteToreport
     * @throws Exception
     */
    void clickToreport(NoteToreport noteToreport) throws Exception;

    /**
     * 用户点击“举报”按钮，note_district表中的ToreportCoun字段的数值加1
     * @param noteToreport
     * @throws Exception
     */
    void changeToreportCoun(NoteToreport noteToreport) throws Exception;

    /**
     * 根据toreportIds查询寄语的“举办”记录
     * @throws Exception
     */
    List<NoteToreport> findToreportByToreportId(String toreportIds) throws Exception;

    /**
     * 显示登陆用户发表的寄语
     * @param uid
     * @return
     * @throws Exception
     */
    List<NoteDistrict> showMyNote(String uid) throws Exception;

    /**
     * 删除寄语
     * @param noteId
     * @throws Exception
     */
    void deleteNote(String noteId) throws Exception;

    /**
     * 查看我的消息
     * @throws Exception
     * @return
     */
    Map showMyMessages(String myNoteIds) throws Exception;

    /**
     * 修改寄语的内容
     * @param noteDistrict
     * @throws Exception
     */
    void alterNote(NoteDistrict noteDistrict) throws Exception;

    /**
     * 更改为已读
     * @param tableKey
     * @param databaseTable
     * @throws Exception
     */
    void changeIsRead(String tableKey, String databaseTable) throws Exception;
}
