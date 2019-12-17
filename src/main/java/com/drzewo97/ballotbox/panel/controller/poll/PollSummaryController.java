package com.drzewo97.ballotbox.panel.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/panel/poll/{id}")
public class PollSummaryController {
	
	@Autowired
	private PollRepository pollRepository;
	
	@GetMapping
	public String pollSummary(Model model, @PathVariable("id") Long id){
		Optional<Poll> poll = pollRepository.findById(id);
		if(poll.isEmpty()){
			return "redirect:/poll/all?empty";
		}
		
		model.addAttribute("poll", poll.get());
		
		return "panel/poll_summary";
	}
}
