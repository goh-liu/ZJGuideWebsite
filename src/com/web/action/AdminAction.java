package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.opensymphony.xwork2.ActionSupport;
import com.service.AdminService;
import com.service.serviceImp.AdminServiceImp;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2020/1/2 - 10:13
 */
public class AdminAction extends ActionSupport {
    private AdminService adminService = new AdminServiceImp();

    public AdminService getAdminService() {
        return adminService;
    }

    public void setAdminService(AdminService adminService) {
        this.adminService = adminService;
    }


    public String adminUI(){
        return "adminUI";
    }
    /**
     * 查看匿名友人的各种数据的数量
     */
    public void showAnonCoun(){
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");
        int coun = 0;
        try {
            if ("1".equals(index) || "2".equals(index)){
                coun = adminService.showAonoDistricteCoun();
            }else if ("3".equals(index)){
                coun = adminService.showAnonCommentsCoun();
            }else if ("4".equals(index)){
//                coun = adminService.showNoteToreportCoun();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(coun);
    }

    /**
     * 查看学长寄语
     */
    public void showAnon(){
        String currentPage = ServletActionContext.getRequest().getParameter("currentPage");
        String pageSizeStr = ServletActionContext.getRequest().getParameter("pageSize");
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");

        //查询分页的页面条数
        int pageSize = Integer.parseInt(pageSizeStr);
        //查询的起始下标
        int startIndex = (Integer.parseInt(currentPage)-1 ) * pageSize;

        List noteList1 = null;
        try {
            switch (index){
                case "1":
                    noteList1 = adminService.showAnon(startIndex, pageSize);

                    break;
                case "2":
                    noteList1 = adminService.showAnonWithHot(startIndex, pageSize);
                    break;
                case "3":
                    noteList1 = adminService.showAnonComments(startIndex, pageSize);
                    break;
                case "4":
//                    noteList1 = adminService.showNoteToreport(startIndex, pageSize);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        returnJSONWithResp(noteList1);
    }

    /**
     * 查看学长寄语的各种数据的数量
     */
    public void showNoteCoun(){
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");
        int coun = 0;
        try {
            if ("1".equals(index) || "2".equals(index)){
                coun = adminService.showNoteDistricteCoun();
            }else if ("3".equals(index)){
                coun = adminService.showNoteObjectionCoun();
            }else if ("4".equals(index)){
                coun = adminService.showNoteToreportCoun();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(coun);
    }
    /**
     * 查看学长寄语
     */
    public void showNote(){
        String currentPage = ServletActionContext.getRequest().getParameter("currentPage");
        String pageSizeStr = ServletActionContext.getRequest().getParameter("pageSize");
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");

        //查询分页的页面条数
        int pageSize = Integer.parseInt(pageSizeStr);
        //查询的起始下标
        int startIndex = (Integer.parseInt(currentPage)-1 ) * pageSize;

        List noteList1 = null;
        try {
            switch (index){
                case "1":
                    noteList1 = adminService.showNote(startIndex, pageSize);
                    break;
                case "2":
                    noteList1 = adminService.showNoteWithHot(startIndex, pageSize);
                    break;
                case "3":
                    noteList1 = adminService.showNoteObjection(startIndex, pageSize);
                    break;
                case "4":
                    noteList1 = adminService.showNoteToreport(startIndex, pageSize);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        returnJSONWithResp(noteList1);
    }

    /**
     * 查看志同道合的各种数据的数量
     */
    public void showTeamCoun(){
        //index=1是查看队伍表的数量，=2是查看队员表的数量，=3是查看举报表的数量，=4是查看已满队伍的数量
        String index = ServletActionContext.getRequest().getParameter("index");
        int coun = 0;
        try {
            if ("1".equals(index)){
                coun = adminService.showTeamCoun("TeamDistrict");
            }else if ("2".equals(index)){
                coun = adminService.showTeamCoun("TeamMember");
            }else if ("3".equals(index)){
                coun = adminService.showTeamCoun("TeamToreport");
            }else if ("4".equals(index)){
                coun = adminService.showTeamCoun("TeamDistrictFull");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(coun);
    }

    /**
     * 查看志同道合(队伍)
     */
    public void showTeam(){
        String currentPage = ServletActionContext.getRequest().getParameter("currentPage");
        String pageSizeStr = ServletActionContext.getRequest().getParameter("pageSize");
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");

        //查询分页的页面条数
        int pageSize = Integer.parseInt(pageSizeStr);
        //查询的起始下标
        int startIndex = (Integer.parseInt(currentPage)-1 ) * pageSize;

        List teamList1 = null;
//        List<NoteObjection> noteList2 = null;
//        List<NoteToreport> noteList3 = null;
        try {
            switch (index){
                case "1":
                    teamList1 = adminService.showTeam(startIndex, pageSize);
                    break;
                case "2":
                    teamList1 = adminService.showTeamMenber(startIndex, pageSize);
                    break;
                case "3":
                    teamList1 = adminService.showTeamToreport(startIndex, pageSize);
                    break;
                case "4":
                    teamList1 = adminService.showTeamFull(startIndex, pageSize);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        returnJSONWithResp(teamList1);
    }

    /**
     * 查看用户管理的各种数据的量
     */
    public void showUserCoun(){
        //index=1是查看用户的数量，=2是查看管理员的数量
        String index = ServletActionContext.getRequest().getParameter("index");
        int coun = 0;
        try {
            if ("1".equals(index)){
                coun = adminService.showUserCoun();
            }else if ("2".equals(index)){
                coun = adminService.showIsAdminCoun();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(coun);
    }

    /**
     * 查看用户
     */
    public void showUser(){
        String currentPage = ServletActionContext.getRequest().getParameter("currentPage");
        String pageSizeStr = ServletActionContext.getRequest().getParameter("pageSize");
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");

        //查询分页的页面条数
        int pageSize = Integer.parseInt(pageSizeStr);
        //查询的起始下标
        int startIndex = (Integer.parseInt(currentPage)-1 ) * pageSize;

        List userList = null;
        try {
            switch (index){
                case "1":
                    userList = adminService.showUser(startIndex, pageSize);
                    break;
                case "2":
                    userList = adminService.showIsAdmin(startIndex, pageSize);
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        returnJSONWithResp(userList);
    }

    /**
     * 删除学长寄语
     */
    public void deleteNote(){
        //index=1是查看寄语，=2是查看热门寄语，=3是查看异议，=4是查看举报
        String index = ServletActionContext.getRequest().getParameter("index");
        String deleteId = ServletActionContext.getRequest().getParameter("deleteId");
        String deleted = "true";
        try {
            switch (index){
                case "1":
                case "2":
                    adminService.deleteNote(deleteId);
                    break;
                case "3":
                    adminService.deleteNoteObjection(deleteId);
                    break;
                case "4":
                    adminService.deleteNoteToreport(deleteId);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            deleted = "false";
        }
        returnJSONWithResp(deleted);
    }

    /**
     * 删除志同道合
     */
    public void deleteTeam(){
        //index=1是查看队伍，=2是查看队员，=3是查看举报，=4是查看已满队伍
        String index = ServletActionContext.getRequest().getParameter("index");
        String deleteId = ServletActionContext.getRequest().getParameter("deleteId");
        String deleted = "true";
        try {
            switch (index){
                case "1":
                case "4":
                    adminService.deleteTeam(deleteId);
                    break;
                case "2":
                    adminService.deleteTeamMember(deleteId);
                    break;
                case "3":
                    adminService.deleteTeamToreport(deleteId);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            if ("isTeamCaptain".equals(eMessage)){
                deleted = "isTeamCaptain";
            }else {
                deleted = "false";
            }
        }
        returnJSONWithResp(deleted);
    }

    /**
     * 删除匿名友人
     */
    public void deleteAnon(){
        //index=1是查看说说，=2是查看热门，=3是查看评论
        String index = ServletActionContext.getRequest().getParameter("index");
        String deleteId = ServletActionContext.getRequest().getParameter("deleteId");
        String deleted = "true";
        try {
            switch (index){
                case "1":
                case "2":
                    adminService.deleteAnon(deleteId);
                    break;
                case "3":
                    adminService.deleteAnonComment(deleteId);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            if ("isTeamCaptain".equals(eMessage)){
                deleted = "isTeamCaptain";
            }else {
                deleted = "false";
            }
        }
        returnJSONWithResp(deleted);
    }

    /**
     * 禁用用户
     */
    public void deleteUser(){
        //index=1是普通用户，=2是管理用户
        String index = ServletActionContext.getRequest().getParameter("index");
        String deleteId = ServletActionContext.getRequest().getParameter("deleteId");
        String deleted = "true";
        try {
            switch (index){
                case "1":
                    adminService.deleteUser(deleteId);
                    break;
                case "2":
                    /*adminService.deleteAnonComment(deleteId);*/
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String eMessage = e.getMessage();
            if ("isTeamCaptain".equals(eMessage)){
                deleted = "isTeamCaptain";
            }else {
                deleted = "false";
            }
        }
        returnJSONWithResp(deleted);
    }




    /**
     * 将对象转为JSON字符串并返回给前端页面
     */
    public void returnJSONWithResp(Object obj){
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        //转为json格式字符串返回前端,后面参数去掉重复对象引用
        String myNoteListJSON = JSON.toJSONStringWithDateFormat(obj,"yyyy-MM-dd  HH:mm:ss",SerializerFeature.DisableCircularReferenceDetect);
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

}
