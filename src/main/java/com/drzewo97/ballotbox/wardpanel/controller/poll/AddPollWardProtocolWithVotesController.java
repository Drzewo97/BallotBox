package com.drzewo97.ballotbox.wardpanel.controller.poll;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotes;
import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotesRepository;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocolRepository;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.service.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("wardpanel/{wardId}/poll/{pollId}/protocol/add/withVotes")
public class AddPollWardProtocolWithVotesController {
	
	@Autowired
	private WardService wardService;
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private CandidatesVotesCountWardProtocolRepository wardProtocolRepository;
	
	@Autowired
	private CandidateProtocolVotesRepository candidateProtocolVotesRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	private final String POST_ORDERING_LANDING_PAGE = "redirect:/wardpanel/{0}/poll/all";
	
	@GetMapping
	public String showAddPollWardProtocol(Model model,
	                                      @PathVariable("wardId") Integer wardId,
	                                      @PathVariable("pollId") Integer pollId,
	                                      RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			outputMessages.add("You are not this ward admin!");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		Ward ward = wardRepository.findById(wardId).get();
		
		Optional<Poll> poll = pollRepository.findById(pollId);
		if(poll.isEmpty()){
			// no such poll
			outputMessages.add("No such poll!");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		// if poll is not in this ward or poll is protocol votes eligible
		if(!poll.get().equals(pollRepository.findByIdAndCountryOrDistrictOrWard(pollId, ward.getDistrict().getCountry(), ward.getDistrict(), ward).get()) || !poll.get().isProtocolVotesCountEligible()){
			outputMessages.add("Inconsistency between poll and ward.");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		if(poll.get().getActive() || poll.get().getOpenFrom().isAfter(LocalDateTime.now())){
			outputMessages.add("You cannot add ward protocol yet.");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		
		Optional<CandidatesVotesCountWardProtocol> wardProtocolOptional = wardProtocolRepository.findByWardAndPoll(ward, poll.get());
		if(wardProtocolOptional.isPresent()){
			outputMessages.add("Protocol is already sent.");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		CandidatesVotesCountWardProtocol wardProtocol = new CandidatesVotesCountWardProtocol();
		// initialize number of votes for candidates form
		Set<CandidateProtocolVotes> candidateProtocolVotes = new HashSet<>();
		for(Candidate candidate : poll.get().getCandidates()){
			candidateProtocolVotes.add(new CandidateProtocolVotes(candidate));
		}
		wardProtocol.setCandidateProtocolVotes(candidateProtocolVotes);
		model.addAttribute("wardProtocol", wardProtocol);
		
		return "wardpanel/add_poll_ward_protocol_with_votes";
	}
	
	@PostMapping
	public String addPollWardProtocol(Model model,
	                                  @PathVariable("wardId") Integer wardId,
	                                  @PathVariable("pollId") Integer pollId,
	                                  @ModelAttribute("wardProtocol") CandidatesVotesCountWardProtocol wardProtocol,
	                                  RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			outputMessages.add("You are not this ward admin!");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		Ward ward = wardRepository.findById(wardId).get();
		
		Optional<Poll> poll = pollRepository.findById(pollId);
		if(poll.isEmpty()){
			// no such poll
			outputMessages.add("No such poll!");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		// if poll is not in this ward or poll is protocol votes eligible
		if(!poll.get().equals(pollRepository.findByIdAndCountryOrDistrictOrWard(pollId, ward.getDistrict().getCountry(), ward.getDistrict(), ward).get()) || !poll.get().isProtocolVotesCountEligible()){
			outputMessages.add("Inconsistency between poll and ward.");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		if(poll.get().getActive() || poll.get().getOpenFrom().isAfter(LocalDateTime.now())){
			outputMessages.add("You cannot add ward protocol yet.");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		
		Optional<CandidatesVotesCountWardProtocol> wardProtocolOptional = wardProtocolRepository.findByWardAndPoll(ward, poll.get());
		if(wardProtocolOptional.isPresent()){
			outputMessages.add("Protocol is already sent.");
			return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
		}
		
		if(!wardService.isProtocolValid(wardProtocol)){
			outputMessages.add("Protocol has logic errors. Please correct.");
			Set<CandidateProtocolVotes> candidateProtocolVotes = new HashSet<>();
			for(CandidateProtocolVotes c : wardProtocol.getCandidateProtocolVotesAsList()){
				CandidateProtocolVotes newCPV = new CandidateProtocolVotes();
				newCPV.setVotesCount(c.getVotesCount());
				newCPV.setCandidatesVotesCountWardProtocol(c.getCandidatesVotesCountWardProtocol());
				newCPV.setCandidate(candidateRepository.findById(c.getCandidate().getId()).get());
				candidateProtocolVotes.add(newCPV);
			}
			wardProtocol.setCandidateProtocolVotes(candidateProtocolVotes);
			model.addAttribute("wardProtocol", wardProtocol);
			model.addAttribute("messages", outputMessages);
			return "wardpanel/add_poll_ward_protocol_with_votes";
		}
		
		wardProtocol.setWard(ward);
		wardProtocol.setPoll(poll.get());
		wardProtocolRepository.save(wardProtocol);
		
		// workaround for lack of index access on set
		Set<CandidateProtocolVotes> candidateProtocolVotesSet = new HashSet<>();
		for(CandidateProtocolVotes c : wardProtocol.getCandidateProtocolVotesAsList()){
			c.setCandidate(candidateRepository.findById(c.getCandidate().getId()).get());
			c.setCandidatesVotesCountWardProtocol(wardProtocol);
			candidateProtocolVotesSet.add(c);
		}
		candidateProtocolVotesRepository.saveAll(candidateProtocolVotesSet);
		
		outputMessages.add("Protocol saved correctly.");
		return MessageFormat.format(POST_ORDERING_LANDING_PAGE, wardId);
	}
}
