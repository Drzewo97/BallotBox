package com.drzewo97.ballotbox.wardpanel.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.model.wardprotocol.WardProtocol;
import com.drzewo97.ballotbox.core.model.wardprotocol.WardProtocolRepository;
import com.drzewo97.ballotbox.core.service.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("wardpanel/{wardId}/poll/{pollId}/protocol/add")
public class AddPollWardProtocolController {
	
	@Autowired
	private WardService wardService;
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private WardProtocolRepository wardProtocolRepository;
	
	@GetMapping
	public String showAddPollWardProtocol(Model model, @PathVariable("wardId") Long wardId, @PathVariable("pollId") Long pollId){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			return "redirect:/";
		}
		
		// ward is present for sure
		Optional<Ward> ward = wardRepository.findById(wardId);
		// returns null if poll is not in this ward
		Optional<Poll> poll = pollRepository.findByIdAndCountryOrDistrictOrWard(pollId, ward.get().getDistrict().getCountry(), ward.get().getDistrict(), ward.get());
		if(poll.isEmpty() || poll.get().isProtocolVotesCountEligible()){
			return "redirect:/";
		}
		Optional<WardProtocol> wardProtocolOptional = wardProtocolRepository.findByWardAndPoll(ward.get(), poll.get());
		if(wardProtocolOptional.isPresent()){
			return "redirect:/";
		}
		
		model.addAttribute("wardProtocol", new WardProtocol());
		
		return "wardpanel/add_poll_ward_protocol";
	}
	
	@PostMapping
	public String addPollWardProtocol(Model model, @PathVariable("wardId") Long wardId, @PathVariable("pollId") Long pollId, @ModelAttribute("wardProtocol") WardProtocol wardProtocol){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			return "redirect:/";
		}
		
		// ward is present for sure
		Optional<Ward> ward = wardRepository.findById(wardId);
		// returns null if poll is not in this ward
		Optional<Poll> poll = pollRepository.findByIdAndCountryOrDistrictOrWard(pollId, ward.get().getDistrict().getCountry(), ward.get().getDistrict(), ward.get());
		if(poll.isEmpty()){
			return "redirect:/";
		}
		Optional<WardProtocol> wardProtocolOptional = wardProtocolRepository.findByWardAndPoll(ward.get(), poll.get());
		if(wardProtocolOptional.isPresent()){
			return "redirect:/";
		}
		
		//TODO: check ward protocol values
		wardProtocol.setWard(ward.get());
		wardProtocol.setPoll(poll.get());
		
		wardProtocolRepository.save(wardProtocol);
		
		return "redirect:/";
	}
}
