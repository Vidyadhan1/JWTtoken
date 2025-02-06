package com.farmeco.dto;

import com.farmeco.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class FeedbackDto {
	
	private Integer id;

	private String name;
    private String email;
    private String subject;
    private String message;
}
