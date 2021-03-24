package com.stock.soft.socksoft.serviceImpl;

import com.stock.soft.socksoft.dao.UserDao;
import com.stock.soft.socksoft.model.Users;
import com.stock.soft.socksoft.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public Users auth(String username, String pwd) {
        return userDao.auth(username,pwd);
    }

    @Override
    public Users getByUserName(String username) {
        return userDao.getByUserName(username);
    }


}
