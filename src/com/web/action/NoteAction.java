package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domain.*;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.service.NoteService;
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
 * @date 2019/12/21 - 10:08
 */
public class NoteAction extends ActionSupport implements SessionAware,ModelDriven<NoteDistrict>{
    //已经登录的用户
    private User loginuser;

    @Override
    public void setSession(Map<String, Object> sessionMap) {
        this.loginuser = (User)sessionMap.get("loginUser");
    }

    // 模型驱动使用的对象:自动获取并封装note的内容
    private NoteDistrict noteDistrict = new NoteDistrict();
    private NoteObjection noteObjection = new NoteObjection();
    private NoteToreport noteToreport = new NoteToreport();

    @Override
    public NoteDistrict getModel() {
        return noteDistrict;
    }

    public NoteObjection getNoteObjection() {
        return noteObjection;
    }

    public void setNoteObjection(NoteObjection noteObjection) {
        this.noteObjection = noteObjection;
    }

    public NoteToreport getNoteToreport() {
        return noteToreport;
    }

    public void setNoteToreport(NoteToreport noteToreport) {
        this.noteToreport = noteToreport;
    }

    // 注入service
    private NoteService noteService;

    public void setNoteService(NoteService noteService) {
        this.noteService = noteService;
    }

    /**
     * 点击“学长寄语”跳转到学长寄语界面
     * @return
     */
    public String noteUINew(){
        String uid = null;
        try {
            if(null != loginuser){
                uid = loginuser.getUid();
            }else {
                uid = UUIDUtils.getId();
            }
            PageModel pageModel = noteService.showNew(1,uid);
            ServletActionContext.getRequest().getSession().setAttribute("page",pageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "noteUI_SUCCESS";
    }


    /**
     * 发表学长寄语,寄语的内容会自动封装到noteDistrict对象中
     * @return
     */
    public String published() {
        //生成寄语的noteId,hibernate会自动生成
        //noteDistrict.setNoteId(UUIDUtils.getId());
        //获取当前登录用户的UID
        noteDistrict.setUid(loginuser.getUid());
        //生成该寄语的发表时间
        noteDistrict.setPublishedTime(new Date());
        //设置有用数为0
        noteDistrict.setUsefulCoun(0);
        //设置提出异议数为0
        noteDistrict.setObjectionCoun(0);
        //设置举报人数为0
        noteDistrict.setToreportCoun(0);
        //设置寄语的状态为1--有效
        noteDistrict.setStatus(1);
        try {
            //调用service层
            noteService.published(noteDistrict);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //显示已经发表成功
        ServletActionContext.getRequest().getSession().setAttribute("popupMessage","发表成功！");
        return "published_SUCCESS";
    }

    /**
     * 查看单个寄语
     * @return
     */
    public void showOne() {
        NoteDistrict oneNoteDistrict =null;
        try {
            oneNoteDistrict = noteService.showOne(noteDistrict.getNoteId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        returnJSONWithResp(oneNoteDistrict);
    }

    /**
     * 查看最新的寄语
     * @return
     */
    public String showNew() {
        int curNum = 1;
        //获取当前页
        Map<String, Object> map = ActionContext.getContext().getParameters();
        for (String num : map.keySet()) {
            if ("num".equals(num)){
                String[] strings = (String[]) map.get(num);
                curNum = Integer.parseInt(strings[0]);
            }
        }
        try {
            PageModel pageModel = noteService.showNew(curNum,loginuser.getUid());
            ServletActionContext.getRequest().getSession().setAttribute("page",pageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "noteUI_SUCCESS";
    }
    /**
     * 查看热门的寄语
     * @return
     */
    public String showHot() {
        int curNum = 1;
        Map<String, Object> map = ActionContext.getContext().getParameters();
        for (String num : map.keySet()) {
            if ("num".equals(num)){
                String[] strings = (String[]) map.get(num);
                curNum = Integer.parseInt(strings[0]);
            }
        }
        try {
            PageModel pageModel = noteService.showHot(curNum,loginuser.getUid());
            ServletActionContext.getRequest().getSession().setAttribute("page",pageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "noteUI_SUCCESS";
    }

    /**
     * 用户点击“有用”按钮
     * @return
     */
    public String clickUseful() {
        NoteUseful noteUseful = new NoteUseful();
        //获取当前寄语的noteId和是否已经点击“有用”
        String noteId = ServletActionContext.getRequest().getParameter("noteId");
        String usefuled = ServletActionContext.getRequest().getParameter("usefuled");
        //获取当前登录用户的UID
        String uid = loginuser.getUid();
        //获取noteId和uid的md5码
        String usefulId = MD5Utils.md5(noteId + uid);
        noteUseful.setUsefulId(usefulId);
        noteUseful.setNoteId(noteId);
        noteUseful.setUid(uid);
        noteUseful.setIsRead("false");
        try {
            noteService.clickUseful(noteUseful,usefuled);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    /**
     * 用户点击“提出异议”按钮
     * @return
     */
    public String clickObjection(){
        //获取当前登录用户的UID
        String uid = loginuser.getUid();
        //获取noteId和uid的md5码
        String objectionId = MD5Utils.md5(noteObjection.getNoteId() + uid);
        noteObjection.setObjectionId(objectionId);
        noteObjection.setUid(uid);
        noteObjection.setObjectionTime(new Date());
        noteObjection.setIsRead("false");

        try {
            noteService.clickObjection(noteObjection);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServletActionContext.getRequest().getSession().setAttribute("popupMessage","提出异议成功！学长将能看到您的意见。");
        return "click_SUCCESS";
    }

    /**
     * 用户点击“举报”按钮
     * @return
     */
    public String clickToreport(){
        //获取当前登录用户的UID
        String uid = loginuser.getUid();
        //获取noteId和uid的md5码
        String toreportId = MD5Utils.md5(noteToreport.getNoteId() + uid);
        noteToreport.setToreportId(toreportId);
        noteToreport.setUid(uid);
        noteToreport.setToreportTime(new Date());
        noteToreport.setIsRead("false");

        try {
            noteService.clickToreport(noteToreport);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServletActionContext.getRequest().getSession().setAttribute("popupMessage","举报成功！感谢您的努力");
        return "click_SUCCESS";
    }

    /**
     * 显示登陆用户发表的寄语
     * @return
     */
    public void showMyNote(){
        List<NoteDistrict> myNoteList = null;
        try {
            myNoteList = noteService.showMyNote(loginuser.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将myNoteList转为JSON字符串并返回给前端页面
        returnJSONWithResp(myNoteList);
    }

    /**
     * 删除寄语
     * @return
     */
    public String deleteNote(){
        try {
            noteService.deleteNote(noteDistrict.getNoteId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServletActionContext.getRequest().getSession().setAttribute("popupMessage","删除成功");
        return "delete_SUCCESS";
    }

    /**
     * 显示我的消息
     */
    public void showMyMessages(){
        String uid = loginuser.getUid();
        Map myMessageMap = null;
        try {
            myMessageMap = noteService.showMyMessages(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(myMessageMap);
    }

    /**
     * 修改寄语
     * @return
     */
    public String alterNote(){
        try {
            noteService.alterNote(noteDistrict);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServletActionContext.getRequest().getSession().setAttribute("popupMessage","修改成功");
        return "alter_SUCCESS";
    }

    /**
     * 更改为已读
     * @return
     */
    public String changeIsRead(){
        String databaseTable = ServletActionContext.getRequest().getParameter("databaseTable");
        String sourceUid = ServletActionContext.getRequest().getParameter("uid");
        System.out.println(databaseTable);
        String tableKey = MD5Utils.md5(noteDistrict.getNoteId() + sourceUid);
        try {
            noteService.changeIsRead(tableKey,databaseTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
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