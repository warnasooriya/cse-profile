package com.stock.soft.socksoft.service;

import com.stock.soft.socksoft.model.Users;

import java.util.List;

public interface UserService {
    Users auth(String username, String pwd);

    Users getByUserName(String username);
}
