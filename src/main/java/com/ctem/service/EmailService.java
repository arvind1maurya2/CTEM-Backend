package com.ctem.service;
/**
 * 
 * @author Arvind Maurya
 *
 */
public interface EmailService {
	public boolean sendMail(String email, String subject , String content);
}