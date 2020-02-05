package com.drzewo97.ballotbox.core.listener;

import com.drzewo97.ballotbox.core.event.OnRegistrationCompleteEvent;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
		this.confirmRegistration(onRegistrationCompleteEvent);
	}
	
	private void confirmRegistration(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.createVerificationToken(user, token);
		
		String recipientAddress = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl
				= event.getAppUrl() + "/registrationConfirm?token=" + token;
		String message = "To confirm registration go to link: ";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + confirmationUrl);
		mailSender.send(email);
	}
}
