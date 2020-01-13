package com.drzewo97.ballotbox.votingmachine.controller.election;

import com.drzewo97.ballotbox.core.dto.electionvote.ElectionVoteDto;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.model.election.Election;
import com.drzewo97.ballotbox.core.model.election.ElectionRepository;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.vote.VoteRepository;
import com.drzewo97.ballotbox.core.service.converter.Converter;
import com.drzewo97.ballotbox.votingmachine.dto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/election/{id}/vote")
public class ElectionVoteController {
	
	@Autowired
	private ElectionRepository electionRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private CandidateRepository candidateRepository;
	
	@Autowired
	private Converter<Set<Map.Entry<Candidate, VoteDto>>, Vote> converter;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public String showElections(@PathVariable("id") Integer id, WebRequest request, Model model) {
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
	public String registerVote(@PathVariable("id") Integer id, @ModelAttribute("electionVote") ElectionVoteDto electionVoteDto, BindingResult result){
		Optional<Election> election = electionRepository.findById(id);
		if(election.isEmpty()){
			return "redirect:/election/all?empty";
		}
		if(result.hasErrors()){
			return "panel/district_create";
		}
		
		// divide candidate list into polls
		Map<Poll, Set<Map.Entry<Candidate, VoteDto>>> pollCandidates = groupCandidatesToPolls(electionVoteDto.getCandidateIdPreferenceMap());
		
		for(Set<Map.Entry<Candidate, VoteDto>> candidates : pollCandidates.values()){
			voteRepository.save(converter.convert(candidates));
		}
		
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(currentPrincipalName).get();
		for(Poll poll:election.get().getPolls()){
			user.getPollsVoted().add(poll);
		}
		userRepository.save(user);
		
		return "redirect:/election/all?success";
	}
	
	
	private Map<Poll, Set<Map.Entry<Candidate, VoteDto>>> groupCandidatesToPolls(Map<Integer, VoteDto> candidates){
		// fetch candidate by id in map key
		Map<Candidate, VoteDto> candidateVoteDtoMap = candidates.entrySet().stream()
				.collect(Collectors.toMap(e -> candidateRepository.findById(e.getKey()).get(), Map.Entry::getValue));
		
		// sieve not marked candidates
		Map<Candidate, VoteDto> sievedCandidateVoteMap = new HashMap<>();
		for(Map.Entry<Candidate, VoteDto> entry : candidateVoteDtoMap.entrySet()){
			if(entry.getValue().getChecked() != null && entry.getValue().getPreferenceNumber() != null){
				//TODO: different browsers may set default values? Shouldn't
				throw new RuntimeException("Someone was playing with form attributes -> ban and discard vote.");
			}
			
			if(entry.getKey().getPoll().isPreferenceVoting()){
				if(entry.getValue().getPreferenceNumber() != 0){
					sievedCandidateVoteMap.put(entry.getKey(), entry.getValue());
				}
			}
			else{
				// checkbox voting
				if(entry.getValue().getChecked()){
					sievedCandidateVoteMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		
		// create map of poll and every vote that counts
		Map<Poll, Set<Map.Entry<Candidate, VoteDto>>> pollCandidates = new HashMap<>();
		for(Map.Entry<Candidate, VoteDto> e : sievedCandidateVoteMap.entrySet()){
			Set<Map.Entry<Candidate, VoteDto>> temp = pollCandidates.get(e.getKey().getPoll());

			if(temp == null){
				temp = new HashSet<>();
				pollCandidates.put(e.getKey().getPoll(), temp);
			}

			temp.add(e);
		}
		
		return pollCandidates;
	}
}
