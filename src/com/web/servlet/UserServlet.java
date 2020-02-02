package com.web.servlet;

import com.domain.User;
import com.service.UserService;
import com.service.serviceImp.UserServiceImp;
import com.utils.CookieUtils;
import com.utils.MD5Utils;
import com.utils.UUIDUtils;
import com.web.base.BaseServlet;
import com.wf.captcha.utils.CaptchaUtil;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @autor goh_liu
 * @date 2019/7/30 - 12:43
 */
public class UserServlet extends BaseServlet {
    //跳转到首页页面
    public String indexUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return "/index.jsp";
    }
    //跳转到注册页面
    public String registerUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return "/jsp/register.jsp";
    }

    //跳转到登录页面
    public String loginUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        return "/jsp/login.jsp";
    }

    //点击注册按钮
    public String userRegister(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html; charset=UTF-8"); //转码
        //获取客户端提交过来的数据,封装成一个User对象
        Map<String, String[]> map = req.getParameterMap();
        User user = new User();
        BeanUtils.populate(user,map);
        //判断是否有同名或
        // 者同电话号码的，若有，不允许注册
        UserService userService = new UserServiceImp();
        String flag = userService.findUserbyNameOrTele(user.getUname(),user.getTelephone());
        //存在，不允许登陆
        if (!("noExist".equals(flag))){
            if ("existUname".equals(flag)){
                resp.getWriter().print("<script>alert('注册失败！用户名被使用,换个试试？');" +
                        "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=registerUI';</script>");
                resp.getWriter().flush();
            }else {
                resp.getWriter().print("<script>alert('注册失败！该手机号已注册，请登录！');" +
                        "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
                resp.getWriter().flush();
            }
            return null;
        }
        user.setUid(UUIDUtils.getId());
        //对密码进行加密处理
        user.setUpassword(MD5Utils.md5(user.getUpassword()));
        user.setStatus(1);//1代表用户状态是正常的
        try {
            //调用service层处理
            userService.userRegister(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().print("<script>alert('注册成功！登录后即可愉快玩耍啦');" +
                "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
        resp.getWriter().flush();
        return null;
    }



    //点击登录按钮
    public String userLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html; charset=UTF-8"); //转码
        //清空之前账号密码的错误提示
        req.getSession().setAttribute("msg", "");
        //获取客户端提交过来的数据
        String uname = req.getParameter("uname");
        String upassword = MD5Utils.md5(req.getParameter("upassword"));
        String cerifi = req.getParameter("verification-code");
        String autoLogin = req.getParameter("autoLogin");
        //判断验证码是否正确
        if (!CaptchaUtil.ver(cerifi,req)){
            resp.getWriter().print("<script>alert('用验证码错误！');" +
                    "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
            resp.getWriter().flush();
            return null;
        }
        UserService userService = new UserServiceImp();
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
            //转发到首页
            resp.sendRedirect("/ZJGuideWebsite_war_exploded/UserServlet?method=indexUI");
        } catch (Exception e) {
            //报错说明登录失败了
            resp.getWriter().print("<script>alert('用户名/密码错误，请重新登录！');" +
                    "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
            resp.getWriter().flush();
        }

        return null;
    }

    //退出登录
    public String exitLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //使session时效
        req.getSession().invalidate();
        resp.sendRedirect("/ZJGuideWebsite_war_exploded/UserServlet?method=indexUI");
        return null;
    }

    //自动登录
    public String autoLogin(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取要自动登陆用户的cookie及其内容
        Cookie autoLogin = CookieUtils.getCookieByName("autoLogin", req.getCookies());
        String uid = autoLogin.getValue();
        //调用service层处理
        UserService userService = new UserServiceImp();
        User user = userService.autoLogin(uid);
        //该cookie-->autoLogining：防止自动登陆的功能在一次会话中对次执行
        req.getSession().setAttribute("loginUser",user);
        Cookie autoLoginingCookie = new Cookie("autoLogining","1");
        autoLoginingCookie.setMaxAge(-1);
        resp.addCookie(autoLoginingCookie);
        //转发到首页
        resp.sendRedirect("/ZJGuideWebsite_war_exploded/UserServlet?method=indexUI");
        return null;
    }

    //找回密码
    public String findpassword(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html; charset=UTF-8"); //转码
        String telephone = req.getParameter("telephone");
        String newpassword =req.getParameter("newpassword");
        if (null == telephone||null == newpassword||"".equals(telephone)||"".equals(newpassword)){
            resp.getWriter().print("<script>alert('请输入手机号和密码！');" +
                    "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
        }else {
            UserService userService = new UserServiceImp();
            newpassword = MD5Utils.md5(newpassword);//对密码进行加密
            try {
                userService.findpassword(telephone,newpassword);
            } catch (Exception e) {
                resp.getWriter().print("<script>alert('手机号码不存在，请确认！');" +
                        "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
            }
            resp.getWriter().print("<script>alert('更新密码成功！');" +
                    "window.location.href='/ZJGuideWebsite_war_exploded/UserServlet?method=loginUI';</script>");
        }
        resp.getWriter().flush();
        return null;
    }


}
