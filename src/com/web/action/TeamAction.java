package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domain.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.TeamService;
import com.utils.MD5Utils;
import com.utils.UUIDUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/12/28 - 11:10
 */
public class TeamAction extends ActionSupport implements SessionAware{

    //已经登录的用户
    private User loginuser;

    @Override
    public void setSession(Map<String, Object> sessionMap) {
        this.loginuser = (User)sessionMap.get("loginUser");
    }

    private TeamDistrict teamDistrict = new TeamDistrict();
    private TeamMember teamMember = new TeamMember();
    private TeamToreport teamToreport = new TeamToreport();

    //自动注入service
    private TeamService teamService;
    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }


    /**
     * 用户点击导航栏中的“志同道合”
     * @return
     */
    public String commonGoal(){
        PageModel pm = null;
        try {
            pm = teamService.showTeam(1, "study");
            ServletActionContext.getRequest().getSession().setAttribute("page",pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "TeamUI";
    }

    /**
     * 跳转到“志同道合”界面
     * @return
     */
    public String TeamUI(){
        return "TeamUI";
    }

    /**
     * 用户创建队伍
     * @return
     */
    public String createTeam(){
        //完善队伍的其他信息
        teamDistrict.setTeamId(UUIDUtils.getId());
        teamDistrict.setUid(loginuser.getUid());
        teamDistrict.setCurrPeopleNum(1);
        teamDistrict.setCreateTime(new Date());

        try {
            teamService.createTeam(teamDistrict);
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","创建队伍成功！！");
        } catch (Exception e) {
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","创建队伍失败，请联系开发人员！！");
            e.printStackTrace();
        }
        return "createTeam_SUCCESS";
    }

    /**
     * 按照类型返回队伍
     */
    public String showTeam(){
        String teamType = ServletActionContext.getRequest().getParameter("teamType");
        String curnum = ServletActionContext.getRequest().getParameter("num");
        try {
            PageModel pm = teamService.showTeam(Integer.parseInt(curnum), teamType);
            ServletActionContext.getRequest().getSession().setAttribute("page",pm);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "showTeam_SUCCESS";
    }

    /**
     * 用户点击“举报”按钮
     * @return
     */
    public String clickToreport(){
        String uid = loginuser.getUid();
        String toreportId = MD5Utils.md5(teamToreport.getTeamId() + uid);
        teamToreport.setToreportId(toreportId);
        teamToreport.setUid(uid);
        teamToreport.setToreportTime(new Date());
        teamToreport.setIsRead("false");
        try {
            teamService.clickToreport(teamToreport);
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","举报成功，感谢您的努力");
        } catch (Exception e) {
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","举报失败，请联系管理人员");
            e.printStackTrace();
        }
        return "TeamUI";
    }

    /**
     * 用户加入队伍
     * @return
     */
    public String joinTeam(){
        String uid = loginuser.getUid();
        String teamId = ServletActionContext.getRequest().getParameter("teamId");
        String teamCaptainUid = ServletActionContext.getRequest().getParameter("teamCaptainUid");
        String teamMemberId = MD5Utils.md5(teamId + uid);
        teamMember.setTeamMemberId(teamMemberId);
        teamMember.setTeamId(teamId);
        teamMember.setTeamCaptainUid(teamCaptainUid);
        teamMember.setTeamMemberUid(uid);
        teamMember.setConsentJoin("false");
        teamMember.setJoinTime(new Date());
        System.out.println(teamMember);
        try {
            teamService.saveTeamMember(teamMember);
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","申请加入中，请等待队长同意！！");
        } catch (Exception e) {
            e.printStackTrace();
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","您已经是该队伍的成员，请不要重新加入！");
        }
        return "TeamUI";
    }

    /**
     * 显示我的消息
     */
    public void showMyMessages(){
        Map myMessageMap = null;
        try {
            myMessageMap = teamService.showMyMessages(loginuser.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(myMessageMap);
    }

    /**
     * 根据teamId显示队伍信息
     */
    public void showTeamByTeamId(){
        TeamDistrict teamDistrict1 = null;
        String teamId = ServletActionContext.getRequest().getParameter("teamId");
        try {
            teamDistrict1 = teamService.showTeamByTeamId(teamId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(teamDistrict1);
    }

    /**
     * 用户审核队伍加入申请
     * @return
     */
    public String auditApply(){
        String audit = ServletActionContext.getRequest().getParameter("audit");
        String teamMemberId = ServletActionContext.getRequest().getParameter("teamMemberId");
        try {
            teamService.auditApply(teamMemberId,audit);
            String popupMessage = null;
            if("agree".equals(audit)){
                popupMessage = "同意加入！";
            }else {
                popupMessage = "不同意加入！！";
            }

            ServletActionContext.getRequest().getSession().setAttribute("popupMessage",popupMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "TeamUI";
    }

    /**
     * 用户查看加入队伍结果的消息
     */
    public void auditApplyTrue() {
        String audit = ServletActionContext.getRequest().getParameter("audit");
        String teamMemberId = ServletActionContext.getRequest().getParameter("teamMemberId");
        try {
            teamService.auditApply(teamMemberId, audit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户查看被举报的队伍的信息
     */
    public void toreportIsRead(){
        String toreportId = ServletActionContext.getRequest().getParameter("toreportId");
        try {
            teamService.toreportIsRead(toreportId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看我的队伍
     */
    public void showMyTeam(){
        String uid = loginuser.getUid();
        List<TeamDistrict> myTeamList = null;
        try {
            myTeamList = teamService.showMyTeam(uid);
            for (TeamDistrict district : myTeamList) {
                System.out.println(district);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(myTeamList);
    }


    /**
     * 将对象转为JSON字符串并返回给前端页面
     */
    public void returnJSONWithResp(Object obj){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        //转为json格式字符串返回前端,后面参数去掉重复对象引用
        String myNoteListJSON = JSON.toJSONString(obj,SerializerFeature.DisableCircularReferenceDetect);

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.println(myNoteListJSON);
        out.flush();
        out.close();
    }



    /* 提供get、set方法，自动封装前端提交的数据 */
    public TeamDistrict getTeamDistrict() {
        return teamDistrict;
    }

    public void setTeamDistrict(TeamDistrict teamDistrict) {
        this.teamDistrict = teamDistrict;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(TeamMember teamMember) {
        this.teamMember = teamMember;
    }

    public TeamToreport getTeamToreport() {
        return teamToreport;
    }

    public void setTeamToreport(TeamToreport teamToreport) {
        this.teamToreport = teamToreport;
    }
}
