package com.web.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.domain.User;
import com.domain.UserIdAndName;
import com.opensymphony.xwork2.ActionSupport;
import com.service.UserService;
import com.utils.CookieUtils;
import com.utils.JeditUtils;
import com.utils.MD5Utils;
import com.utils.UUIDUtils;
import com.utils.verificationcode.Utility;
import com.utils.verificationcode.ZxHttpUtil;
import com.wf.captcha.utils.CaptchaUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


/**
 * @autor goh_liu
 * @date 2020/1/14 - 10:53
 */
@Controller("userAction")
public class UserAction extends ActionSupport {

    @Resource(name = "userService")
    private UserService userService;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String registerUI(){
        return "registerUI";
    }
    public String loginUI(){
        return "loginUI";
    }

    /**
     * 注册用户
     * @return
     */
    public String register(){
        //判断验证码是否正确，从redis中获取验证码
        String telephoneCode = JeditUtils.getJedis().get(user.getTelephone());
        if (user.getTelephoneCode() != telephoneCode){
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","验证码错误");
            return "registerUI";
        }else {
            //判断是否有同名或者同电话号码的，若有，不允许注册
            String flag = null;
            try {
                flag = userService.findUserbyNameOrTele(user.getUname(), user.getTelephone());
                //存在，不允许登陆
                if (!("noExist".equals(flag))) {
                    if ("existUname".equals(flag)) {
                        ServletActionContext.getRequest().getSession().setAttribute("popupMessage", "注册失败！用户名已被使用！！");
                        return "registerUI";
                    } else {
                        ServletActionContext.getRequest().getSession().setAttribute("popupMessage", "注册失败！该手机号已注册，请登录！");
                        return "loginUI";
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            //生成用户的ID
            user.setUid(UUIDUtils.getId());
            //对密码进行加密处理
            user.setUpassword(MD5Utils.md5(user.getUpassword()));
            //1代表用户状态是正常的
            user.setStatus(1);
            try {
                //调用service层处理
                userService.userRegister(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage", "注册成功！登录后即可愉快玩耍啦！");
            return "loginUI";
        }
    }

    /**
     * 发送验证码
     * @return
     */
    public void verificationCode(){
        // 您的短信账号
        String Account = "18312181651";
        // 您的短信账号密码
        String Password = "liu123456789,";
        // 是否需要状态报告
        String NeedStatus = "true";
        //生成手机验证码数值
        final double d = Math.random();
        final int verifiCode = (int)(d*1000000);
        //短信内容
        String message = "您的验证码是"+verifiCode+"，5分钟内有效，如非本人操作，请忽略本短信！";
        //要发送的手机号,多个手机号用,隔开
        String mobile = ServletActionContext.getRequest().getParameter("telephone");
        //时间戳
        String ts =  Utility.getNowDateStr();
        // Md5签名(账号+密码+时间戳)
        Password = Utility.getMD5(Account + Password + ts);
        HashMap params = new HashMap(10);
        //请求参数
        params.put("account",Account);
        params.put("pswd",Password);
        params.put("mobile",mobile);
        params.put("msg",message);
        params.put("ts",ts);
        params.put("needstatus",NeedStatus);
        System.out.println(params);
        //发送验证码
        String rep = ZxHttpUtil.sendPostMessage("http://139.196.108.241:8080/Api/HttpSendSMYzm.ashx", params, "UTF-8");
        System.out.println("验证码返回数据："+rep);
        String returnMsg = null;
        if (0 == (int)JSONObject.fromObject(rep).get("result")){
            //返回0，说明发送成功,将手机验证码存入redis中
            Jedis jedis = JeditUtils.getJedis();
            //将手机验证码存入redis中
            jedis.set(mobile,Integer.toString(verifiCode));
            //手机验证码的有效时间为5分钟
            jedis.expire(mobile,60*5);
            JeditUtils.closeJedis(jedis);
            returnMsg = "验证码发送成功！5分钟内有效";
        }else {
            //返回其他，说明发送失败
            System.out.println(JSONObject.fromObject(rep).get("result"));
            returnMsg = "验证码发送失败，错误代号："+JSONObject.fromObject(rep).get("result");
        }
        returnJSONWithResp(returnMsg);
    }

    /**
     * 用户登陆
     * @return
     */
    public String userLogin(){
        HttpServletResponse resp = ServletActionContext.getResponse();
        HttpServletRequest req = ServletActionContext.getRequest();
        resp.setContentType("text/html; charset=UTF-8");
        //清空之前账号密码的错误提示
        req.getSession().setAttribute("msg", "");
        //获取客户端提交过来的数据
        String uname = req.getParameter("uname");
        String upassword = MD5Utils.md5(req.getParameter("upassword"));
        String cerifi = req.getParameter("verification-code");
        String autoLogin = req.getParameter("autoLogin");
        //判断验证码是否正确
        if (!CaptchaUtil.ver(cerifi,req)){
            req.getSession().setAttribute("popupMessage","验证码错误！");
            return "loginUI";
        }
        try {
            //登录成功, service处理
            User user = userService.userLogin(uname,upassword);
            //记录登陆用户的信息，存放在loginUser中
            req.getSession().setAttribute("loginUser",user);
            //该cookie-->autoLogining：防止自动登陆的功能在一次会话中多次执行
            Cookie autoLoginingCookie = new Cookie("autoLogining","1");
            autoLoginingCookie.setMaxAge(-1);
            resp.addCookie(autoLoginingCookie);
            //若用户设置自动登陆,将用户信息保存在cookie中
            if ("on".equals(autoLogin)){
                Cookie autoLoginCookie = new Cookie("autoLogin",user.getUid());
                //自动登登录的有效期为三天
                autoLoginCookie.setMaxAge(60*60*24*3);
                resp.addCookie(autoLoginCookie);
            }
        } catch (Exception e) {
            //报错说明登录失败了
            req.getSession().setAttribute("popupMessage","用户名/密码错误，请重新登录！");
            return "loginUI";
        }
        return "anonUI";
    }

    /**
     * 退出登陆
     * @return
     * @throws Exception
     */
    public String exitLogin(){
        //使session时效
        HttpServletRequest req = ServletActionContext.getRequest();
        req.getSession().invalidate();
        return "loginUI";
    }

    /**
     * 自动登陆
     * @return
     * @throws Exception
     */
    public String autoLogin() {
        HttpServletRequest req = ServletActionContext.getRequest();
        //获取要自动登陆用户的cookie及其内容
        Cookie autoLogin = CookieUtils.getCookieByName("autoLogin", req.getCookies());
        String uid = autoLogin.getValue();
        //调用service层处理
        User user = null;
        try {
            user = userService.autoLogin(uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //该cookie-->autoLogining：防止自动登陆的功能在一次会话中对次执行
        req.getSession().setAttribute("loginUser",user);
        Cookie autoLoginingCookie = new Cookie("autoLogining","1");
        autoLoginingCookie.setMaxAge(-1);
        ServletActionContext.getResponse().addCookie(autoLoginingCookie);
        //转发到首页
        return "anonUI";
    }

    /**
     * 找回密码
     * @return
     * @throws Exception
     */
    public String findpassword() {
        HttpServletRequest req = ServletActionContext.getRequest();
        String telephone = req.getParameter("telephone");
        String newpassword =req.getParameter("newpassword");
        String telephoneCode =req.getParameter("telephoneCode");
        //判断验证码是否正确，从redis中获取验证码
        String telephoneCode1 = JeditUtils.getJedis().get(user.getTelephone());
        if (telephoneCode != telephoneCode1){
            ServletActionContext.getRequest().getSession().setAttribute("popupMessage","验证码错误");
            return "loginUI";
        }else {
            if (null == telephone||null == newpassword||"".equals(telephone)||"".equals(newpassword)){
                req.getSession().setAttribute("popupMessage","请输入手机号和密码！");
                return "loginUI";
            }else {
                //对密码进行加密
                newpassword = MD5Utils.md5(newpassword);
                try {
                    userService.findpassword(telephone,newpassword);
                } catch (Exception e) {
                    req.getSession().setAttribute("popupMessage","该手机号码不存在！");
                }
                req.getSession().setAttribute("popupMessage","更新密码成功！");
                return "loginUI";
            }
        }
    }

    /**
     * 登陆管理后台
     * @return
     */
    public String adminLogin(){
        HttpServletRequest req = ServletActionContext.getRequest();
        //获取客户端提交过来的数据
        String uname = req.getParameter("uname");
        String upassword = MD5Utils.md5(req.getParameter("upassword"));
        String cerifi = req.getParameter("verification-code");
        //判断验证码是否正确
        if (!CaptchaUtil.ver(cerifi,req)){
            req.getSession().setAttribute("popupMessage","验证码错误！");
            return "anonUI";
        }
        try {
            UserIdAndName userIdAndName = userService.adminLogin(uname, upassword);
            //记录登陆用户的信息，存放在loginUser中
            req.getSession().setAttribute("adminUser",userIdAndName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "adminUI";
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
