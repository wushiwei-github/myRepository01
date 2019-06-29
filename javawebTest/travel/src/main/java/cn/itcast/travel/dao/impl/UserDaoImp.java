package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImp implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findUserByName(String username) {
        try {
            String sql = "select * from tab_user where username=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
            return user;
        } catch (Exception e) {

            return null;
        }

    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode());
    }

    //激活码状态修改
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status='Y' where uid=?";
        template.update(sql, user.getUid());

    }

    //查询激活码对象
    @Override
    public User findBycode(String code) {
        try {
            String sql = "select * from tab_user where code = ?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try {
            String sql="select * from tab_user where username=? and password=?";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }
}
