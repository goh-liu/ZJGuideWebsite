package com.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @autor goh_liu
 * @date 2019/7/31 - 8:32
 */
public class User {
    private String uid;
    private String uname;
    private String upassword;
    private String sex;
    private String telephone;
    private String telephoneCode;
    //学生所在学校
    private String school;
    //学生所在学院
    private String secondarySchool;
    //学生年级
    private int ugrade;
    //学生班级
    private String uclass;
    //账号状态
    private int status;

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", upassword='" + upassword + '\'' +
                ", sex='" + sex + '\'' +
                ", telephone='" + telephone + '\'' +
                ", telephoneCode='" + telephoneCode + '\'' +
                ", school='" + school + '\'' +
                ", secondarySchool='" + secondarySchool + '\'' +
                ", ugrade=" + ugrade +
                ", uclass='" + uclass + '\'' +
                ", status=" + status +
                '}';
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephoneCode() {
        return telephoneCode;
    }

    public void setTelephoneCode(String telephoneCode) {
        this.telephoneCode = telephoneCode;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSecondarySchool() {
        return secondarySchool;
    }

    public void setSecondarySchool(String secondarySchool) {
        this.secondarySchool = secondarySchool;
    }

    public int getUgrade() {
        return ugrade;
    }

    public void setUgrade(int ugrade) {
        this.ugrade = ugrade;
    }

    public String getUclass() {
        return uclass;
    }

    public void setUclass(String uclass) {
        this.uclass = uclass;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public User(String uid, String uname, String sex, int ugrade) {
        this.uid = uid;
        this.uname = uname;
        this.sex = sex;
        this.ugrade = ugrade;
    }

    public User(String uid, String uname, String upassword, String sex, String telephone, String school, String secondarySchool, int ugrade, String uclass, int status) {
        this.uid = uid;
        this.uname = uname;
        this.upassword = upassword;
        this.sex = sex;
        this.telephone = telephone;
        this.school = school;
        this.secondarySchool = secondarySchool;
        this.ugrade = ugrade;
        this.uclass = uclass;
        this.status = status;
    }

    public User() {
    }
}
