package com.domain;

/**
 * @autor goh_liu
 * @date 2019/12/27 - 15:29
 */
public class UserIdAndName {
    private String uid;
    private String uname;
    private int ugrade;

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

    public int getUgrade() {
        return ugrade;
    }

    public void setUgrade(int ugrade) {
        this.ugrade = ugrade;
    }

    @Override
    public String toString() {
        return "UserIdAndName{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                '}';
    }

    public UserIdAndName() {
    }

    public UserIdAndName(String uid, String uname) {
        this.uid = uid;
        this.uname = uname;
    }

    public UserIdAndName(String uid, String uname, int ugrade) {
        this.uid = uid;
        this.uname = uname;
        this.ugrade = ugrade;
    }
}
