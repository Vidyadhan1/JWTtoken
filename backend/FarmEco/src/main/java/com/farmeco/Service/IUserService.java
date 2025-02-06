package com.farmeco.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.farmeco.dto.LoginRequest;
import com.farmeco.dto.LoginResponse;
import com.farmeco.dto.UserDto;

import jakarta.mail.MessagingException;

public interface IUserService {

	Boolean register(UserDto user) throws UnsupportedEncodingException, MessagingException;
	
	List<UserDto> getAllUsers();
	
	String deleteUserById(Integer id);
	
	LoginResponse login(LoginRequest loginRequest);
}
