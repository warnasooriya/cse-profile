package com.stock.soft.socksoft.dao;

import com.stock.soft.socksoft.model.Users;

public interface UserDao {
    Users auth(String username, String pwd);

    Users getByUserName(String username);
}
