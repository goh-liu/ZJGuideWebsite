package com.dao;


import com.domain.User;


/**
 * @autor goh_liu
 * @date 2019/7/30 - 23:38
 */
public interface UserDao {
   User userLogin(String uname, String upassword) throws Exception;

    User findUserByID(String uid) throws Exception;

    void userRegister(User user) throws Exception;

    String findUserbyNameOrTele(String uname,String telephone) throws Exception;

    void findpassword(String telephone,String newpassword) throws Exception;
}
