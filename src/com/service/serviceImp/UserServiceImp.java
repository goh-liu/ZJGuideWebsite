package com.service.serviceImp;

import com.dao.UserDao;
import com.domain.User;
import com.service.UserService;
import com.utils.BeanFactory;

/**
 * @autor goh_liu
 * @date 2019/7/30 - 23:36
 */
public class UserServiceImp implements UserService {


    @Override
    public User userLogin(String uname, String upassword) throws Exception {
        UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
        User user = userDao.userLogin(uname, upassword);
        if(null == user){
            throw new RuntimeException("用户名/密码错误,请重新登录");
        }else{
            return user;
        }
    }

    @Override
    public User autoLogin(String uid) throws Exception {
        UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
        return userDao.findUserByID(uid);

    }
    //用户注册
    @Override
    public void userRegister(User user) throws Exception {
        UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
        userDao.userRegister(user);
    }

    //查询用户已经注册过，或者重名
    @Override
    public String findUserbyNameOrTele(String uname, String telephone) throws Exception {
        UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
        return userDao.findUserbyNameOrTele(uname,telephone);
    }

    //找回密码
    @Override
    public void findpassword(String telephone,String newpassword) throws Exception {
        UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
        userDao.findpassword(telephone,newpassword);
    }
}
