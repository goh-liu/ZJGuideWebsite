package com.service.serviceImp;

import com.dao.UserDao;
import com.domain.User;
import com.domain.UserIdAndName;
import com.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

/**
 * @autor goh_liu
 * @date 2019/7/30 - 23:36
 */
@Service("userService")
@Transactional
public class UserServiceImp implements UserService {

    @Resource(name = "userDao")
    private UserDao userDao;

    /**
     * 注册用户
     * @param user
     * @throws Exception
     */
    @Override
    public void userRegister(User user) throws Exception {
        userDao.userRegister(user);
    }

    /**
     * 用户登录
     * @param uname
     * @param upassword
     * @return
     * @throws Exception
     */
    @Override
    public UserIdAndName userLogin(String uname, String upassword) throws Exception {
        UserIdAndName user = userDao.userLogin(uname, upassword);
        if(null == user){
            throw new RuntimeException("用户名/密码错误,请重新登录");
        }else{
            return user;
        }
    }

    /**
     * 自动登陆
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public User autoLogin(String uid) throws Exception {
        return userDao.findUserByID(uid);
    }

    /**
     * 查看注册时手机号码和用户名是否存在
     * @param uname
     * @param telephone
     * @return
     * @throws Exception
     */
    @Override
    public String findUserbyNameOrTele(String uname, String telephone) throws Exception {
        return userDao.findUserbyNameOrTele(uname,telephone);
    }

    /**
     * 找回密码
     * @param telephone
     * @param newpassword
     * @throws Exception
     */
    @Override
    public void findpassword(String telephone,String newpassword) throws Exception {
        userDao.findpassword(telephone,newpassword);
    }

    /**
     * 登陆管理后台
     * @param uname
     * @param upassword
     * @throws Exception
     */
    @Override
    public UserIdAndName adminLogin(String uname, String upassword) throws Exception {
        return userDao.adminLogin(uname, upassword);

    }
}
