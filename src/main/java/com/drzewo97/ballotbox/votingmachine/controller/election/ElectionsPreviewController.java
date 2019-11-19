package com.drzewo97.ballotbox.votingmachine.controller.election;

import com.drzewo97.ballotbox.core.model.election.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/election/all")
public class ElectionsPreviewController {
	@Autowired
	private ElectionRepository electionRepository;
	
	@GetMapping
	public String showElections(WebRequest request, Model model) {
		// Add all polls as attribute
		model.addAttribute("elections", electionRepository.findAll());
		
		// Return view
		return "votingmachine/election_all_show";
	}
}