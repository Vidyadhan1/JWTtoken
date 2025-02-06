package com.farmeco.dto;

import com.farmeco.entity.Role;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailRequest {

	private String to;
	
	private String subject;
	
	private String message;
	
	private String title;
}
