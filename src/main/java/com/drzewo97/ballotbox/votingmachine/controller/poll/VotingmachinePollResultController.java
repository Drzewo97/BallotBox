package com.drzewo97.ballotbox.votingmachine.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.pollresult.PollResult;
import com.drzewo97.ballotbox.core.model.pollresult.PollResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/poll/{id}/result")
public class VotingmachinePollResultController {
	
	@Autowired
	private PollRepository pollRepository;
	
	@Autowired
	private PollResultRepository pollResultRepository;
	
	private final String ERROR_LANDING_PAGE = "redirect:/poll/all";
	
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
			outputMessages.add("Results are not calculated yet.");
			return ERROR_LANDING_PAGE;
		}
		
		model.addAttribute("poll", poll.get());
		
		return "panel/poll_result";
	}
}
