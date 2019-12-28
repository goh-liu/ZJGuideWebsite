package com.dao;

import com.domain.AnonDistrict;
import com.domain.AnonComments;
import com.domain.AnonPrice;
import com.domain.User;

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

    void anonComment(User user,String comment_destUid,String comment_destUname,String commentOrReply, String counter, String anonComment) throws Exception;

    int anonLike(String counter) throws Exception;

    int cancelAnonLike(String counter) throws Exception;

    void recordLike(String counter, String uid) throws Exception;

    void delRecordLike(String counter, String uid) throws Exception;

    /**
     * 用户点击删除匿名说说
     * @param counter
     * @throws Exception
     */
    void anonDel(String counter) throws Exception;

    /**
     * 查看用户发表过的匿名说说
     * @param uid 登陆用户的ID
     * @return 返回该用户所发表过的所有匿名说说信息
     * @throws Exception
     */
    List showUserAnon(String uid) throws Exception;

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     * @param counter 该项的counter
     * @return
     */
    HashMap showAnonDetails(String counter) throws Exception;

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
     * @param anonID 匿名说说ID
     * @param destUid 本消息是对谁说的
     * @throws Exception
     */
    void readMessage(String anonID,String destUid) throws Exception;
}
