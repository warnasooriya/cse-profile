package com.stock.soft.socksoft.repository;

import com.stock.soft.socksoft.model.Companies;
import org.springframework.data.repository.CrudRepository;

public interface  CompanyRepository extends CrudRepository<Companies, String> {

    Companies findByCode(String symbol);
}
