package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public boolean isFavorite(String rid, int uid) {
        Favorite uidAndCid = favoriteDao.findUserUidAndCid(Integer.parseInt(rid), uid);

        return uidAndCid!=null;
    }

    @Override
    public void add(String rid, int uid) {
        favoriteDao.add(Integer.parseInt(rid),uid);
    }
}
