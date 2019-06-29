package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImp;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao dao= new UserDaoImp();
    @Override
    public boolean regist(User user) {
        //调用方法获得用户名判断
        User user1=dao.findUserByName(user.getUsername());
        if (user1!=null) {
        //用户存在
        return false;
        }
        //不存在
        //设置邮件激活码
        user.setCode(UuidUtil.getUuid());//唯一字符串
        user.setStatus("N");
        dao.save(user);

        //激活邮件发送,设置激活
        String context="<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a>";

        //工具类发送
        MailUtils.sendMail(user.getEmail(),context,"激活邮件");


        return true;
    }
    //激活码验证
    @Override
    public boolean active(String code) {
        //调用findByCode查询激活码是否正确
        User user=dao.findBycode(code);
        //判断激活码是否存在
        if (user!=null) {
            //在修改激活码状态,调用updateStatus
            dao.updateStatus(user);
            return true;
        }else {
            return false;
        }

    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
