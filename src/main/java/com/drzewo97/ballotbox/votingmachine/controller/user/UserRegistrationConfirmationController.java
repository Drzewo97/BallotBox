package com.drzewo97.ballotbox.votingmachine.controller.user;

import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import com.drzewo97.ballotbox.core.model.verificationtoken.VerificationToken;
import com.drzewo97.ballotbox.core.model.verificationtoken.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Calendar;
import java.util.Optional;

@Controller
@RequestMapping("/registrationConfirm")
public class UserRegistrationConfirmationController {
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String confirmRegistration(Model model, @RequestParam("token") String token){
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		if (verificationToken.isEmpty()) {
			return "redirect:/";
		}
		
		Calendar cal = Calendar.getInstance();
		if (verificationToken.get().getExpiryDate().before(cal.getTime())) {
			return "redirect:/";
		}
		
		User user = verificationToken.get().getUser();
		user.setActive(true);
		userRepository.save(user);
		return "redirect:/";
	}
}
