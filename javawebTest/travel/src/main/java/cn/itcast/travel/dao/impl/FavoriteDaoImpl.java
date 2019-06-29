package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findUserUidAndCid(int rid, int uid) {
        try {
            String sql="select * from tab_favorite where rid=? and uid=?";
            Favorite favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
            return favorite;
        } catch (DataAccessException e) {
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public int fingCountByRid(int rid) {
        String sql="select count(*) from tab_favorite where rid=?";
        return template.queryForObject(sql,Integer.class,rid);


    }

    @Override
    public void add(int rid, int uid) {
        String sql="insert into tab_favorite values(?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }
}
