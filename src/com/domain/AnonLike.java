package com.domain;

/**
 * @autor goh_liu
 * @date 2019/8/11 - 22:20
 */
public class AnonLike {
    private String counter;
    private String anonID;
    private String likeUID;

    public AnonLike() {
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
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
                "counter='" + counter + '\'' +
                ", anonID='" + anonID + '\'' +
                ", likeUID='" + likeUID + '\'' +
                '}';
    }
}
