package com.service.serviceImp;

import com.dao.AnonDao;
import com.dao.NoteDao;
import com.dao.UserDao;
import com.domain.*;
import com.service.AnonService;
import com.utils.BeanFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 15:02
 */
@Transactional
public class AnonServiceImp implements AnonService {

    /**
     * 注入anonDao
     */
    private AnonDao anonDao;

    public void setAnonDao(AnonDao anonDao) {
        this.anonDao = anonDao;
    }

    /**
     * 发表匿名说说
     * @param anon
     * @param list
     * @throws Exception
     */
    @Override
    public void anonWrite(AnonDistrict anon, List<AnonPrice> list) throws Exception {
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
        //1_创建PageModel对象 目的:计算分页参数
        //获取总记录条数
        int totalRecords=anonDao.findTotalRecords();
        PageModel pm=new PageModel(curNum,totalRecords,8);
        //2_关联集合
        HashMap anonMap = anonDao.showAnonWithPage(pm.getStartIndex(), pm.getPageSize());
        pm.setMap(anonMap);

        //3_关联url,后面的?是为了连接页码
        pm.setUrl("anon_showAnonWithPage.action?");
        return pm;
    }

    /**
     * 评论
     * @throws Exception
     */
    @Override
    public void anonComment(AnonComments anonComments) throws Exception {
        anonDao.anonComment(anonComments);
    }

    /**
     * 点赞功能
     * @param anonID
     * @throws Exception
     */
    @Override
    public int anonLike(String anonID, String uid) throws Exception {
        //将点赞的人和点赞的匿名说说保存在anon_like表中
        AnonLike anonLike = new AnonLike();
        anonLike.setAnonID(anonID);
        anonLike.setLikeUID(uid);
        anonDao.recordLike(anonLike);
        //更新anon_district表的likeCoun列并返回当前likeCoun
        return anonDao.anonLike(anonID);

    }
    /**
     * 取消点赞功能
     * @throws Exception
     */
    @Override
    public int cancelAnonLike(String anonID, String uid) throws Exception {
        //将点赞的人和点赞的匿名说说保存在anon_like表中
        AnonLike anonLike = new AnonLike();
        anonLike.setAnonID(anonID);
        anonLike.setLikeUID(uid);
        anonDao.delRecordLike(anonLike);
        //更新anon_district表的likeCoun列并返回当前likeCoun
        return anonDao.cancelAnonLike(anonID);
    }


    /**
     * 用户点击删除匿名说说
     * @param anonID
     * @throws Exception
     */
    @Override
    public void anonDel(String anonID) throws Exception {
        anonDao.anonDel(anonID);
    }

    /**
     * 查看用户发表过的匿名说说
     * @param uid 登陆用户的ID
     * @return 返回该用户所发表过的所有匿名说说信息
     * @throws Exception
     */
    @Override
    public List showUserAnon(String uid) throws Exception {
        return anonDao.showUserAnon(uid);
    }

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     * @param anonID 该项的anonID
     * @return
     */
    @Override
    public HashMap showAnonDetails(String anonID) throws Exception{
        return anonDao.showAnonDetails(anonID);
    }

    /**
     * 查看用户评论
     * @param uid 登陆用户的ID
     * @return 返回该用户所评论过内容
     * @throws Exception
     */
    @Override
    public List<AnonComments> showUserComment(String uid) throws Exception {
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
        return anonDao.showMessages(uid);
    }

    /**
     * 查看消息后将将消息设置为已读
     * @param counter 匿名说说评论的序号
     * @throws Exception
     */
    @Override
    public void readMessage(String counter) throws Exception {
        anonDao.readMessage(counter);
    }


}
