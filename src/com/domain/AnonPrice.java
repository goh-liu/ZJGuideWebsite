package com.domain;

/**
 * @autor goh_liu
 * @date 2019/8/6 - 13:55
 */
public class AnonPrice {
    private String anonID;
    private String priceUrl;
    private int status;

    public AnonPrice() {
    }

    public AnonPrice(String anonID, String priceUrl, int status) {
        this.anonID = anonID;
        this.priceUrl = priceUrl;
        this.status = status;
    }

    public String getAnonID() {
        return anonID;
    }

    public void setAnonID(String anonID) {
        this.anonID = anonID;
    }

    public String getPriceUrl() {
        return priceUrl;
    }

    public void setPriceUrl(String priceUrl) {
        this.priceUrl = priceUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AnonPrice{" +
                "anonID='" + anonID + '\'' +
                ", priceUrl='" + priceUrl + '\'' +
                ", status=" + status +
                '}';
    }
}
