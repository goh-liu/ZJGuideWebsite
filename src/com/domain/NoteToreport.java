package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/12/26 - 21:45
 */
public class NoteToreport {
    private String toreportId;
    private String noteId;
    private String uid;
    private String toreportText;
    private Date toreportTime;
    private String isRead;

    public String getToreportId() {
        return toreportId;
    }

    public void setToreportId(String toreportId) {
        this.toreportId = toreportId;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
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

    @Override
    public String toString() {
        return "NoteToreport{" +
                "toreportId='" + toreportId + '\'' +
                ", noteId='" + noteId + '\'' +
                ", uid='" + uid + '\'' +
                ", toreportText='" + toreportText + '\'' +
                ", toreportTime=" + toreportTime +
                ", isRead='" + isRead + '\'' +
                '}';
    }
}
