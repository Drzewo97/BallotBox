package com.drzewo97.ballotbox.wardpanel.controller.poll;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotes;
import com.drzewo97.ballotbox.core.model.candidateprotocolvotes.CandidateProtocolVotesRepository;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocolRepository;
import com.drzewo97.ballotbox.core.service.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
		if(poll.isEmpty()){
			return "redirect:/";
		}
		Optional<CandidatesVotesCountWardProtocol> wardProtocolOptional = wardProtocolRepository.findByWardAndPoll(ward.get(), poll.get());
		if(wardProtocolOptional.isPresent()){
			return "redirect:/";
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
	public String addPollWardProtocol(Model model, @PathVariable("wardId") Long wardId, @PathVariable("pollId") Long pollId, @ModelAttribute("wardProtocol") CandidatesVotesCountWardProtocol wardProtocol){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			return "redirect:/";
		}
		
		// ward is present for sure
		Optional<Ward> ward = wardRepository.findById(wardId);
		// returns null if poll is not in this ward
		Optional<Poll> poll = pollRepository.findByIdAndCountryOrDistrictOrWard(pollId, ward.get().getDistrict().getCountry(), ward.get().getDistrict(), ward.get());
		if(poll.isEmpty() || !poll.get().isProtocolVotesCountEligible()){
			return "redirect:/";
		}
		Optional<CandidatesVotesCountWardProtocol> wardProtocolOptional = wardProtocolRepository.findByWardAndPoll(ward.get(), poll.get());
		if(wardProtocolOptional.isPresent()){
			return "redirect:/";
		}
		
		//TODO: check ward protocol values
		wardProtocol.setWard(ward.get());
		wardProtocol.setPoll(poll.get());
		
		// workaround for lack of index access on set
		Set<CandidateProtocolVotes> candidateProtocolVotesSet = new HashSet<>();
		for(CandidateProtocolVotes c : wardProtocol.getCandidateProtocolVotesAsList()){
			c.setCandidate(candidateRepository.findById(c.getCandidate().getId()).get());
			candidateProtocolVotesSet.add(c);
		}
		candidateProtocolVotesRepository.saveAll(candidateProtocolVotesSet);
		wardProtocol.setCandidateProtocolVotes(candidateProtocolVotesSet);
		
		wardProtocolRepository.save(wardProtocol);
		
		return "redirect:/";
	}
}
