package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domain.*;
import com.mchange.lang.ShortUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.service.AnonService;
import com.utils.UUIDUtils;
import com.utils.UploadUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.aspectj.util.FileUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2020/2/3 - 10:36
 */

public class AnonAction extends ActionSupport {
    /**
     * 注入anonService
     */
    private AnonService anonService;

    public void setAnonService(AnonService anonService) {
        this.anonService = anonService;
    }

    /**
     *定义一个File ,变量名要与jsp中的input标签的name一致
     */
    private List<File> uploadFile;
    /**
     *上传文件的mimeType类型
     */
    private List<String> uploadFileContentType;
    /**
     *上传文件的名称
     */
    private List<String> uploadFileFileName;

    public List<File> getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(List<File> uploadFile) {
        this.uploadFile = uploadFile;
    }

    public List<String> getUploadFileContentType() {
        return uploadFileContentType;
    }

    public void setUploadFileContentType(List<String> uploadFileContentType) {
        this.uploadFileContentType = uploadFileContentType;
    }

    public List<String> getUploadFileFileName() {
        return uploadFileFileName;
    }

    public void setUploadFileFileName(List<String> uploadFileFileName) {
        this.uploadFileFileName = uploadFileFileName;
    }

    /**
     *   跳转到匿名友人板块
     */
    public String anonUI() {
        ServletActionContext.getRequest().getSession().removeAttribute("oneAnonMap");
        PageModel pageModel = null;
        try {
            pageModel = anonService.showAnonWithPage(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ServletActionContext.getRequest().getSession().setAttribute("page",pageModel);
        return "anonUI";
    }

    /**
     * 用户点击发表按钮
     */
    public String anonWrite() {
        HttpServletRequest req = ServletActionContext.getRequest();
        AnonDistrict anonDistrict = new AnonDistrict();
        ArrayList<AnonPrice> anonPrices = new ArrayList<>();

        User user = (User) req.getSession().getAttribute("loginUser");
        String anonContent = req.getParameter("anonContent");
        //封装anonDistrict的其他信息
        anonDistrict.setAnonID(UUIDUtils.getId());
        anonDistrict.setUid(user.getUid());
        anonDistrict.setAnonContent(anonContent);
        anonDistrict.setDeliveryTime(new Date());
        anonDistrict.setLikeCoun(0);
        anonDistrict.setStatus(1);
        //封装anonPrice并将图片保存在硬盘中
        try {
            int i = 0;
            for (File uploadFile : uploadFile) {
                //获取到原始的文件名称,若没有，直接跳出本次循环
                String oldFileName = uploadFileFileName.get(i);
                i++;
                System.out.println("--------------");
                System.out.println("oldFileName"+oldFileName);
                if(null == oldFileName||"".equals(oldFileName)){
                    break;
                }
                //获取到要保存文件的名称
                String newFileName= UploadUtils.getUUIDName(oldFileName);
                //通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
                InputStream is = new FileInputStream(uploadFile);
                //获取到当前项目下anonImage下的真实路径
                String realPath="F:/Image";
                String dir= UploadUtils.getDir(newFileName);
                String path=realPath+dir;
                //内存中声明一个目录
                File newDir=new File(path);
                if(!newDir.exists()){
                   newDir.mkdirs();
                }
                //在服务端创建一个空文件(后缀必须和上传到服务端的文件名后缀一致)
                File finalFile=new File(newDir,newFileName);
                if(!finalFile.exists()){
                    finalFile.createNewFile();
                }
                //建立和空文件对应的输出流
                OutputStream os=new FileOutputStream(finalFile);
                //将输入流中的数据刷到输出流中
                IOUtils.copy(is, os);
                //释放资源
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(os);
                //图片的路径
                String priceUrl = dir+"/"+newFileName;
                //将数据封装在price对象中
                AnonPrice anonPrice = new AnonPrice();
                anonPrice.setAnonID(anonDistrict.getAnonID());
                anonPrice.setPriceUrl(priceUrl);
                anonPrice.setStatus(1);
                anonPrices.add(anonPrice);
            }
            anonService.anonWrite(anonDistrict, anonPrices);
            req.setAttribute("popupMessage","发表成功！点击《已发表》可查看");
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("popupMessage","发表失败！！！");
        }
        return "anonUIAction";
    }


    /**
     * 分页显示用户发表的匿名内容
     */
    public String showAnonWithPage() {
        HttpServletRequest req = ServletActionContext.getRequest();
        int curNum=Integer.parseInt(req.getParameter("num"));
        PageModel pageModel = null;
        try {
            pageModel = anonService.showAnonWithPage(curNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.getSession().setAttribute("page",pageModel);
        return "anonUI";
    }

    /**
     * 写入评论
     */
    public String anonComment() {
        HttpServletRequest req = ServletActionContext.getRequest();
        //评论内容
        String anonCommentText = req.getParameter("anonComment");
        //匿名说说序号
        String anonID = req.getParameter("anonID");
        //评论目标人物
        String comment_destUid = req.getParameter("comment_destUid");
        String comment_destUname = req.getParameter("comment_destUname");
        //commentOrReply:1-->评论,2-->回复
        String commentOrReply = req.getParameter("commentOrReply");
        //登陆者
        User user = (User) req.getSession().getAttribute("loginUser");
        //封装anonComments对象
        AnonComments anonComments = new AnonComments();
        anonComments.setAnonID(anonID);
        anonComments.setSourceUid(user.getUid());
        anonComments.setSourceUname(user.getUname());
        anonComments.setDestUid(comment_destUid);
        anonComments.setDestUname(comment_destUname);
        anonComments.setCommentText(anonCommentText);
        anonComments.setCommentTime(new Date());
        anonComments.setCommentOrReply(Integer.parseInt(commentOrReply));
        anonComments.setIsRead("false");
        //判断是否自己评论或者回复自己
        if(anonComments.getSourceUid().equals(anonComments.getDestUid())){
            //匿名者回复自己
            anonComments.setSourceUname(anonComments.getDestUname());
        }
        try {
            anonService.anonComment(anonComments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "anonUIAction";
    }

    /**
     * 点赞功能
     */
    public void anonLike() {
        HttpServletRequest req = ServletActionContext.getRequest();
        //获取点赞的匿名说说的序号
        String anonID = req.getParameter("anonID");
        int likeCoun = 0;
        try {
            //将点赞的人和点赞的匿名说说保存在anon_like表中并更新anon_district表的likeCoun列
            User user = (User) req.getSession().getAttribute("loginUser");
            likeCoun = anonService.anonLike(anonID,user.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将该条匿名说说的点赞人数返回
        returnJSONWithResp(likeCoun);
    }

    /**
     * 取消点赞功能
     */
    public void cancelAnonLike() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String anonID = req.getParameter("anonID");
        //更新anon_district表的likeCoun列
        int likeCoun = 0;
        try {
            User user = (User) req.getSession().getAttribute("loginUser");
            //删除在anon_like表中点赞信息,并将likeCoun-1
            likeCoun = anonService.cancelAnonLike(anonID,user.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //将该条匿名说说的点赞人数返回
        returnJSONWithResp(likeCoun);
    }

    /**
     * 删除匿名说说
     */
    public String anonDel() {
        String anonID = ServletActionContext.getRequest().getParameter("anonID");
        try {
            anonService.anonDel(anonID);
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","删除成功！！");
        } catch (Exception e) {
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","删除失败！！");
            e.printStackTrace();
        }
        return "anonUIAction";
    }

    /**
     * 显示用户消息
     */
    public void showMessages() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        List<AnonComments>  userMessages = null;
        try {
            userMessages = anonService.showMessages(user.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(userMessages);
    }

    /**
     * 显示用户发表过的匿名说说
     */
    public void showUserAnon() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        List userAnonList = null;
        try {
            userAnonList = anonService.showUserAnon(user.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(userAnonList);
    }

    /**
     * 显示用户的评论
     */
    public void showUserComment() {
        User user = (User) ServletActionContext.getRequest().getSession().getAttribute("loginUser");
        List<AnonComments> userCommentList = null;
        try {
            userCommentList = anonService.showUserComment(user.getUid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(userCommentList);
    }

    /**
     * 用户点击左边区域的已发表中的其中一项，显示该项的详细信息
     */
    public void showAnonDetails() {
        String anonID = ServletActionContext.getRequest().getParameter("anonID");
        HashMap hashMap = null;
        try {
            hashMap = anonService.showAnonDetails(anonID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        returnJSONWithResp(hashMap);
    }

    /**
     * 查看消息后将将消息设置为已读
     */
    public String readMessage() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String counter = req.getParameter("counter");
        System.out.println("counter======"+counter);
        try {
            anonService.readMessage(counter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "anonUI";
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
