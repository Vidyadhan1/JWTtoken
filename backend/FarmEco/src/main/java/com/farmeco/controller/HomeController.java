package com.farmeco.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.farmeco.Service.IHomeService;

@RestController
@RequestMapping("/api/home")
public class HomeController {

	private IHomeService homeService;
	
	@GetMapping("/verify")
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer userId,@RequestParam String code) {
		return ResponseEntity.ok(homeService.verifyAccount(userId, code));
	}
	
}
