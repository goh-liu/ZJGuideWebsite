package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/12/29 - 10:39
 */
public class TeamToreport {
    private String toreportId;
    private String teamId;
    private String teamCaptainUid;
    private String uid;
    private String toreportText;
    private Date toreportTime;
    private String isRead;

    public TeamToreport() {
    }

    public TeamToreport(String toreportId, String teamId, String toreportText) {
        this.toreportId = toreportId;
        this.teamId = teamId;
        this.toreportText = toreportText;
    }


    public TeamToreport(String toreportId, String teamId, String uid, String toreportText, Date toreportTime, String isRead) {
        this.toreportId = toreportId;
        this.teamId = teamId;
        this.uid = uid;
        this.toreportText = toreportText;
        this.toreportTime = toreportTime;
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "TeamToreport{" +
                "toreportId='" + toreportId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", teamCaptainUid='" + teamCaptainUid + '\'' +
                ", uid='" + uid + '\'' +
                ", toreportText='" + toreportText + '\'' +
                ", toreportTime=" + toreportTime +
                ", isRead='" + isRead + '\'' +
                '}';
    }

    public String getToreportId() {
        return toreportId;
    }

    public void setToreportId(String toreportId) {
        this.toreportId = toreportId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamCaptainUid() {
        return teamCaptainUid;
    }

    public void setTeamCaptainUid(String teamCaptainUid) {
        this.teamCaptainUid = teamCaptainUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getToreportText() {
        return toreportText;
    }

    public void setToreportText(String toreportText) {
        this.toreportText = toreportText;
    }

    public Date getToreportTime() {
        return toreportTime;
    }

    public void setToreportTime(Date toreportTime) {
        this.toreportTime = toreportTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }
}
