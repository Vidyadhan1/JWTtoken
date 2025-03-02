package com.farmeco.util;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.farmeco.dao.RoleRepository;
import com.farmeco.dao.UserRepository;
import com.farmeco.dto.UserDto;

@Component
public class Validation {
	
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private UserRepository userRepo;
	
	public static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
	public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,10}$";
	public static final String MOBILE_REGEX = "^(\\+\\d{1,3}[- ]?)?\\d{10}$";
	
	public void validateUser(UserDto user) {
		if(!StringUtils.hasText(user.getFirstName())) {
			throw new IllegalArgumentException("First Name Invalid");
		}
		
		if(!StringUtils.hasText(user.getLastName())) {
			throw new IllegalArgumentException("Last Name Invalid");
		}
		
		if(!StringUtils.hasText(user.getEmail()) || !user.getEmail().matches(EMAIL_REGEX)) {
			throw new IllegalArgumentException("Email Invalid");
		}else if (userRepo.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("Email already used,provide different email");
		}
		
		if(!StringUtils.hasText(user.getPassword()) || !user.getPassword().matches(PASSWORD_REGEX)) {
			throw new IllegalArgumentException("Password Invalid");
		}
		
		if(!StringUtils.hasText(user.getMobileNo()) || !user.getMobileNo().matches(MOBILE_REGEX)) {
			throw new IllegalArgumentException("Mobile Number Invalid");
		}
		
		if(CollectionUtils.isEmpty(user.getRoles())) {
			throw new IllegalArgumentException("Role is not defined");
		}else {
			List<Integer> availableRolesId = roleRepo.findAll().stream().map(role -> role.getId()).toList();
			List<Integer> invalidRoleId = user.getRoles().stream().map(role -> role.getId())
					.filter(roleId -> !availableRolesId.contains(roleId)).toList();
			
			if(!CollectionUtils.isEmpty(invalidRoleId)) {
				throw new IllegalArgumentException("Invaild Roles");
			}

		}
	}
}
