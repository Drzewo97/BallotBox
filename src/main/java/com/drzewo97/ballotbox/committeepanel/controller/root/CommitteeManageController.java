package com.drzewo97.ballotbox.committeepanel.controller.root;

import com.drzewo97.ballotbox.core.service.committee.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/committeepanel/{committeeId}")
public class CommitteeManageController {
	
	@Autowired
	private CommitteeService committeeService;
	
	@GetMapping
	private String showCommitteeManage(@PathVariable("committeeId") Integer committeeId){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!committeeService.isCommitteeAdmin(currentPrincipalName, committeeId)){
			return "redirect:/";
		}
		
		return "committeepanel/committeepanel";
	}
}
