package com.service.serviceImp;

import com.dao.AnonDao;
import com.domain.*;
import com.service.AnonService;
import com.utils.BeanFactory;

import java.util.HashMap;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 15:02
 */
public class AnonServiceImp implements AnonService {

    /**
     * 发表匿名说说
     * @param anon
     * @param list
     * @throws Exception
     */
    @Override
    public void anonWrite(AnonDistrict anon, List<AnonPrice> list) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        anonDao.anonWrite(anon,list);
    }

    /**
     * 分页查看匿名说说
     * @param curNum
     * @return
     * @throws Exception
     */
    @Override
    public PageModel showAnonWithPage(int curNum) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        //1_创建PageModel对象 目的:计算分页参数
        int totalRecords=anonDao.findTotalRecords();//获取总记录条数
        PageModel pm=new PageModel(curNum,totalRecords,8);
        //2_关联集合
        HashMap anonMap = anonDao.showAnonWithPage(pm.getStartIndex(), pm.getPageSize());
        pm.setMap(anonMap);

        //3_关联url,后面的&是为了连接页码
        pm.setUrl("AnonServlet?method=showAnonWithPage&");
        return pm;
    }

    /**
     * 评论
     * @param user
     * @param counter
     * @param anonComment
     * @throws Exception
     */
    @Override
    public void anonComment(User user,String comment_destUid,String comment_destUname,String commentOrReply,String counter, String anonComment) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        anonDao.anonComment(user,comment_destUid,comment_destUname,commentOrReply, counter, anonComment);
    }

    /**
     * 点赞功能
     * @param counter
     * @throws Exception
     */
    @Override
    public int anonLike(String counter) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        return anonDao.anonLike(counter);

    }
    /**
     * 取消点赞功能
     * @param counter
     * @throws Exception
     */
    @Override
    public int cancelAnonLike(String counter) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        return anonDao.cancelAnonLike(counter);
    }

    /**
     * 记录点赞的信息
     * @param counter 匿名说说序号
     * @param uid 点赞者
     * @throws Exception
     */
    @Override
    public void recordLike(String counter, String uid) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        anonDao.recordLike(counter,uid);
    }

    /**
     * 删除记录点赞的信息
     * @param counter 匿名说说序号
     * @param uid 点赞者
     * @throws Exception
     */
    @Override
    public void delRecordLike(String counter, String uid) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        anonDao.delRecordLike(counter,uid);

    }

    /**
     * 用户点击删除匿名说说
     * @param counter
     * @throws Exception
     */
    @Override
    public void anonDel(String counter) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        anonDao.anonDel(counter);
    }

    /**
     * 查看用户发表过的匿名说说
     * @param uid 登陆用户的ID
     * @return 返回该用户所发表过的所有匿名说说信息
     * @throws Exception
     */
    @Override
    public List showUserAnon(String uid) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        return anonDao.showUserAnon(uid);
    }

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     * @param counter 该项的counter
     * @return
     */
    @Override
    public HashMap showAnonDetails(String counter) throws Exception{
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        return anonDao.showAnonDetails(counter);
    }

    /**
     * 查看用户评论
     * @param uid 登陆用户的ID
     * @return 返回该用户所评论过内容
     * @throws Exception
     */
    @Override
    public List<AnonComments> showUserComment(String uid) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        return anonDao.showUserComment(uid);
    }

    /**
     * 查看用户消息
     * @param uid 登陆用户的ID
     * @return 返回该用户的消息
     * @throws Exception
     */
    @Override
    public List<AnonComments> showMessages(String uid) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        return anonDao.showMessages(uid);
    }

    /**
     * 查看消息后将将消息设置为已读
     * @param anonID 匿名说说ID
     * @param destUid 本消息是对谁说的
     * @throws Exception
     */
    @Override
    public void readMessage(String anonID,String destUid) throws Exception {
        AnonDao anonDao = (AnonDao)BeanFactory.createObject("AnonDao");
        anonDao.readMessage(anonID,destUid);
    }


}
