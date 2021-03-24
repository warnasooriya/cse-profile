package com.stock.soft.socksoft.daoImpl;

import com.stock.soft.socksoft.dao.UserDao;
import com.stock.soft.socksoft.model.Users;
import org.hibernate.criterion.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Users auth(String username, String pwd) {
        String sql = "select * from users where username=? and password=?";
        Users users = (Users) entityManager.createNativeQuery(sql,Users.class).setParameter(1,username).setParameter(2,pwd).getSingleResult();
        return users;

    }

    @Override
    public Users getByUserName(String username) {
        String sql = "select * from users where username=?";
        Users users = (Users) entityManager.createNativeQuery(sql,Users.class).setParameter(1,username).getSingleResult();
        return users;
    }
}
