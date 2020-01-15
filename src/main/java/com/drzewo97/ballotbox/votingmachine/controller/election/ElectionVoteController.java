package com.drzewo97.ballotbox.votingmachine.controller.election;

import com.drzewo97.ballotbox.core.dto.electionvote.ElectionVoteDto;
import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.candidate.CandidateRepository;
import com.drzewo97.ballotbox.core.model.election.Election;
import com.drzewo97.ballotbox.core.model.election.ElectionRepository;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.VotingMode;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showElection(@PathVariable("id") Integer id,
	                           Model model,
	                           RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		Optional<Election> election = electionRepository.findById(id);
		if(election.isEmpty()){
			outputMessages.add("No such election!");
			return "redirect:/election/all";
		}
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(currentPrincipalName).get();
		if(election.get().getPolls().stream().anyMatch(p -> user.getPollsVoted().contains(p))){
			outputMessages.add("You have already voted on this election!");
			return "redirect:/election/all";
		}
		// TODO: choose polls eligible for user
		
		model.addAttribute("election", election.get());
		model.addAttribute("electionVote", new ElectionVoteDto());
		
		// Return view
		return "votingmachine/election_vote";
	}
	
	@PostMapping
	public String registerVote(@PathVariable("id") Integer id,
	                           @ModelAttribute("electionVote") ElectionVoteDto electionVoteDto,
	                           RedirectAttributes redirectAttributes)
	{
		List<String> outputMessages = new ArrayList<>();
		redirectAttributes.addFlashAttribute("messages", outputMessages);
		
		Optional<Election> election = electionRepository.findById(id);
		if(election.isEmpty()){
			outputMessages.add("No such election!");
			return "redirect:/election/all";
		}
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userRepository.findByUsername(currentPrincipalName).get();
		if(election.get().getPolls().stream().anyMatch(p -> user.getPollsVoted().contains(p))){
			outputMessages.add("You have already voted on this election!");
			return "redirect:/election/all";
		}
		// TODO: can user vote in those polls? country, district, ward
		
		// divide candidate list into polls
		// keep only marked candidates
		Map<Poll, Set<Map.Entry<Candidate, VoteDto>>> voterPollsChoices = groupCandidatesToPolls(electionVoteDto.getCandidateIdPreferenceMap());
		
		for(Map.Entry<Poll, Set<Map.Entry<Candidate, VoteDto>>> voterPollChoices : voterPollsChoices.entrySet()){
			Poll poll = voterPollChoices.getKey();
			Set<Map.Entry<Candidate, VoteDto>> votes = voterPollChoices.getValue();
			
			// validate
			if(!poll.isChoicesCountSuitable(votes.size())){
				// user marked more than avaliable number of candidates
				outputMessages.add("Incorrect number of choices marked for poll " + poll.getName() + " - vote is not valid and will not be taken into account.");
				continue;
			}
			if(voterPollChoices.getKey().isPreferenceVoting()){
				if(!checkPreferenceVotes(poll.getCandidatesCount(), poll.getVotingMode(), votes.stream().map(e -> e.getValue()).collect(Collectors.toSet()))){
					// preferences of candidates not set properly
					outputMessages.add("Poll " + voterPollChoices.getKey().getName() + " has invalid preference numbers. Vote is not valid and will not be taken into account.");
					continue;
				}
			}
			
			outputMessages.add("Poll " + voterPollChoices.getKey().getName() + " has correctly registered Your vote.");
			
			voteRepository.save(converter.convert(votes));
		}
		
		// check all polls that user left unmarked
		Set<Poll> pollsVoted = voterPollsChoices.entrySet().stream().map(e -> e.getKey()).collect(Collectors.toSet());
		
		for(Poll poll : election.get().getPolls()){
			user.getPollsVoted().add(poll);
			if(!pollsVoted.contains(poll)){
				outputMessages.add("No vote casted for poll " + poll.getName() + ".");
			}
		}
		userRepository.save(user);
		
		return "redirect:/election/all";
	}
	
	/**
	 * Group marked candidates to polls - it will discard not marked candidates, as well as polls left blank
	 * @param candidates
	 * @return
	 */
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
				if(entry.getValue().getPreferenceNumber() != null && entry.getValue().getPreferenceNumber() != 0){
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
	
	/**
	 *
	 * @param votes
	 * @return true if votes are ok, false if there is an error and vote should be invalidated
	 */
	private Boolean checkPreferenceVotes(Integer candidatesCount, VotingMode votingMode, Set<VoteDto> votes){
		Boolean negativeValues = votes.stream().anyMatch(v -> v.getPreferenceNumber() < 0);
		if(negativeValues){
			// user marked negative values
			return false;
		}
		
		// repeating values
		// at this point should be no preference numbers = 0
		if(!votes.stream().mapToInt(v -> v.getPreferenceNumber()).filter(n -> n != 0).allMatch(new HashSet<>()::add)){
			// user repeated some preference number
			return false;
		}
		
		return true;
	}
}
