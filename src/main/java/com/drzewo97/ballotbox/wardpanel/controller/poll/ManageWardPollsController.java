package com.drzewo97.ballotbox.wardpanel.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.service.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("wardpanel/{wardId}/poll/all")
public class ManageWardPollsController {
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private WardService wardService;

	@GetMapping
	public String showWardPolls(Model model, @PathVariable("wardId") Long wardId){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			return "redirect:/";
		}
		
		Optional<Ward> ward = wardRepository.findById(wardId);
		if(ward.isEmpty()){
			return "redirect:/";
		}
		
		Set<Poll> polls = pollRepository.findByCountryOrDistrictOrWard(ward.get().getDistrict().getCountry(), ward.get().getDistrict(), ward.get());
		model.addAttribute("polls", polls);
		model.addAttribute("wardId", wardId);
		
		return "wardpanel/ward_poll_all_show";
	}
}
