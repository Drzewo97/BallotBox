package com.drzewo97.ballotbox.votingmachine.controller.election;

import com.drzewo97.ballotbox.core.dto.electionvote.ElectionVoteDto;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.election.Election;
import com.drzewo97.ballotbox.core.model.election.ElectionRepository;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.vote.VoteRepository;
import com.drzewo97.ballotbox.core.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;

@Controller
@RequestMapping("/election/{id}/vote")
public class ElectionVoteController {
	
	@Autowired
	private ElectionRepository electionRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private Converter<Set<Candidate>, Vote> converter;
	
	@GetMapping
	public String showElections(@PathVariable("id") Long id, WebRequest request, Model model) {
		Optional<Election> election = electionRepository.findById(id);
		if(election.isEmpty()){
			return "redirect:/election/all?empty";
		}
		
		model.addAttribute("election", election.get());
		model.addAttribute("electionVote", new ElectionVoteDto());
		
		// Return view
		return "votingmachine/election_vote";
	}
	
	@PostMapping
	public String registerVote(@ModelAttribute("electionVote") ElectionVoteDto electionVoteDto, BindingResult result){
		if(result.hasErrors()){
			return "panel/district_create";
		}
		
		// divide candidate list into polls
		Map<Poll, Set<Candidate>> pollCandidates = constructPollCandidates(electionVoteDto.getChoices());
		
		for(Set<Candidate> candidates : pollCandidates.values()){
			voteRepository.save(converter.convert(candidates));
		}
		
		return "redirect:/election/all?success";
	}
	
	
	private Map<Poll, Set<Candidate>> constructPollCandidates(Set<Candidate> candidates){
		//https://stackoverflow.com/questions/3836621/split-a-java-collection-into-sub-collections-based-on-a-object-property
		// create the thing to store the sub lists
		Map<Poll, Set<Candidate>> pollCandidates = new HashMap<>();
		
		// iterate through your objects
		for(Candidate c : candidates){
			
			// fetch the list for this object's id
			Set<Candidate> temp = pollCandidates.get(c.getPoll());
			
			if(temp == null){
				// if the list is null we haven't seen an
				// object with this id before, so create
				// a new list
				temp = new HashSet<>();
				
				// and add it to the map
				pollCandidates.put(c.getPoll(), temp);
			}
			
			// whether we got the list from the map
			// or made a new one we need to add our
			// object.
			temp.add(c);
		}
		
		return pollCandidates;
	}
}
