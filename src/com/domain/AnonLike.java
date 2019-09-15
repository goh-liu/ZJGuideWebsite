package com.domain;

/**
 * @autor goh_liu
 * @date 2019/8/11 - 22:20
 */
public class AnonLike {
    private String anonID;
    private String likeUID;

    public AnonLike() {
    }

    public String getAnonID() {
        return anonID;
    }

    public void setAnonID(String anonID) {
        this.anonID = anonID;
    }

    public String getLikeUID() {
        return likeUID;
    }

    public void setLikeUID(String likeUID) {
        this.likeUID = likeUID;
    }

    @Override
    public String toString() {
        return "AnonLike{" +
                "anonID='" + anonID + '\'' +
                ", likeUID='" + likeUID + '\'' +
                '}';
    }
}
