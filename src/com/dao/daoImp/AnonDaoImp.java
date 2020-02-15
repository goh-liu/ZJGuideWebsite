package com.dao.daoImp;

import com.dao.AnonDao;
import com.domain.*;
import com.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 15:09
 */
public class AnonDaoImp extends HibernateDaoSupport implements AnonDao {

    /**
     * 发表匿名说说
     * @param anon
     * @param list
     * @throws Exception
     */
    @Override
    public void anonWrite(AnonDistrict anon, List<AnonPrice> list) throws Exception {
        this.getHibernateTemplate().save(anon);
        if (!(list.isEmpty())){
            //判断是否有上传图片
            for (AnonPrice anonPrice : list) {
                this.getHibernateTemplate().save(anonPrice);
            }
        }
    }

    /**
     * 计算当前有多少条匿名说说
     * @return
     * @throws Exception
     */
    @Override
    public int findTotalRecords() throws Exception {
        String hql = "select count(*) from AnonDistrict where status = 1";
        List counList = this.getHibernateTemplate().find(hql);
        Long num = (Long)counList.get(0);
        return num.intValue();
    }


    /**
     * 分页显示匿名说说
     * @param startIndex
     * @param pageSize
     * @return
     * @throws Exception
     */
    @Override
    public HashMap showAnonWithPage(int startIndex, int pageSize) throws Exception {

        //用于存放内容list，图片list，评论map
        HashMap<String, Object> anonMap = new HashMap<>();

        //查找本页的匿名说说
        List<AnonDistrict> list1 = (List<AnonDistrict>) this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                // String hql = "from TeamDistrict t where t.teamType = ? and t.teamPeopleNum > t.currPeopleNum order by t.createTime desc";
                String hql = "from AnonDistrict  where status=1 order by deliveryTime desc";
                Query query = session.createQuery(hql);
                query.setFirstResult(startIndex);
                query.setMaxResults(pageSize);
                return query.list();
            }
        });

        String anonIDList = null;
        //将本页的匿名说说的ID遍历出来
        for (AnonDistrict anonDistrict : list1) {
            if (null == anonIDList){
                anonIDList ="'"+anonDistrict.getAnonID()+"'";
            }else {
                anonIDList = anonIDList + ",'" + anonDistrict.getAnonID()+"'";
            }
        }
        //根据匿名说说ID遍历出这些匿名说说的图片，
        String hql2="from AnonPrice where anonID in("+anonIDList+")";
        List<AnonPrice> anonPriceList = (List<AnonPrice>) this.getHibernateTemplate().find(hql2);

        //根据匿名说说ID查找出其评论
        String hql3 = "from AnonComments where anonID in("+anonIDList+")";
        List<AnonComments> anonCommentsList = (List<AnonComments>) this.getHibernateTemplate().find(hql3);
        //根据匿名说说ID查找出点赞情况
        String hql4 = "from AnonLike where anonID in("+anonIDList+")";
        List<AnonLike> likeList = (List<AnonLike>) this.getHibernateTemplate().find(hql4);
        //将匿名说说及其图片、评论存放在map中
        anonMap.put("anonCommentList",list1);
        anonMap.put("anonPriceList", anonPriceList);
        anonMap.put("commentList", anonCommentsList);
        anonMap.put("likeList",likeList);

        return anonMap;
    }

    /**
     * 用户评论/回复
     * @throws Exception
     */
    @Override
    public void anonComment(AnonComments anonComments) throws Exception {
        //写进anon_comments表中
        this.getHibernateTemplate().save(anonComments);
    }

    /**
     * 点赞功能
     * @param anonID 匿名说说的序号
     * @throws Exception
     */
    @Override
    public int anonLike(String anonID) throws Exception {
        //点赞人数+1
        AnonDistrict anonDistrict = this.getHibernateTemplate().get(AnonDistrict.class, anonID);
        anonDistrict.setLikeCoun(anonDistrict.getLikeCoun()+1);
        int likeCoun = anonDistrict.getLikeCoun();
        this.getHibernateTemplate().update(anonDistrict);
        //返回当前点赞人数
        return likeCoun;
    }

    /**
     * 取消点赞功能
     * @param anonID 匿名说说的序号
     * @throws Exception
     */
    @Override
    public int cancelAnonLike(String anonID) throws Exception {
        //点赞人数-1
        AnonDistrict anonDistrict = this.getHibernateTemplate().get(AnonDistrict.class, anonID);
        anonDistrict.setLikeCoun(anonDistrict.getLikeCoun()-1);
        int likeCoun = anonDistrict.getLikeCoun();
        this.getHibernateTemplate().update(anonDistrict);
        //返回当前点赞人数
        return likeCoun;
    }

    /**
     * 记录点赞的信息
     * @throws Exception
     */
    @Override
    public void recordLike( AnonLike anonLike ) throws Exception {
        this.getHibernateTemplate().save(anonLike);
    }

    /**
     * 删除记录点赞的信息
     * @throws Exception
     */
    @Override
    public void delRecordLike( AnonLike anonLike ) throws Exception {
       this.getHibernateTemplate().delete(anonLike);
    }

    /**
     * 用户点击删除匿名说说
     * @param anonID
     * @throws Exception
     */
    @Override
    public void anonDel(String anonID) throws Exception {
        AnonDistrict anonDistrict = this.getHibernateTemplate().get(AnonDistrict.class, anonID);
        anonDistrict.setStatus(0);
        this.getHibernateTemplate().update(anonDistrict);
    }

    /**
     * 查看用户发表过的匿名说说
     * @param uid 登陆用户的ID
     * @return 返回该用户所发表过的所有匿名说说信息
     * @throws Exception
     */
    @Override
    public List showUserAnon(String uid) throws Exception {
        String hql = "from AnonDistrict where uid = ? and status = 1 order by deliveryTime desc";
        List<AnonDistrict> userAnonlist = (List<AnonDistrict>) this.getHibernateTemplate().find(hql, uid);
        return userAnonlist;
    }

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     * @param anonID 该项的anonID
     * @return
     */
    @Override
    public HashMap showAnonDetails(String anonID) throws Exception {
        //用于存放内容list，图片list，评论map
        HashMap<String, Object> anonMap = new HashMap<>();

        //根据ID查找匿名说说
        AnonDistrict anonDistrict = this.getHibernateTemplate().get(AnonDistrict.class, anonID);
        //根据匿名说说ID找出这些匿名说说的图片
        String hql="from AnonPrice where anonID = ?";
        List<AnonPrice> anonPriceList = (List<AnonPrice>) this.getHibernateTemplate().find(hql,anonID);
        //根据匿名说说ID查找出其评论
        String hql1 = "from AnonComments where anonID = ?";
        List<AnonComments> anonCommentsList = (List<AnonComments>) this.getHibernateTemplate().find(hql1,anonID);
        //根据匿名说说ID查找出点赞情况
        String hql2= "from AnonLike where anonID = ?";
        List<AnonLike> likeList = (List<AnonLike>) this.getHibernateTemplate().find(hql2,anonID);
        //将匿名说说及其图片、评论存放在map中
        anonMap.put("anonDistrict",anonDistrict);
        anonMap.put("anonPriceList", anonPriceList);
        anonMap.put("commentList", anonCommentsList);
        anonMap.put("likeList",likeList);
        return anonMap;
    }

    /**
     * 查看用户评论
     * @param uid 登陆用户的ID
     * @return 返回该用户所评论过内容
     * @throws Exception
     */
    @Override
    public List<AnonComments> showUserComment(String uid) throws Exception {
        String hql = "from AnonComments where sourceUid = ? order by commentTime desc";
        List<AnonComments> usercommentlist = (List<AnonComments>) this.getHibernateTemplate().find(hql,uid);
        return usercommentlist;
    }

    /**
     * 查看用户消息
     * @param uid 登陆用户的ID
     * @return 返回该用户的消息
     * @throws Exception
     */
    @Override
    public List<AnonComments> showMessages(String uid) throws Exception {
        String hql = " from AnonComments where destUid = ? and isRead = 'false' order by commentTime desc";
        List<AnonComments> userMessages = (List<AnonComments>) this.getHibernateTemplate().find(hql,uid);
        return userMessages;
    }

    /**
     * 查看消息后将将消息设置为已读
     * @param counter 匿名说说评论的序号
     * @throws Exception
     */
    @Override
    public void readMessage(String counter) throws Exception {
        AnonComments anonComments = this.getHibernateTemplate().get(AnonComments.class, counter);
        anonComments.setIsRead("true");
        this.getHibernateTemplate().update(anonComments);
    }


}
