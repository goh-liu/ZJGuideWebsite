package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/12/26 - 15:09
 */
public class NoteObjection {
    private String objectionId;
    private String noteId;
    private String uid;
    private String objectionText;
    private Date objectionTime;
    private String isRead;

    public String getObjectionId() {
        return objectionId;
    }

    public void setObjectionId(String objectionId) {
        this.objectionId = objectionId;
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

    public String getObjectionText() {
        return objectionText;
    }

    public void setObjectionText(String objectionText) {
        this.objectionText = objectionText;
    }

    public Date getObjectionTime() {
        return objectionTime;
    }

    public void setObjectionTime(Date objectionTime) {
        this.objectionTime = objectionTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "NoteObjection{" +
                "objectionId='" + objectionId + '\'' +
                ", noteId='" + noteId + '\'' +
                ", uid='" + uid + '\'' +
                ", objectionText='" + objectionText + '\'' +
                ", objectionTime=" + objectionTime +
                ", isRead='" + isRead + '\'' +
                '}';
    }
}
