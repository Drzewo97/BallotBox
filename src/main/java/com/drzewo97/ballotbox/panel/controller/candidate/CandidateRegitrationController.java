package com.drzewo97.ballotbox.panel.controller.candidate;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.model.country.CountryRepository;
import com.drzewo97.ballotbox.core.model.district.DistrictRepository;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/panel/candidate/register")
public class CandidateRegitrationController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@ModelAttribute("candidate")
	private Candidate candidate(){
		return new Candidate();
	}
	
	@GetMapping
	private String showCandidateRegister(Model model){
		model.addAttribute("countries", countryRepository.findAll());
		model.addAttribute("districts", districtRepository.findAll());
		model.addAttribute("wards", wardRepository.findAll());
		model.addAttribute("polls", pollRepository.findAll());
		model.addAttribute("users", userRepository.findAll());
		
		return "panel/candidate_register";
	}
	
	@PostMapping
	private String createPoll(@ModelAttribute("candidate") Candidate candidate, BindingResult result){
		if(candidateRepository.existsByPollAndUser(candidate.getPoll(), candidate.getUser())){
			result.rejectValue("name", "name.exist", "Candidate already exists.");
		}
		if(result.hasErrors()){
			return "panel/candidate_register";
		}
		
		candidate.setPlace(0);
		candidate.setVotesPlaced(0);
		candidateRepository.save(candidate);
		
		return "redirect:register?success";
	}
}
