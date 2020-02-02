package com.dao.daoImp;

import com.dao.UserDao;
import com.domain.User;
import com.domain.UserIdAndName;
import com.utils.JDBCUtils;
import com.utils.JeditUtils;
import com.utils.MD5Utils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.dao.support.DaoSupport;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import javax.naming.Name;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @autor goh_liu
 * @date 2019/7/30 - 23:39
 */
public class UserDaoImp extends HibernateDaoSupport implements UserDao {

    /**
     * 用户注册
     * @param user
     * @throws Exception
     */
    @Override
    public void userRegister(User user) throws Exception {
        this.getHibernateTemplate().save(user);
    }

    /**
     * 用户登录
     * @param uname
     * @param upassword
     * @return
     * @throws Exception
     */
    @Override
    public User userLogin(String uname, String upassword) throws Exception {
        User user = null;
        String hql = "from User u where u.telephone = ? or u.uname = ? and u.upassword = ?";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql, uname, uname, upassword);
        for (User user1 : list) {
            user = user1;
        }
       /* User user = null;
        String sql1 = "select * from user where telephone = ? and upassword = ?";
        String sql2 = "select * from user where uname = ? and upassword = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        user = queryRunner.query(sql1, new BeanHandler<User>(User.class), uname, upassword);
        if(null == user){
            user = queryRunner.query(sql2, new BeanHandler<User>(User.class), uname, upassword);
        }*/
        return user;
    }


    /**
     * 根据用户ID获取用户信息
     */
    @Override
    public User findUserByID(String uid) throws Exception {
        User user = this.getHibernateTemplate().get(User.class, uid);
        /*String sql = "select * from user where uid = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),uid);*/
        return user;
    }


    /**
     * 根据用户名或者手机号码进行查询，不允许出现同名字或者同电话号码的用户注册
     * @param uname
     * @param telephone
     * @return
     * @throws Exception
     */
    @Override
    public String findUserbyNameOrTele(String uname, String telephone) throws Exception {
        User user = null;
        String hql1 = "select count(*) from User u where u.uname = ? ";
        List list = this.getHibernateTemplate().find(hql1, uname);
        Long num1 = (Long)list.get(0);
        if (0 != num1){
            return "existUname";
        } else {
            String hql2 = "select count(*) from User u where u.telephone = ? ";
            List list2 = this.getHibernateTemplate().find(hql2,telephone);
            Long num2 = (Long)list.get(0);
            if (0 != num2 ){
                return "existTelephone";
            }
        }

       /* String sql1 = "select count(*) from user where uname = ? ";
        String sql2 = "select count(*) from user where telephone = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num1 =(Long)queryRunner.query(sql1, new ScalarHandler(), uname);
        Long num2 =(Long)queryRunner.query(sql2, new ScalarHandler(), telephone);
        if (0 != num1){
            return "existUname";
        }
        if (0 != num2 ){
            return "existTelephone";
        }*/
        return "noExist";
    }

    /**
     * 根据手机号码找回密码
     * @param telephone
     * @param newpassword
     * @throws Exception
     */
    @Override
    public void findpassword(String telephone,String newpassword) throws Exception {
        User user = null;
        String hql = "from User u where u.telephone = ? ";
        List<User> list = (List<User>) this.getHibernateTemplate().find(hql,telephone);
        for (User user1 : list) {
            user = user1;
        }
        if (user == null){
            throw new RuntimeException("没有该手机号");
        }else{
            user.setUpassword(telephone);
            this.getHibernateTemplate().update(user);
        }
        /*String sql = "update user set upassword = ? where telephone = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        int rows = queryRunner.update(sql,newpassword,telephone);
        if (rows == 0){
            throw new RuntimeException("没有该手机号");
        }*/
    }

    /**
     * 登陆管理后台
     * @param uname
     * @param upassword
     * @throws Exception
     */
    @Override
    public UserIdAndName adminLogin(String uname, String upassword) throws Exception {
        UserIdAndName user = null;
        String hql = "from UserIdAndName where telephone = ? or uname = ? and upassword = ? and isAdmin = '是'";
        List<UserIdAndName> list = (List<UserIdAndName>) this.getHibernateTemplate().find(hql, uname, uname, upassword);
        for (UserIdAndName user1 : list) {
            user = user1;
        }
        return user;
    }

    /**
     * 更新redis数据库,如特殊情况，一般不需要用到
     * @throws Exception
     */
    public void updateRedis() throws Exception {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "select * from user";
        List<User> users = queryRunner.query(sql, new BeanListHandler<User>(User.class));
        Jedis jedis = JeditUtils.getJedis();
        for (User user1 : users) {
            jedis.set(user1.getUid(),user1.getUname());
        }
        JeditUtils.closeJedis(jedis);
    }


}
