package com.domain;

/**
 * @autor goh_liu
 * @date 2019/12/29 - 23:07
 */
public class TeamIdAndName {
    private String teamId;
    private String teamName;

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "TeamIdAndName{" +
                "teamId='" + teamId + '\'' +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
