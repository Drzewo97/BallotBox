package com.drzewo97.ballotbox.committeepanel.controller.root;

import com.drzewo97.ballotbox.core.model.committee.Committee;
import com.drzewo97.ballotbox.core.model.committee.CommitteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/committeepanel")
public class CommitteeSelectionController {
	
	@Autowired
	private CommitteeRepository committeeRepository;
	
	@GetMapping
	private String showCommitteeAdminAdd(Model model){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		Set<Committee> adminsCommittees = committeeRepository.findAllByCommitteeAdminUsername(currentPrincipalName);
		if(adminsCommittees.isEmpty()){
			return "redirect:/";
		}
		
		model.addAttribute("adminsCommittees", adminsCommittees);
		
		return "committeepanel/committeechoice";
	}
}
