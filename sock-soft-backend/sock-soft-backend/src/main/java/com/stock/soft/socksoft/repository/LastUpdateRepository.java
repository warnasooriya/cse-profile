package com.stock.soft.socksoft.repository;

import com.stock.soft.socksoft.model.LastUpdate;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;

public interface LastUpdateRepository extends CrudRepository<LastUpdate, BigInteger> {
}
