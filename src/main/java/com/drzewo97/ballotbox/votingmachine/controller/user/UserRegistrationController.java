package com.drzewo97.ballotbox.votingmachine.controller.user;

import com.drzewo97.ballotbox.core.event.OnRegistrationCompleteEvent;
import com.drzewo97.ballotbox.core.model.country.CountryRepository;
import com.drzewo97.ballotbox.core.model.district.DistrictRepository;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.service.converter.Converter;
import com.drzewo97.ballotbox.core.service.roleservice.RoleService;
import com.drzewo97.ballotbox.core.service.userservice.UserService;
import com.drzewo97.ballotbox.votingmachine.dto.VoterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Converter<VoterDto, User> voterDtoUserConverter;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ApplicationEventPublisher eventPublisher;
	
	@ModelAttribute("voter")
	private VoterDto voterDto(){
		return new VoterDto();
	}
	
	@GetMapping
	public String showRegistration(Model model){
		model.addAttribute("countries", countryRepository.findAll());
		model.addAttribute("districts", districtRepository.findAll());
		model.addAttribute("wards", wardRepository.findAll());
		
		return "registration";
	}
	
	@PostMapping
	public String registerVoter(@ModelAttribute("voter") VoterDto voterDto, BindingResult result, HttpServletRequest request){
		if(!voterDto.getPassword().equals(voterDto.getPasswordRepeat())){
			result.rejectValue("password", "password.different", "Passwords are different.");
		}
		
		User user = voterDtoUserConverter.convert(voterDto);
		
		//TODO: implement logic
		if(!userService.isRegisterable(user)){
			result.reject("user.exists", "User cannot register");
		}
		
		user.setRoles(roleService.getVoterRoles());
		user.setActive(false);
		
		userService.save(user);
		
		try {
			String appUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
			eventPublisher.publishEvent(new OnRegistrationCompleteEvent
					(user, appUrl));
		} catch (Exception me) {
			return "redirect:/";
		}
		
		return "redirect:/";
	}

}