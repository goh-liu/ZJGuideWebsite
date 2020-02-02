package com.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @autor goh_liu
 * @date 2019/12/28 - 9:35
 */
public class TeamDistrict {
    private int counter;
    private String teamId;
    private String uid;
    private String teamName;
    private String teamType;
    private int teamPeopleNum;
    private int currPeopleNum;
    private String teamIntroduction;
    private String joinCondition;
    private Date createTime;

    private Set<TeamMember> teamMember = new HashSet<TeamMember>();

    public TeamDistrict() {
    }

    public TeamDistrict(int counter, String teamId, String teamName, String teamType) {
        this.counter = counter;
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamType = teamType;
    }

    public TeamDistrict(int counter, String teamId, String uid, String teamName, String teamType, int teamPeopleNum, int currPeopleNum, String teamIntroduction, String joinCondition, Date createTime) {
        this.counter = counter;
        this.teamId = teamId;
        this.uid = uid;
        this.teamName = teamName;
        this.teamType = teamType;
        this.teamPeopleNum = teamPeopleNum;
        this.currPeopleNum = currPeopleNum;
        this.teamIntroduction = teamIntroduction;
        this.joinCondition = joinCondition;
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TeamDistrict{" +
                "counter=" + counter +
                ", teamId='" + teamId + '\'' +
                ", uid='" + uid + '\'' +
                ", teamName='" + teamName + '\'' +
                ", teamType='" + teamType + '\'' +
                ", teamPeopleNum=" + teamPeopleNum +
                ", currPeopleNum=" + currPeopleNum +
                ", teamIntroduction='" + teamIntroduction + '\'' +
                ", joinCondition='" + joinCondition + '\'' +
                ", createTime=" + createTime +
                ", teamMember=" + teamMember +
                '}';
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamType() {
        return teamType;
    }

    public void setTeamType(String teamType) {
        this.teamType = teamType;
    }

    public int getTeamPeopleNum() {
        return teamPeopleNum;
    }

    public void setTeamPeopleNum(int teamPeopleNum) {
        this.teamPeopleNum = teamPeopleNum;
    }

    public int getCurrPeopleNum() {
        return currPeopleNum;
    }

    public void setCurrPeopleNum(int currPeopleNum) {
        this.currPeopleNum = currPeopleNum;
    }

    public String getTeamIntroduction() {
        return teamIntroduction;
    }

    public void setTeamIntroduction(String teamIntroduction) {
        this.teamIntroduction = teamIntroduction;
    }

    public String getJoinCondition() {
        return joinCondition;
    }

    public void setJoinCondition(String joinCondition) {
        this.joinCondition = joinCondition;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<TeamMember> getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(Set<TeamMember> teamMember) {
        this.teamMember = teamMember;
    }
}
