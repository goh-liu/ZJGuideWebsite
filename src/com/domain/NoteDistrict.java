package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/12/21 - 9:51
 */
public class NoteDistrict {
    private String noteId;
    private String uid;
    private String note;
    private int usefulCoun;
    private int objectionCoun;
    private int toreportCoun;
    private Date publishedTime;
    private int status;
    private UserIdAndName userIdAndName;

    public NoteDistrict() {
    }

    public NoteDistrict(String noteId) {
        this.noteId = noteId;
    }

    public NoteDistrict(String noteId, String note, Date publishedTime) {
        this.noteId = noteId;
        this.note = note;
        this.publishedTime = publishedTime;
    }

    public NoteDistrict(String noteId, String uid, String note, int usefulCoun, int objectionCoun, int toreportCoun, Date publishedTime, int status, UserIdAndName userIdAndName) {
        this.noteId = noteId;
        this.uid = uid;
        this.note = note;
        this.usefulCoun = usefulCoun;
        this.objectionCoun = objectionCoun;
        this.toreportCoun = toreportCoun;
        this.publishedTime = publishedTime;
        this.status = status;
        this.userIdAndName = userIdAndName;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getUsefulCoun() {
        return usefulCoun;
    }

    public void setUsefulCoun(int usefulCoun) {
        this.usefulCoun = usefulCoun;
    }

    public int getObjectionCoun() {
        return objectionCoun;
    }

    public void setObjectionCoun(int objectionCoun) {
        this.objectionCoun = objectionCoun;
    }

    public int getToreportCoun() {
        return toreportCoun;
    }

    public void setToreportCoun(int toreportCoun) {
        this.toreportCoun = toreportCoun;
    }

    public Date getPublishedTime() {
        return publishedTime;
    }

    public void setPublishedTime(Date publishedTime) {
        this.publishedTime = publishedTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserIdAndName getUserIdAndName() {
        return userIdAndName;
    }

    public void setUserIdAndName(UserIdAndName userIdAndName) {
        this.userIdAndName = userIdAndName;
    }

    @Override
    public String toString() {
        return "NoteDistrict{" +
                "noteId='" + noteId + '\'' +
                ", uid='" + uid + '\'' +
                ", note='" + note + '\'' +
                ", usefulCoun=" + usefulCoun +
                ", objectionCoun=" + objectionCoun +
                ", toreportCoun=" + toreportCoun +
                ", publishedTime=" + publishedTime +
                ", status=" + status +
                ", userIdAndName=" + userIdAndName +
                '}';
    }
}
