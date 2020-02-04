package com.dao;

import com.domain.*;

import java.util.HashMap;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 15:09
 */
public interface AnonDao {

    void anonWrite(AnonDistrict anon, List<AnonPrice> list) throws Exception ;

    int findTotalRecords() throws Exception ;

    HashMap showAnonWithPage(int startIndex, int pageSize) throws Exception;

    void anonComment(AnonComments anonComments) throws Exception;

    int anonLike(String anonID) throws Exception;

    int cancelAnonLike(String counter) throws Exception;

    void recordLike(AnonLike anonLike ) throws Exception;

    void delRecordLike( AnonLike anonLike ) throws Exception;

    /**
     * 用户点击删除匿名说说
     * @param anonID
     * @throws Exception
     */
    void anonDel(String anonID) throws Exception;

    /**
     * 查看用户发表过的匿名说说
     * @param uid 登陆用户的ID
     * @return 返回该用户所发表过的所有匿名说说信息
     * @throws Exception
     */
    List showUserAnon(String uid) throws Exception;

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     * @param anonID 该项的anonID
     * @return
     */
    HashMap showAnonDetails(String anonID) throws Exception;

    /**
     * 查看用户评论
     * @param uid 登陆用户的ID
     * @return 返回该用户所评论过内容
     * @throws Exception
     */
    List<AnonComments> showUserComment(String uid) throws Exception;

    /**
     * 查看用户消息
     * @param uid 登陆用户的ID
     * @return 返回该用户的消息
     * @throws Exception
     */
    List<AnonComments> showMessages(String uid) throws Exception;

    /**
     * 查看消息后将将消息设置为已读
     * @param counter 匿名说说评论的序号
     * @throws Exception
     */
    void readMessage(String counter) throws Exception;
}
