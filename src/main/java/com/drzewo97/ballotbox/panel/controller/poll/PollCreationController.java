package com.drzewo97.ballotbox.panel.controller.poll;

import com.drzewo97.ballotbox.core.service.choiceservice.ChoiceService;
import com.drzewo97.ballotbox.core.service.pollservice.PollService;
import com.drzewo97.ballotbox.core.dto.polldto.PollDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/panel/poll/create")
public class PollCreationController {
	
	@Autowired
	private PollService pollService;
	
	@Autowired
	private ChoiceService choiceService;
	
	@ModelAttribute("poll")
	private PollDto pollDto(){
		return new PollDto();
	}
	
	@GetMapping
	private String showPollCreate(Model model){
		model.addAttribute("allChoices", choiceService.findAllChoices());
		return "panel/poll_create";
	}
	
	@PostMapping
	private String createPoll(@ModelAttribute("poll") @Valid PollDto pollDto, BindingResult result){
		if(pollService.existsByName(pollDto.getName())){
			result.rejectValue("name", "name.exist", "Poll already exists.");
		}
		if(result.hasErrors()){
			return "panel/poll_create";
		}
		
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		
		pollService.save(pollDto, currentPrincipalName);
		return "redirect:create?success";
	}
}