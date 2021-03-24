package com.stock.soft.socksoft.repository;

import com.stock.soft.socksoft.model.Companies;
import com.stock.soft.socksoft.model.Deposit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepositRepository extends CrudRepository<Companies, String> {

}
