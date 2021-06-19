package com.stock.soft.socksoftSync.repository;

import com.stock.soft.socksoftSync.model.Companies;
import org.springframework.data.repository.CrudRepository;

public interface  CompanyRepository extends CrudRepository<Companies, String> {

    Companies findByCode(String symbol);
}
