package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明service
    private UserService service=new UserServiceImpl();
    //注册用户
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //校验验证码
        String check = request.getParameter("check");

        //获取session
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
            writeValueAsString(info,response);
            return;

        }

        //1，获取数据
        Map<String, String[]> map = request.getParameterMap();
        //2，封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //3.调用service方法
        //UserService service = new UserServiceImpl();
        boolean flag = service.regist(user);
        ResultInfo info = new ResultInfo();
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败,");
        }
        //4，序列化json对象。
        /*ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //5，设置响应。并且响应
        response.setContentType("application/json;charset=utf-8");
        //response.getOutputStream().write(json.getBytes());
        response.getWriter().write(json);*/
        writeValue(info,response);


    }
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证码获取
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            //验证码错误
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            /*ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);*/
            writeValueAsString(info,response);
            return;

        }


        //获取对象
        Map<String, String[]> parameterMap = request.getParameterMap();
        //封装user对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service查询
        //UserService service = new UserServiceImpl();
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //判断用户是否为空
        if (u == null) {
            //用户名或者密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        }
        //判断用户是否激活
        if (u != null && !("Y").equals(u.getStatus())) {
            info.setFlag(false);
            info.setErrorMsg("你尚未激活,请先激活");
        }
        //登录成功
        if (u != null && ("Y").equals(u.getStatus())) {
            info.setFlag(true);
            request.getSession().setAttribute("user",u);
        }
        //响应数据
        /*ObjectMapper mapper = new ObjectMapper();

        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);*/
        writeValue(info,response);


    }
    //查询单个对象
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //
        User user =(User)request.getSession().getAttribute("user");

        /*ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);*/
        writeValue(user,response);

    }
    //退出用户
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //重定向
        response.sendRedirect(request.getContextPath()+"/login.html");

    }
    //激活功能
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1,获取激活码
        String code = request.getParameter("code");
        //验证j激活码
        //调用service查询
        if (code!=null) {
           // UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            //设置一个msg
            String msg = null;
            if (flag) {
                //为true，激活成功
                msg = "激活成功<a href='/travel/login.html'>登录</a>";
            } else {
                msg = "激活失败，请联系管理员";
            }
            //响应，格式设置
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
           // writeValue(msg,response);

        }
    }
    //我的收藏


}
