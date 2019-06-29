package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface MyFavoriteDao {

    //查询uid返回一个集合
    public List<Route>findMyFavorite();
}
