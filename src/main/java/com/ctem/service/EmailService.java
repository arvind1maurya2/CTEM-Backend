package com.ctem.service;
public interface EmailService {
	public boolean sendMail(String email, String subject , String content);
}