package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.*;
import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    //声明doa对象
    private RouteDao dao = new RouteDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private RouteImgDao routeImgDao=new RouteImgDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    private MyFavoriteDao myFavoriteDao=new MyFavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码等参数

        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);


        //调用dao查询
        int totalByCount = dao.findTotalByCount(cid,rname);
        pb.setTotalCount(totalByCount);
        //设置当前页码显示集合
        int start = (currentPage - 1) * pageSize;
        List<Route> byPage = dao.findByPage(cid, start, pageSize,rname);
        pb.setList(byPage);

        //设置总页数
        int countPage = totalByCount % pageSize == 0 ? totalByCount / pageSize : (totalByCount / pageSize) + 1;
        pb.setTotalPage(countPage);
        return pb;
    }

    @Override
    public Route findOne(String rid) {
        //先查询route对象，根据rid
        Route route=dao.fingOne(Integer.parseInt(rid));

        //根据routeImgDao对象的id查询图片信息
        List<RouteImg> routeImgList = routeImgDao.findById(route.getRid());
        route.setRouteImgList(routeImgList);
        //根据sellerDao对象的sid查询卖家信息
        Seller sellerDaoById = sellerDao.findById(route.getSid());
        route.setSeller(sellerDaoById);
        //查询收藏次数
        int count = favoriteDao.fingCountByRid(route.getRid());
        route.setCount(count);


        return route;
    }

}
