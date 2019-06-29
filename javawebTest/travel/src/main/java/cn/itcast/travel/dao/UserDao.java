package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    public User findUserByName(String username);
    public void save(User user);

    void updateStatus(User user);

    User findBycode(String code);

    User findUserByUsernameAndPassword(String username, String password);
}
