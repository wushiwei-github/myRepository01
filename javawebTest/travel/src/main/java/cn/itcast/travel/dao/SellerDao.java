package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.Seller;

import java.awt.*;

public interface SellerDao {
    public Seller findById(int id);
}
