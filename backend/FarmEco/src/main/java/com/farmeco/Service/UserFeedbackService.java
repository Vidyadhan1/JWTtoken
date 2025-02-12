package com.farmeco.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.farmeco.config.security.CustomUserDetails;
import com.farmeco.dao.UserFeedbackRepository;
import com.farmeco.dao.UserRepository;
import com.farmeco.dto.FeedbackDto;
import com.farmeco.entity.User;
import com.farmeco.entity.UserFeedback;
import com.farmeco.util.LoggedInUserId;


@Service
public class UserFeedbackService implements IUserFeedbackService{
	
	@Autowired
	private UserFeedbackRepository feedbackRepository;
	@Autowired
	private UserRepository userRepo;

	@Override
	public Boolean saveFeedback(FeedbackDto feedbackDto) {
//		CustomUserDetails userDetails = (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		Integer id = userDetails.getUser().getId();
		
		LoggedInUserId loggedInUserId = new LoggedInUserId();
		Integer id = loggedInUserId.getLoggedInUserId();
		
		System.out.println(id);
		Optional<User> user = userRepo.findById(id);
		User farmer= user.get();
		UserFeedback userFeedback = new UserFeedback();
		userFeedback.setUser(farmer);
		userFeedback.setName(feedbackDto.getName());
		userFeedback.setEmail(feedbackDto.getEmail());
		userFeedback.setSubject(feedbackDto.getSubject());
		userFeedback.setMessage(feedbackDto.getMessage());
		feedbackRepository.save(userFeedback);
		return true;
	}

//	public Boolean saveFeedback(FeedbackDto feedbackDto) {
//		Optional<User> userId = feedbackRepository.findById(feedbackDto.getUserDto().getId());
//		User farmer = userId.get();
//		UserFeedback userFeedback = new UserFeedback();
//		userFeedback.setId(userId);
//		userFeedback.setName(feedbackDto.getName());
//		userFeedback.setEmail(feedbackDto.getEmail());
//		userFeedback.setSubject(feedbackDto.getSubject());
//		userFeedback.setMessage(feedbackDto.getMessage());
//		
//		userFeedbackRepo.save(userFeedback);
//		return null;
//		
//	}
}
