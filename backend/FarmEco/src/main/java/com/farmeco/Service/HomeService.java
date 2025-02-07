package com.farmeco.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farmeco.dao.UserRepository;
import com.farmeco.entity.User;
import com.farmeco.exception.ResourseNotFoundExceptionClass;

@Service
public class HomeService implements IHomeService {

	@Autowired
	private UserRepository userRepo;
	
	
	@Override
	public String verifyAccount(Integer id, String code) {
	
		User foundUser=userRepo.findById(id).orElseThrow(()->new ResourseNotFoundExceptionClass("Invallid User"));
		
		if(code.equals(foundUser.getStatus().getVerification())) {
			foundUser.getStatus().setIsActive(true);
			foundUser.getStatus().setVerification(null);
			userRepo.save(foundUser);
			return "Account Verified Successfully";
		}else if(foundUser.getStatus().getVerification() == null) {
			return "Account already Verified";
		}
		return "verification failed";
	}

}
