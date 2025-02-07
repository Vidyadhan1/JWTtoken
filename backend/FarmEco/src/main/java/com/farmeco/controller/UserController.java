package com.farmeco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.farmeco.Service.IUserFeedbackService;
import com.farmeco.Service.IUserService;
import com.farmeco.dto.FeedbackDto;
import com.farmeco.dto.UserDto;

@RestController
	@RequestMapping("/api/user")
	public class UserController {
		
		private IUserService userService;
		
		public UserController(IUserService userService) {
	        this.userService = userService;
	    }
		
		@GetMapping("/all")
		public ResponseEntity<?> allUsers() {
			List<UserDto> allUsers =  userService.getAllUsers();
			if(CollectionUtils.isEmpty(allUsers)) {
				return new ResponseEntity<>("users not availabble",HttpStatus.NO_CONTENT);
			}
			return ResponseEntity.ok(allUsers);
		}
		
		@DeleteMapping("/delete/{id}")
		public ResponseEntity<?> deleteUserById(@PathVariable Integer id){
			return ResponseEntity.ok(userService.deleteUserById(id));
		}
		
		
		@GetMapping("/forAdmin")
		@PreAuthorize("hasRole('Admin')")
		public ResponseEntity<?> endpointOfAdmin(){
			return ResponseEntity.ok("This Api of Admin");
					
		}
		@GetMapping("/forUser")
		@PreAuthorize("hasRole('Farmer')")
		public ResponseEntity<?> endpointOfUser(){
			return ResponseEntity.ok("This Api of User");
			
		}
		@GetMapping("/forBoth")
		@PreAuthorize("hasAnyRole('Admin','Farmer')")
		public ResponseEntity<?> endpointOfBothUserAndAdmin(){
			return ResponseEntity.ok("This Api of User");
			
		}
		
//		@RestController
//		@RequestMapping("/feedback")
//		public class UserFeedbackController {

		    @Autowired
		    private IUserFeedbackService userFeedbackService;

		    @PostMapping("/save")
		    public ResponseEntity<String> saveFeedback(@RequestBody FeedbackDto feedbackDto) {
		        Boolean saveFeedback = userFeedbackService.saveFeedback(feedbackDto);
		        return new ResponseEntity<>("Record adddedd successfully", HttpStatus.OK);
		    }
//		}
		
	}

