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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping
	public String pollResult(Model model, @PathVariable("id") Integer id){
		Optional<Poll> poll = pollRepository.findById(id);
		if(poll.isEmpty()){
			return "redirect:/poll/all?empty";
		}
		
		if(poll.get().getActive()){
			// TODO: change appropriately
			return "redirect:/poll/all?empty";
		}
		
		Optional<PollResult> result = pollResultRepository.findByPoll(poll.get());
		if(result.isPresent()){
			model.addAttribute("result", result.get());
		}
		else{
			CandidateResultsCalculationService resultsCalculationService = applicationContext.getBean(CandidateResultsCalculationService.class, poll.get());
			
			Set<CandidatesVotesCountWardProtocol> wardProtocols = wardProtocolRepository.findAllByPoll(poll.get());
			// TODO: if not all eligible wards -> return, don't continue
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
