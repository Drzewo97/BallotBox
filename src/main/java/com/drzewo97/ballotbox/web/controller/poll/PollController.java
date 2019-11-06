package com.drzewo97.ballotbox.web.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping
    public String showPolls(WebRequest request, Model model) {
        // Get all polls from datasource
        List<Poll> polls = new ArrayList<>();
        pollRepository.findAll().forEach(polls::add);

        // Add all polls as attribute
        model.addAttribute("polls", polls);

        // Return view
        return "show_polls";
    }
}
