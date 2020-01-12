package com.drzewo97.ballotbox.panel.controller.committee;

import com.drzewo97.ballotbox.core.model.committee.Committee;
import com.drzewo97.ballotbox.core.model.committee.CommitteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/panel/committee/create")
public class CommitteeCreationController {
	
	@Autowired
	private CommitteeRepository committeeRepository;
	
	@ModelAttribute("committee")
	private Committee committee(){
		return new Committee();
	}
	
	@GetMapping
	private String showCommitteeCreate(Model model){
		return "panel/committee_create";
	}
	
	@PostMapping
	private String createCommittee(@ModelAttribute("committee") @Valid Committee committee, BindingResult result){
		if(committeeRepository.existsByName(committee.getName())){
			result.rejectValue("name", "name.exist", "Committee already exists.");
		}
		
		if(result.hasErrors()){
			return "panel/committee_create";
		}
		
		committeeRepository.save(committee);
		
		return "redirect:create?success";
	}
}
