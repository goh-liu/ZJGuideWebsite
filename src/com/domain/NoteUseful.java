package com.domain;


/**
 * @autor goh_liu
 * @date 2019/12/25 - 16:59
 */
public class NoteUseful{
    private String usefulId;
    private String noteId;
    private String uid;
    private String isRead;


    public String getUsefulId() {
        return usefulId;
    }

    public void setUsefulId(String usefulId) {
        this.usefulId = usefulId;
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

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    @Override
    public String toString() {
        return "NoteUseful{" +
                "usefulId='" + usefulId + '\'' +
                ", noteId='" + noteId + '\'' +
                ", uid='" + uid + '\'' +
                ", isRead='" + isRead + '\'' +
                '}';
    }
}
