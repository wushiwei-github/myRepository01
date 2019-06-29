package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//@WebServlet("/*")
public class BaseServlet extends HttpServlet {

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取方法请求路径
        String uri = request.getRequestURI();
        //获取方法名称
        String methodname = uri.substring(uri.lastIndexOf('/')+1);
        //获取方法对象
        try {
            Method method = this.getClass().getDeclaredMethod(methodname, HttpServletRequest.class, HttpServletResponse.class);
            //暴力反射调用
            //method.setAccessible(true);
            method.invoke(this,request,response);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        //反射调用

    }
    //将序列化抽取为一个方法,返回一个
    public void writeValue(Object obj ,HttpServletResponse response){
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        try {
            mapper.writeValue(response.getOutputStream(),obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String writeValueAsString(Object obj,HttpServletResponse response) {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper=new ObjectMapper();
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
