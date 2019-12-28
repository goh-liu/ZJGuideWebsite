package com.service;

import com.domain.*;

import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/21 - 16:39
 */
public interface NoteService {
    /**
     * 发表学长寄语
     */

    void published(NoteDistrict noteDistrict) throws Exception;

    /**
     * 查看单条寄语
     * @param noteId
     */
    NoteDistrict showOne(String noteId) throws Exception;

    /**
     * 查看热门寄语
     * @throws Exception
     */
    PageModel showNew(int curNum,String uid) throws Exception;

    /**
     * 查看热门寄语
     * @throws Exception
     */
    PageModel showHot(int curNum,String uid) throws Exception;

    /**
     * 用户点击“有用”按钮
     * @param noteUseful
     * @throws Exception
     */
    void clickUseful(NoteUseful noteUseful,String usefuled) throws Exception;

    /**
     * 用户点击“提出异议”按钮
     * @param noteObjection
     * @throws Exception
     */
    void clickObjection(NoteObjection noteObjection) throws Exception;

    /**
     * 用户点击“举报”按钮
     * @param noteToreport
     * @throws Exception
     */
    void clickToreport(NoteToreport noteToreport) throws Exception;

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
    Map showMyMessages(String uid) throws Exception;

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
     */
    void changeIsRead(String tableKey, String databaseTable) throws Exception;
}
