package com.dao.daoImp;

import com.dao.UserDao;
import com.domain.User;
import com.utils.JDBCUtils;
import com.utils.JeditUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @autor goh_liu
 * @date 2019/7/30 - 23:39
 */
public class UserDaoImp implements UserDao {
    //用户登录
    @Override
    public User userLogin(String uname, String upassword) throws Exception {
        User user = null;
        String sql1 = "select * from user where telephone = ? and upassword = ?";
        String sql2 = "select * from user where uname = ? and upassword = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        user = queryRunner.query(sql1, new BeanHandler<User>(User.class), uname, upassword);
        if(null == user){
            user = queryRunner.query(sql2, new BeanHandler<User>(User.class), uname, upassword);
        }
        return user;
    }

    //根据用户ID获取用户信息
    @Override
    public User findUserByID(String uid) throws Exception {
        String sql = "select * from user where uid = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),uid);
    }

    //用户注册
    @Override
    public void userRegister(User user) throws Exception {
        String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Object[] params = {user.getUid(),user.getUname(),user.getUpassword(),user.getSex(),
                user.getTelephone(),user.getSchool(),user.getSecondarySchool(),
                user.getUgrade(),user.getUclass(),user.getState(),null };
        queryRunner.update(sql,params);
        //将该新用户的用户ID和用户名存放在redis数据库中
        Jedis jedis = JeditUtils.getJedis();
        jedis.set(user.getUid(),user.getUname());
        JeditUtils.closeJedis(jedis);
    }

    //根据用户名或者手机号码进行查询，不允许出现同名字或者同电话号码的用户注册
    @Override
    public String findUserbyNameOrTele(String uname, String telephone) throws Exception {
        String sql1 = "select count(*) from user where uname = ? ";
        String sql2 = "select count(*) from user where telephone = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        Long num1 =(Long)queryRunner.query(sql1, new ScalarHandler(), uname);
        Long num2 =(Long)queryRunner.query(sql2, new ScalarHandler(), telephone);
        if (0 != num1){
            return "existUname";
        }
        if (0 != num2 ){
            return "existTelephone";
        }
        return "noExist";
    }

    //根据手机号码找回密码
    @Override
    public void findpassword(String telephone,String newpassword) throws Exception {
        String sql = "update user set upassword = ? where telephone = ?";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        int rows = queryRunner.update(sql,newpassword,telephone);
        if (rows == 0){
            throw new RuntimeException("没有该手机号");
        }
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
