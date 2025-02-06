package com.farmeco.Service;



import org.springframework.security.core.userdetails.UserDetails;

import com.farmeco.entity.User;

public interface IJwtService {

	String generateJwToken(User user);
	
	String extractUserName(String token);
	
	Boolean validateToken(String token, UserDetails userDetails);
}
