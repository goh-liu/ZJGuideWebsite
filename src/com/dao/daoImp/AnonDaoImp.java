package com.dao.daoImp;

import com.dao.AnonDao;
import com.domain.*;
import com.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 15:09
 */
public class AnonDaoImp implements AnonDao {

    /**
     * 发表匿名说说
     * @param anon
     * @param list
     * @throws Exception
     */
    @Override
    public void anonWrite(AnonDistrict anon, List<AnonPrice> list) throws Exception {
        String sql1 = "insert into anon_district(anonID,uid,anonContent,deliveryTime,status) values(?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params1 = {anon.getAnonID(),anon.getUid(),anon.getAnonContent(),
                anon.getDeliveryTime(),anon.getStatus()};
        queryRunner.update(sql1,params1);
        if (!(list.isEmpty())){//判断是否有上传图片
            String sql2 = "insert into anon_price values(?,?,?)";
            for (AnonPrice anonPrice : list) {
                Object[] params2 = {anonPrice.getAnonID(), anonPrice.getPriceUrl(),anon.getStatus()};
                queryRunner.update(sql2,params2);
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
        String sql = "select count(*) from anon_district";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num = (Long)queryRunner.query(sql, new ScalarHandler());
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

        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        //查找本业的匿名说说
        String sql1="select * from anon_district where status=1 order by deliveryTime desc limit ? , ?";
        List<AnonDistrict> list1 = qr.query(sql1, new BeanListHandler<AnonDistrict>(AnonDistrict.class), startIndex, pageSize);
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
        String sql2="select * from anon_price where anonID in("+anonIDList+")";
        List<AnonPrice> anonPriceList = qr.query(sql2, new BeanListHandler<AnonPrice>(AnonPrice.class));
        //根据匿名说说ID查找出其评论
        String sql3 = "select * from anon_comments where anonID in("+anonIDList+")";
        List<AnonComments> anonCommentsList = qr.query(sql3, new BeanListHandler<AnonComments>(AnonComments.class));
        //根据匿名说说ID查找出点赞情况
        String sql4 = "select * from anon_like where anonID in("+anonIDList+")";
        List<AnonLike> likeList = qr.query(sql4, new BeanListHandler<AnonLike>(AnonLike.class));
        //将匿名说说及其图片、评论存放在map中
        anonMap.put("anonCommentList",list1);
        anonMap.put("anonPriceList", anonPriceList);
        anonMap.put("commentList", anonCommentsList);
        anonMap.put("likeList",likeList);

        return anonMap;
    }

    /**
     * 用户评论/回复
     * @param user 登陆用户
     * @param counter 本条说说匿名者的自动增长序号
     * @param anonComment 评论的内容
     * @throws Exception
     */
    @Override
    public void anonComment(User user, String comment_destUid,String comment_destUname,String commentOrReply,
                            String counter, String anonComment) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        AnonDistrict anonDistrict = null;
        //查找匿名说说详细信息
        String sql1 = "select * from anon_district where counter = " + counter;
        anonDistrict = queryRunner.query(sql1, new BeanHandler<AnonDistrict>(AnonDistrict.class));
        if ("anonUID".equals(comment_destUid)){
            comment_destUid = anonDistrict.getUid();
        }
        //写进anon_comments表中
        String sql = "insert into anon_comments values(?,?,?,?,?,?,?,?,?)";
        if ("2".equals(commentOrReply)){

            if(user.getUid().equals(anonDistrict.getUid())){
                //匿名者回复自己
                Object[] params = {anonDistrict.getAnonID(),user.getUid(),comment_destUname,
                        user.getUid(),comment_destUname,anonComment,new Date(),2,0};
                //将该回复写进anon_comments表中------>回复
                queryRunner.update(sql,params);
            }else{
                Object[] params = {anonDistrict.getAnonID(),user.getUid(),user.getUname(),
                        comment_destUid,comment_destUname,anonComment,new Date(),2,0};
                //将该回复写进anon_comments表中------>回复
                queryRunner.update(sql,params);
            }

        }else{
            if(user.getUid().equals(anonDistrict.getUid())) {
                //匿名者评论自己
                //将该评论写进anon_comments表中------>评论
                Object[] params = {anonDistrict.getAnonID(),user.getUid(),comment_destUname,
                        user.getUid(),comment_destUname,anonComment,new Date(),1,0};
                queryRunner.update(sql,params);
            }else{
                //将该评论写进anon_comments表中------>评论
                Object[] params = {anonDistrict.getAnonID(),user.getUid(),user.getUname(),
                        comment_destUid,comment_destUname,anonComment,new Date(),1,0};
                queryRunner.update(sql,params);

            }

        }
    }

    /**
     * 点赞功能
     * @param counter 匿名说说的序号
     * @throws Exception
     */
    @Override
    public int anonLike(String counter) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        //点赞人数+1
        String sql1 = "update anon_district set likeCoun = likeCoun + 1 where counter = "+counter;
        queryRunner.update(sql1);
        //查找当前点赞人数
        String sql2 = "select likeCoun from anon_district where counter = "+counter;
        Integer likeCoun = (Integer) queryRunner.query(sql2, new ScalarHandler());
        return likeCoun.intValue();
    }

    /**
     * 取消点赞功能
     * @param counter 匿名说说的序号
     * @throws Exception
     */
    @Override
    public int cancelAnonLike(String counter) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        //点赞人数-1
        String sql = "update anon_district set likeCoun = likeCoun - 1 where counter = "+counter;
        queryRunner.update(sql);
        //查找当前点赞人数
        String sql2 = "select likeCoun from anon_district where counter = "+counter;
        Integer likeCoun = (Integer) queryRunner.query(sql2, new ScalarHandler());
        return likeCoun.intValue();
    }

    /**
     * 记录点赞的信息
     * @param counter 匿名说说序号
     * @param uid 点赞者
     * @throws Exception
     */
    @Override
    public void recordLike(String counter, String uid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        //查找匿名说说ID
        String sql1 = "select anonID from anon_district where counter = " + counter;
        String anonID = (String) queryRunner.query(sql1,new ScalarHandler());
        //记录
        String sql2 = "insert into anon_like(anonID,likeUID) values(?,?)";
        queryRunner.update(sql2,anonID,uid);
    }

    /**
     * 删除记录点赞的信息
     * @param counter 匿名说说序号
     * @param uid 点赞者
     * @throws Exception
     */
    @Override
    public void delRecordLike(String counter, String uid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        //查找匿名说说ID
        String sql1 = "select anonID from anon_district where counter = " + counter;
        String anonID = (String) queryRunner.query(sql1,new ScalarHandler());
        //删除
        String sql2 = "delete from anon_like where anonID = ? and likeUID = ?";
        queryRunner.update(sql2,anonID,uid);
    }

    /**
     * 用户点击删除匿名说说
     * @param counter
     * @throws Exception
     */
    @Override
    public void anonDel(String counter) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "update anon_district set status = 0 where counter = ?";
        queryRunner.update(sql,counter);
    }

    /**
     * 查看用户发表过的匿名说说
     * @param uid 登陆用户的ID
     * @return 返回该用户所发表过的所有匿名说说信息
     * @throws Exception
     */
    @Override
    public List showUserAnon(String uid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from anon_district where uid = ?  and status = 1 order by deliveryTime desc";
        List<AnonDistrict> userAnonlist = queryRunner.query(sql, new BeanListHandler<AnonDistrict>(AnonDistrict.class), uid);
        return userAnonlist;
    }

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     * @param counter 该项的counter,或者anonID
     * @return
     */
    @Override
    public HashMap showAnonDetails(String counter) throws Exception {
        //用于存放内容list，图片list，评论map
        HashMap<String, Object> anonMap = new HashMap<>();

        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        // 编译正则表达式
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher matcher = pattern.matcher(counter);
        // 查找字符串中是否有匹配正则表达式的字符/字符串
        String sql = null;
        if (matcher.find()){
            sql = "select * from anon_district where counter = ?";
        }else {
            sql = "select * from anon_district where anonID = ?";
        }
        AnonDistrict anon = queryRunner.query(sql,new BeanHandler<AnonDistrict>(AnonDistrict.class), counter);
        //根据匿名说说ID遍历出这些匿名说说的图片，
        String sql2="select * from anon_price where anonID = ?";
        List<AnonPrice> anonPriceList = queryRunner.query(sql2, new BeanListHandler<AnonPrice>(AnonPrice.class),anon.getAnonID());
        //根据匿名说说ID查找出其评论
        String sql3 = "select * from anon_comments where anonID = ?";
        List<AnonComments> anonCommentsList = queryRunner.query(sql3, new BeanListHandler<AnonComments>(AnonComments.class),anon.getAnonID());
        //根据匿名说说ID查找出点赞情况
        String sql4 = "select * from anon_like where anonID = ?";
        List<AnonLike> likeList = queryRunner.query(sql4, new BeanListHandler<AnonLike>(AnonLike.class),anon.getAnonID());
        //将匿名说说及其图片、评论存放在map中
        anonMap.put("anonDistrict",anon);
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
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from anon_comments where sourceUid = ? order by commentTime desc";
        List<AnonComments> usercommentlist = queryRunner.query(sql, new BeanListHandler<AnonComments>(AnonComments.class), uid);
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
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from anon_comments where destUid = ? and isRead = 0 order by commentTime desc";
        List<AnonComments> userMessages = queryRunner.query(sql, new BeanListHandler<AnonComments>(AnonComments.class), uid);
        return userMessages;
    }

    /**
     * 查看消息后将将消息设置为已读
     * @param anonID 匿名说说ID
     * @param destUid 本消息是对谁说的
     * @throws Exception
     */
    @Override
    public void readMessage(String anonID,String destUid) throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "update anon_comments set isRead = 1  where anonID = ? and destUid = ? and isRead = 0";
        queryRunner.update(sql,anonID,destUid);
    }


}
