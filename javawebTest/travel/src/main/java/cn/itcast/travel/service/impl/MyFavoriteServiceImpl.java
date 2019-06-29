package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.MyFavoriteDao;
import cn.itcast.travel.dao.impl.MyFavoriteDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.MyFavoriteService;

import java.util.List;

public class MyFavoriteServiceImpl implements MyFavoriteService {
    private MyFavoriteDao dao=new MyFavoriteDaoImpl();
   //查询uid，返回一个集合
    @Override
    public List<Route> findMyFavorite() {

        return dao.findMyFavorite();
    }
}
