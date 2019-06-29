package cn.itcast.travel.dao.impl;


import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    //声明JDBC
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
   //通过uid单个查询
    @Override
    public List<Category> findAll() {
        String sql="select * from tab_category order by cid";
        return template.query(sql,new BeanPropertyRowMapper<Category>(Category.class));
    }
}
