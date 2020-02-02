package com.domain;

import java.util.Date;

/**
 * @autor goh_liu
 * @date 2019/12/28 - 10:21
 */
public class TeamMember {
    private String TeamMemberId;
    private String teamId;
    private String teamCaptainUid;
    private String teamMemberUid;
    private String consentJoin;
    private Date joinTime;
    private TeamIdAndName teamIdAndName;
    private UserIdAndName teamUser;

    public TeamMember() {
    }

    public TeamMember(String teamId) {
        this.teamId = teamId;
    }

    public TeamMember(String teamMemberId, String teamId, String teamCaptainUid, String teamMemberUid, String consentJoin, Date joinTime) {
        TeamMemberId = teamMemberId;
        this.teamId = teamId;
        this.teamCaptainUid = teamCaptainUid;
        this.teamMemberUid = teamMemberUid;
        this.consentJoin = consentJoin;
        this.joinTime = joinTime;
    }

    @Override
    public String toString() {
        return "TeamMember{" +
                "TeamMemberId='" + TeamMemberId + '\'' +
                ", teamId='" + teamId + '\'' +
                ", teamCaptainUid='" + teamCaptainUid + '\'' +
                ", teamMemberUid='" + teamMemberUid + '\'' +
                ", consentJoin='" + consentJoin + '\'' +
                ", joinTime=" + joinTime +
                ", teamIdAndName=" + teamIdAndName +
                ", teamUser=" + teamUser +
                '}';
    }

    public String getTeamMemberId() {
        return TeamMemberId;
    }

    public void setTeamMemberId(String teamMemberId) {
        TeamMemberId = teamMemberId;
    }

    public String getTeamId() {
        return teamId;
    }

    public String getTeamCaptainUid() {
        return teamCaptainUid;
    }

    public void setTeamCaptainUid(String teamCaptainUid) {
        this.teamCaptainUid = teamCaptainUid;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamMemberUid() {
        return teamMemberUid;
    }

    public void setTeamMemberUid(String teamMemberUid) {
        this.teamMemberUid = teamMemberUid;
    }

    public UserIdAndName getTeamUser() {
        return teamUser;
    }

    public void setTeamUser(UserIdAndName teamUser) {
        this.teamUser = teamUser;
    }

    public String getConsentJoin() {
        return consentJoin;
    }

    public void setConsentJoin(String consentJoin) {
        this.consentJoin = consentJoin;
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public TeamIdAndName getTeamIdAndName() {
        return teamIdAndName;
    }

    public void setTeamIdAndName(TeamIdAndName teamIdAndName) {
        this.teamIdAndName = teamIdAndName;
    }
}


