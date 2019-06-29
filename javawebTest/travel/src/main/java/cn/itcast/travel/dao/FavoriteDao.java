package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    public Favorite findUserUidAndCid(int rid,int uid);
    public int fingCountByRid(int rid);

    void add(int parseInt, int uid);
}
