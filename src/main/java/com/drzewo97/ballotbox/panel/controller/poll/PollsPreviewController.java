package com.drzewo97.ballotbox.panel.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/panel/poll/all")
public class PollsPreviewController {
	
	@Autowired
	private PollRepository pollRepository;
	
	@GetMapping
	public String showAllPolls(Model model){
		model.addAttribute("polls", pollRepository.findAll());
		
		return "panel/poll_all_show";
	}
}
