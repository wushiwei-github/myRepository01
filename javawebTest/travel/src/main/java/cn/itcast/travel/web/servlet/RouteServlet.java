package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.MyFavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.MyFavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService=new FavoriteServiceImpl();
    private MyFavoriteService myFavoriteService=new MyFavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接受参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        //处理参数
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0 && "null".equals(cidStr)) {
            cid = Integer.parseInt(cidStr);
        }
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }


        //调用service查询PageBean对象

        PageBean<Route> routePageBean = routeService.pageQuery(cid, currentPage, pageSize, rname);
        /*ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),routePageBean);;*/
        writeValue(routePageBean, response);


    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = routeService.findOne(rid);
        writeValue(route,response);


    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");

        //获取用户登录信息
        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        if (user==null) {
            uid=0;
        }else {
            uid=user.getUid();
        }
        boolean flag = favoriteService.isFavorite(rid, uid);

        writeValue(flag,response);


    }
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String rid = request.getParameter("rid");

        //获取用户登录信息
        User user = (User) request.getSession().getAttribute("user");
        int uid=0;
        if (user==null) {
            return;
        }else {
            uid=user.getUid();
        }
       favoriteService.add(rid,uid);

    }
    //查询rid返回一个集合
    public void findMyFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //User user = (User) request.getSession().getAttribute("user");
        List<Route> myFavorite = myFavoriteService.findMyFavorite();
        //存进user对象
        writeValue(myFavorite,response);

    }

}
