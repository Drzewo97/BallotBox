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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/committeepanel/{committeeId}/order")
public class ManageCandidatesOrderController {
	
	@Autowired
	private CommitteeRepository committeeRepository;
	
	@Autowired
	private CommitteeService committeeService;
	
	@Autowired
	private CommitteeCandidateOrderRepository committeeCandidateOrderRepository;
	
	private final String POST_ORDERING_LANDING_PAGE = "redirect:/committeepanel/";
	
	@GetMapping
	public String showManageCandidatesOrder(Model model,
	                                        @PathVariable("committeeId") Integer committeeId,
	                                        RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!committeeService.isCommitteeAdmin(currentPrincipalName, committeeId)){
			outputMessages.add("You are not this committee admin!");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		Committee committee = committeeRepository.findById(committeeId).get();
		
		if(committee.getMembers().size() == 0){
			// no members to order
			outputMessages.add("There are no members!");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		CandidatesOrderDto candidatesOrderDto = new CandidatesOrderDto();
		candidatesOrderDto.setCandidatesOrder(new ArrayList<>(committee.getCandidateOrders()));
		
		model.addAttribute("candidatesOrderDto", candidatesOrderDto);
		model.addAttribute("committeeId", committeeId);
		
		return "committeepanel/manage_candidates_order";
	}
	
	@PostMapping
	public String manageCandidatesOrder(@ModelAttribute("candidatesOrderDto") CandidatesOrderDto candidateOrders,
	                                    @PathVariable("committeeId") Integer committeeId,
	                                    RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!committeeService.isCommitteeAdmin(currentPrincipalName, committeeId)){
			outputMessages.add("You are not this committee admin!");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		Committee committee = committeeRepository.findById(committeeId).get();
		
		if(committee.getMembers().size() == 0){
			// no members to order
			outputMessages.add("There are no members!");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		// check for null/0/negative values
		if(candidateOrders.getCandidatesOrder().stream().anyMatch(o -> o.getCandidateOrder() == null || o.getCandidateOrder() < 1)){
			outputMessages.add("You left some candidates without order!");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		// check for duplicates
		if(!candidateOrders.getCandidatesOrder().stream().mapToInt(o -> o.getCandidateOrder()).allMatch(new HashSet<>()::add)){
			outputMessages.add("You duplicated some order for candidates");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		// TODO: committee members number could change between GET and POST, but don't handle it
		// not empty, no duplicates, no null and 0 -> max order should be equal to number of committee members
		if(committee.getMembers().size() != candidateOrders.getCandidatesOrder().stream().mapToInt(o -> o.getCandidateOrder()).max().getAsInt()){
			outputMessages.add("You jumped in order of some candidates - order should be continuous");
			return POST_ORDERING_LANDING_PAGE + committeeId;
		}
		
		committeeCandidateOrderRepository.saveAll(candidateOrders.getCandidatesOrder());
		
		return POST_ORDERING_LANDING_PAGE + committeeId;
	}
}
