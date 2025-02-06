package com.farmeco.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.farmeco.config.security.CustomUserDetails;
import com.farmeco.dao.RoleRepository;
import com.farmeco.dao.UserRepository;
import com.farmeco.dto.EmailRequest;
import com.farmeco.dto.LoginRequest;
import com.farmeco.dto.LoginResponse;
import com.farmeco.dto.UserDto;
import com.farmeco.entity.AccountStatus;
import com.farmeco.entity.Role;
import com.farmeco.entity.User;
import com.farmeco.exception.AccountInactiveException;
import com.farmeco.exception.ResourseNotFoundExceptionClass;
import com.farmeco.util.Validation;

import jakarta.mail.MessagingException;

@Service
public class UserService implements IUserService {

	@Autowired
	private Validation validation;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private IJwtService jwtService;
	
	
	@Override
	public Boolean register(UserDto userDto) throws UnsupportedEncodingException, MessagingException {
		
		validation.validateUser(userDto);
		User newUser = mapper.map(userDto, User.class);
		
		setRoles(userDto,newUser);
		setStatus(newUser);
		newUser.setIsDeleted(false);
		
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		
		User savedUser=userRepo.save(newUser);
		
		if(!ObjectUtils.isEmpty(savedUser)) {
			accountConfirmationMail(savedUser);
			return true;
		}
		return false;
	}
	
	private void accountConfirmationMail(User savedUser) throws UnsupportedEncodingException,MessagingException {
		String msg="<h1>Hello [[fName]]</h1>\r\n"
				+ "<p>Your Account created Successfully..</p>\r\n"
				+ "<p>To avtivate account <a href=[[URL]]>Click here</a></p>\r\n"
				+ "<b> Thanks by joining FarmEco.</b>";
		String verificationCode = savedUser.getStatus().getVerification();
		String VerifyUrl = "http://localhost:8080/api/home/verify?userId="+savedUser.getId()+"&code="+verificationCode;
		
		msg = msg.replace("[[fName]]", savedUser.getFirstName());
		msg = msg.replace("[[URL]]",VerifyUrl);
		
		
		EmailRequest emailRequest= EmailRequest.builder()
				.to(savedUser.getEmail())
				.subject("Account Create Successfully!!")
				.title("Account Coonfirmation Mail")
				.message(msg)
				.build();
		emailService.sendMail(emailRequest);
	}

	private void setStatus(User newUser) {
		AccountStatus status = new AccountStatus();
		status.setIsActive(false);
		status.setVerification(UUID.randomUUID().toString());
		newUser.setStatus(status);
	}
	
	private void setRoles(UserDto userDto, User newUser) {
		List<Integer> rolesIds= userDto.getRoles().stream().map(role->role.getId()).toList();
		List<Role> roles= roleRepo.findAllById(rolesIds);
		newUser.setRoles(roles);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		return userRepo.findAllByIsDeletedFalse().stream().map(user->mapper.map(user, UserDto.class)).toList();
	}

	@Override
	public String deleteUserById(Integer id) {
		User foundUser=userRepo.findById(id).orElseThrow(()->new ResourseNotFoundExceptionClass("User Not Found"));
		foundUser.setIsDeleted(true);
		foundUser.setEmail(null);
		userRepo.save(foundUser);
		return "user deleted succesfully";
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		User foundUser= userRepo.findByEmail(loginRequest.getEmail()).orElseThrow(()->new ResourseNotFoundExceptionClass("User not Found"));
		
		if(foundUser.getStatus().getIsActive()) {
			Authentication authenticate =  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
				if(authenticate.isAuthenticated()) {
						//String token="hbsdkjfbzjkzkbdkkjsfbak";
					CustomUserDetails userDetails = (CustomUserDetails)authenticate.getPrincipal();
					User authenticatedUser = userDetails.getUser();
					String token=jwtService.generateJwToken(authenticatedUser);
					
					return LoginResponse.builder()
									.token(token)
									.userDto(mapper.map(authenticatedUser, UserDto.class))
									.build();
				}else {
					throw new BadCredentialsException("invalid credentials!!");
				}
		}else {
			throw new AccountInactiveException("Cant login, first verify the email !!");
		}
	}
	
	

	
}
