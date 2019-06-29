package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.Jedis;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public String findAll() {
        //加入缓存查询，减少服务器运行
        //
        Jedis jedis = JedisUtil.getJedis();
        //获取字符串
        String category = jedis.get("category");
        System.out.println(category);
        if (category == null) {

            //说明缓存中没有，调用方法查询数据库
            List<Category> all = categoryDao.findAll();
            //序列化存入缓存中
            ObjectMapper mapper = new ObjectMapper();

            try {
                category = mapper.writeValueAsString(all);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            jedis.set("category", category);
        }

        return category;
    }
}
