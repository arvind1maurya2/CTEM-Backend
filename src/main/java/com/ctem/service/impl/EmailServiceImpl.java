package com.ctem.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ctem.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService{
	
	@Autowired
    private JavaMailSender javaMailSender;

	
	@Override
	public boolean sendMail(String email, String subject, String content) {
		boolean status = sendMail(javaMailSender, email, subject, content);
		return status;
	}
	
	public static boolean sendMail(JavaMailSender javaMailSender , String email, String subject, String content) {
		boolean status = true;
		try {
			SimpleMailMessage msg = new SimpleMailMessage();
	        msg.setTo(email);
	        msg.setSubject(subject);
	        msg.setText(content);
	        javaMailSender.send(msg);
			return status;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			return status;
		}
	}


}
