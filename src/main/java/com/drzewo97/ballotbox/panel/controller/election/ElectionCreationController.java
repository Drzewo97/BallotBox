package com.drzewo97.ballotbox.panel.controller.election;

import com.drzewo97.ballotbox.core.model.election.Election;
import com.drzewo97.ballotbox.core.model.election.ElectionRepository;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
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
@RequestMapping("/panel/election/create")
public class ElectionCreationController {
	@Autowired
	private ElectionRepository electionRepository;
	
	@Autowired
	private PollRepository pollRepository;
	
	@ModelAttribute("election")
	private Election election(){
		return new Election();
	}
	
	@GetMapping
	private String showElectionCreate(Model model){
		model.addAttribute("polls", pollRepository.findAll());
		return "panel/election_create";
	}
	
	@PostMapping
	private String createElection(@ModelAttribute("election") @Valid Election election, BindingResult result){
		if(electionRepository.existsByName(election.getName())){
			result.rejectValue("name", "name.exist", "Election already exists.");
		}
		
		if(result.hasErrors()){
			return "panel/election_create";
		}
		
		electionRepository.save(election);
		
		return "redirect:create?success";
	}
}
