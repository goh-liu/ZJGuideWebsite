package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 13:59
 */
public class AnonDistrict {
    private int counter;
    private String anonID;
    private String uid;
    private String anonContent;
    private Date deliveryTime;
    private int likeCoun;
    private int status;

    public AnonDistrict() {
    }



    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getAnonID() {
        return anonID;
    }

    public void setAnonID(String anonID) {
        this.anonID = anonID;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAnonContent() {
        return anonContent;
    }

    public void setAnonContent(String anonContent) {
        this.anonContent = anonContent;
    }

    public Date getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getLikeCoun() {
        return likeCoun;
    }

    public void setLikeCoun(int likeCoun) {
        this.likeCoun = likeCoun;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnonDistrict{" +
                "counter=" + counter +
                ", anonID='" + anonID + '\'' +
                ", uid='" + uid + '\'' +
                ", anonContent='" + anonContent + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", likeCoun=" + likeCoun +
                ", status=" + status +
                '}';
    }
}
