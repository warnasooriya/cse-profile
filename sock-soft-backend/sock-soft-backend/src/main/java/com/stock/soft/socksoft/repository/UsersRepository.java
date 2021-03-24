package com.stock.soft.socksoft.repository;

import com.stock.soft.socksoft.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users, String> {


}
