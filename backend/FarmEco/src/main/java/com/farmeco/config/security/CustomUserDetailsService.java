package com.farmeco.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.farmeco.dao.UserRepository;
import com.farmeco.entity.User;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User foundUser=userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("Email not Found"));
		return new CustomUserDetails(foundUser);
	}

	
}
