package com.web.servlet;

import com.domain.*;
import com.service.AnonService;
import com.service.serviceImp.AnonServiceImp;
import com.utils.JsonDateValueProcessor;
import com.utils.UUIDUtils;
import com.utils.UploadUtils;
import com.web.base.BaseServlet;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @autor goh_liu
 * @date 2019/8/4 - 9:00
 */
public class AnonServlet extends BaseServlet {
    //跳转到匿名友人板块
    public String anonUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnonService anonService = new AnonServiceImp();
        req.getSession().removeAttribute("oneAnonMap");
        PageModel pageModel = anonService.showAnonWithPage(1);
        req.getSession().setAttribute("page",pageModel);
        return "jsp/anonymityFriend.jsp";
    }

    //用户点击发表按钮
    public String anonWrite(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnonDistrict anonDistrict = null;
        AnonPrice anonPrice = null;
        ArrayList<AnonPrice> anonPrices = new ArrayList<>();
        User user = (User) req.getSession().getAttribute("loginUser");
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        List<FileItem> list = upload.parseRequest(req);
        for (FileItem item : list) {
            if(item.isFormField()){
                //5_如果当前的FileItem对象是普通项,将数据封装在anonDistrict
                if (null!=item.getString("utf-8")||"".equals(item.getString("utf-8"))){
                    anonDistrict = new AnonDistrict();
                    anonDistrict.setAnonID(UUIDUtils.getId());
                    anonDistrict.setUid(user.getUid());
                    anonDistrict.setAnonContent(item.getString("utf-8"));
                    anonDistrict.setDeliveryTime(new Date());
                    anonDistrict.setStatus(1);
                }
            }else{
                //获取到原始的文件名称,若没有，直接跳出本次循环
                String oldFileName=item.getName();
                if(null==oldFileName||"".equals(oldFileName)){
                    break;
                }
                //获取到要保存文件的名称
                String newFileName=UploadUtils.getUUIDName(oldFileName);
                //通过FileItem获取到输入流对象,通过输入流可以获取到图片二进制数据
                InputStream is=item.getInputStream();
                //获取到当前项目下anonImage下的真实路径
                //String realPath=getServletContext().getRealPath("F:/anonImage");
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
                anonPrice = new AnonPrice();
                anonPrice.setAnonID(anonDistrict.getAnonID());
                anonPrice.setPriceUrl(priceUrl);
                anonPrice.setStatus(1);
                anonPrices.add(anonPrice);
            }
        }
        AnonService anonService = new AnonServiceImp();
        anonService.anonWrite(anonDistrict, anonPrices);
        resp.setContentType("text/html; charset=UTF-8"); //转码
        resp.getWriter().print("<script>alert('发表成功！点击《已发表》可查看');" +
                "window.location.href='/ZJGuideWebsite_war_exploded/AnonServlet?method=anonUI';</script>");
        resp.getWriter().flush();
        return null;
    }


    //分页显示用户发表的匿名内容
    public String showAnonWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        int curNum=Integer.parseInt(req.getParameter("num"));
        AnonService anonService = new AnonServiceImp();
        PageModel pageModel = anonService.showAnonWithPage(curNum);
        req.getSession().setAttribute("page",pageModel);
        return "jsp/anonymityFriend.jsp";
    }

    //写入评论
    public String anonComment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //评论内容
        String anonComment = req.getParameter("anonComment");
        //匿名说说序号
        String counter = req.getParameter("counter");

        //评论目标人物
        String comment_destUid = req.getParameter("comment_destUid");

        String comment_destUname = req.getParameter("comment_destUname");
        //commentOrReply:1-->评论,2-->回复
        String commentOrReply = req.getParameter("commentOrReply");
        //登陆者
        User user = (User) req.getSession().getAttribute("loginUser");
        AnonService anonService = new AnonServiceImp();
        anonService.anonComment(user,comment_destUid,comment_destUname,commentOrReply, counter, anonComment);
        return "jsp/anonymityFriend.jsp";
    }

    //点赞功能
    public String anonLike(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取点赞的匿名说说的序号
        String counter = req.getParameter("counter");
        //调用service层
        AnonService anonService = new AnonServiceImp();
        //更新anon_district表的likeCoun列
        int likeCoun = anonService.anonLike(counter);
        //将点赞的人和点赞的匿名说说保存在anon_like表中
        User user = (User) req.getSession().getAttribute("loginUser");
        anonService.recordLike(counter,user.getUid());
        //将该条匿名说说的点赞人数返回
        String likeCounJSON = "{\"likeCoun\":\""+likeCoun+"\"}";
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(likeCounJSON);
        return null;
    }

    //取消点赞功能
    public String cancelAnonLike(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String counter = req.getParameter("counter");
        AnonService anonService = new AnonServiceImp();
        //更新anon_district表的likeCoun列
        int likeCoun = anonService.cancelAnonLike(counter);
        //删除在anon_like表中点赞信息
        User user = (User) req.getSession().getAttribute("loginUser");
        anonService.delRecordLike(counter,user.getUid());
        //将该条匿名说说的点赞人数返回
        String likeCounJSON = "{\"likeCoun\":\""+likeCoun+"\"}";
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(likeCounJSON);
        return null;
    }

    //删除匿名说说
    public String anonDel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String counter = req.getParameter("counter");
        AnonService anonService = new AnonServiceImp();
        anonService.anonDel(counter);
        return null;
    }

    //显示用户消息
    public String showMessages(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        AnonService anonService = new AnonServiceImp();
        List<AnonComments>  userMessages = anonService.showMessages(user.getUid());
        //将Date格式转化为yyyy-MM-dd HH:mm:ss格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        String userAnonListJson = JSONArray.fromObject(userMessages,jsonConfig).toString();
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(userAnonListJson);

        return null;
    }
    //显示用户发表过的匿名说说
    public String showUserAnon(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        AnonService anonService = new AnonServiceImp();
        List userAnonList = anonService.showUserAnon(user.getUid());
        //将Date格式转化为yyyy-MM-dd HH:mm:ss格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        String userAnonListJson = JSONArray.fromObject(userAnonList,jsonConfig).toString();
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(userAnonListJson);

        return null;
    }
    //显示用户的评论
    public String showUserComment(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = (User) req.getSession().getAttribute("loginUser");
        AnonService anonService = new AnonServiceImp();
        List<AnonComments> userCommentList = anonService.showUserComment(user.getUid());
        //将Date格式转化为yyyy-MM-dd HH:mm:ss格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        String userAnonListJson = JSONArray.fromObject(userCommentList,jsonConfig).toString();
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(userAnonListJson);

        return null;
    }

    //用户点击左边区域的已发表中的其中一项，显示该项的详细信息
    public String showAnonDetails(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String counter = req.getParameter("counter");
        AnonService anonService = new AnonServiceImp();
        HashMap hashMap = anonService.showAnonDetails(counter);
        //将Date格式转化为yyyy-MM-dd HH:mm:ss格式
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class , new JsonDateValueProcessor());
        String userAnonListJson = JSONArray.fromObject(hashMap.values(),jsonConfig).toString();
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(userAnonListJson);
        resp.getWriter().flush();
        return null;
    }

    //查看消息后将将消息设置为已读
    public String readMessage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String anonID = req.getParameter("anonID");
        String destUid = req.getParameter("destUid");
        AnonService anonService = new AnonServiceImp();
        anonService.readMessage(anonID,destUid);
        return null;
    }

}


