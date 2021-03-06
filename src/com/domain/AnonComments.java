package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/8/9 - 15:27
 */
public class AnonComments {
    private String counter;
    private String anonID;
    private String sourceUid;
    private String sourceUname;
    private String destUid;
    private String destUname;
    private String commentText;
    private Date commentTime;
    private int commentOrReply;
    private String isRead;

    public AnonComments() {
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

    public String getSourceUid() {
        return sourceUid;
    }

    public void setSourceUid(String sourceUid) {
        this.sourceUid = sourceUid;
    }

    public String getSourceUname() {
        return sourceUname;
    }

    public void setSourceUname(String sourceUname) {
        this.sourceUname = sourceUname;
    }

    public String getDestUid() {
        return destUid;
    }

    public void setDestUid(String destUid) {
        this.destUid = destUid;
    }

    public String getDestUname() {
        return destUname;
    }

    public void setDestUname(String destUname) {
        this.destUname = destUname;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentOrReply() {
        return commentOrReply;
    }

    public void setCommentOrReply(int commentOrReply) {
        this.commentOrReply = commentOrReply;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "AnonComments{" +
                "counter='" + counter + '\'' +
                ", anonID='" + anonID + '\'' +
                ", sourceUid='" + sourceUid + '\'' +
                ", sourceUname='" + sourceUname + '\'' +
                ", destUid='" + destUid + '\'' +
                ", destUname='" + destUname + '\'' +
                ", commentText='" + commentText + '\'' +
                ", commentTime=" + commentTime +
                ", commentOrReply=" + commentOrReply +
                ", isRead=" + isRead +
                '}';
    }
}
