package com.stock.soft.socksoft.serviceImpl;


import com.stock.soft.socksoft.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UserInfoService userInfoDAO;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users users = userInfoDAO.getUserInfoByUserName(userName);
		GrantedAuthority authority = new SimpleGrantedAuthority(users.getRole());
		return new User(users.getUserName(), users.getPassword(), Arrays.asList(authority));
	}
}
