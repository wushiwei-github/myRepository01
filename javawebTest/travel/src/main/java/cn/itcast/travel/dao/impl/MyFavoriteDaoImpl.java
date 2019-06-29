package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.MyFavoriteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MyFavoriteDaoImpl implements MyFavoriteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    //查询uid返回一个集合
    @Override
    public List<Route> findMyFavorite() {
        String sql="select * from tab_favorite where uid=?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<Route>(Route.class));
        return query;
    }
}
