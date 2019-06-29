package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    public int findTotalByCount(int cid,String rname);
    public List<Route>findByPage(int cid,int start,int pageSize,String rname);

     public Route fingOne(int rid);


}
