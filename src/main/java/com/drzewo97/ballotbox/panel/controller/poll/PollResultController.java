package com.drzewo97.ballotbox.panel.controller.poll;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocol;
import com.drzewo97.ballotbox.core.model.candidatesvotescountwardprotocol.CandidatesVotesCountWardProtocolRepository;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;
import com.drzewo97.ballotbox.core.model.pollresult.PollResultRepository;
import com.drzewo97.ballotbox.core.service.calculation.candidateresult.CandidateResultsCalculationService;
import com.drzewo97.ballotbox.core.service.calculation.pollresult.PollResultCalculationService;
import com.drzewo97.ballotbox.core.service.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/panel/poll/{id}/result")
public class PollResultController {
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private PollResultRepository pollResultRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private CandidatesVotesCountWardProtocolRepository wardProtocolRepository;
	
	@Autowired
	private WardService wardService;
	
	private final String ERROR_LANDING_PAGE = "redirect:/panel/poll/all";
	
	@GetMapping
	public String pollResult(Model model,
	                         @PathVariable("id") Integer id,
	                         RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		Optional<Poll> poll = pollRepository.findById(id);
		if(poll.isEmpty()){
			outputMessages.add("No such poll.");
			return ERROR_LANDING_PAGE;
		}
		
		if(LocalDateTime.now().isBefore(poll.get().getOpenFrom())){
			outputMessages.add("Poll has not started yet.");
			return ERROR_LANDING_PAGE;
		}
		
		if(poll.get().getActive()){
			outputMessages.add("Poll is still active! Wait for it to finish and for all wards to send their protocols.");
			return ERROR_LANDING_PAGE;
		}
		
		Optional<PollResult> result = pollResultRepository.findByPoll(poll.get());
		if(result.isPresent()){
			model.addAttribute("result", result.get());
		}
		else{
			// get all given protocols from wards
			Set<CandidatesVotesCountWardProtocol> wardProtocols = wardProtocolRepository.findAllByPoll(poll.get());
			
			if(poll.get().isProtocolVotesCountEligible() && wardProtocols.size() != wardService.findAllEligibleForPollProtocol(poll.get()).size()){
				// when number of ward protocols is different than number of wards eligible for this poll -> some didn't send theirs
				outputMessages.add("Not all wards sent their protocols. Unable to calculate results");
				return ERROR_LANDING_PAGE;
			}
			
			CandidateResultsCalculationService resultsCalculationService = applicationContext.getBean(CandidateResultsCalculationService.class, poll.get());
			Set<Candidate> candidateResults = (Set<Candidate>) resultsCalculationService.calculateResults(poll.get().getVotes(), wardProtocols);
			
			PollResultCalculationService pollResultCalculationService = applicationContext.getBean(PollResultCalculationService.class, poll.get());
			PollResult pollResult = pollResultCalculationService.calculateResult(candidateResults);
			
			pollResult.setPoll(poll.get());
			
			pollResultRepository.save(pollResult);
			candidateRepository.saveAll(candidateResults);
			
			model.addAttribute("result", pollResult);
		}
		
		model.addAttribute("poll", poll.get());
		
		return "panel/poll_result";
	}
}
