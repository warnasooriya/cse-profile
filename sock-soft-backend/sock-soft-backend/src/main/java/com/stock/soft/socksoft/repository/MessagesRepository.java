package com.stock.soft.socksoft.repository;

import com.stock.soft.socksoft.model.UserMessage;
import org.springframework.data.repository.CrudRepository;

public interface MessagesRepository extends CrudRepository<UserMessage, String> {
}
