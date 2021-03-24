package com.stock.soft.socksoft.repository;


import com.stock.soft.socksoft.model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface UserDetailsRepository extends CrudRepository<Users, String> {
	public Users findByUserNameAndEnabled(String userName, short enabled);

	public List<Users> findAllByEnabled(short enabled);

	public Users findById(Long id);
//
//	@Override
//	public Users save(Users users);

	public List<Users> findAll();

	public void deleteById(Long id);
}
