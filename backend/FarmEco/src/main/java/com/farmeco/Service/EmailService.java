package com.farmeco.Service;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.farmeco.dto.EmailRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;

	public void sendMail(EmailRequest emailRequest) throws MessagingException,UnsupportedEncodingException{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(mimeMessage);
		helper.setTo(emailRequest.getTo());
		helper.setSubject(emailRequest.getSubject());
		helper.setText(emailRequest.getMessage(),true);
		helper.setFrom(from,emailRequest.getTitle());
		
		mailSender.send(mimeMessage);
		
	}
}
