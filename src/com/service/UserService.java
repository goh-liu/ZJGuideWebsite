package com.service;

import com.domain.User;
import com.domain.UserIdAndName;

/**
 * @autor goh_liu
 * @date 2019/7/30 - 23:35
 */
public interface UserService {

    User userLogin(String uname,String upassword) throws Exception;

    User autoLogin(String uid) throws Exception;

    void userRegister(User user) throws Exception ;

    String findUserbyNameOrTele(String uname,String telephone) throws Exception;

    void findpassword(String telephone,String newpassword) throws Exception;

    User adminLogin(String uname, String upassword) throws Exception;
}
