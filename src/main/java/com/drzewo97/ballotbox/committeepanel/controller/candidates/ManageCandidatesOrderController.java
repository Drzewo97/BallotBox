package com.drzewo97.ballotbox.committeepanel.controller.candidates;

import com.drzewo97.ballotbox.committeepanel.dto.CandidatesOrderDto;
import com.drzewo97.ballotbox.core.model.committee.Committee;
import com.drzewo97.ballotbox.core.model.committee.CommitteeRepository;
import com.drzewo97.ballotbox.core.model.committeecandidateorder.CommitteeCandidateOrderRepository;
import com.drzewo97.ballotbox.core.service.committee.CommitteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/committeepanel/{committeeId}/order")
public class ManageCandidatesOrderController {
	
	@Autowired
	private CommitteeRepository committeeRepository;
	
	@Autowired
	private CommitteeService committeeService;
	
	@Autowired
	private CommitteeCandidateOrderRepository committeeCandidateOrderRepository;
	
	@GetMapping
	public String showManageCandidatesOrder(Model model, @PathVariable("committeeId") Long committeeId){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!committeeService.isCommitteeAdmin(currentPrincipalName, committeeId)){
			return "redirect:/";
		}
		
		Committee committee = committeeRepository.findById(committeeId).get();
		
		CandidatesOrderDto candidatesOrderDto = new CandidatesOrderDto();
		candidatesOrderDto.setCandidatesOrder(new ArrayList<>(committee.getCandidateOrders()));
		
		model.addAttribute("candidatesOrderDto", candidatesOrderDto);
		model.addAttribute("committeeId", committeeId);
		
		return "committeepanel/manage_candidates_order";
	}
	
	@PostMapping
	public String manageCandidatesOrder(@ModelAttribute("candidatesOrderDto") CandidatesOrderDto candidateOrders, @PathVariable("committeeId") Long committeeId){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!committeeService.isCommitteeAdmin(currentPrincipalName, committeeId)){
			return "redirect:/";
		}
		
		//TODO: validity check
		
		committeeCandidateOrderRepository.saveAll(candidateOrders.getCandidatesOrder());
		
		return "redirect:/committeepanel";
	}
}
