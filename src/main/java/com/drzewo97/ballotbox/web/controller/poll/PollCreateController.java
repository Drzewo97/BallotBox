package com.drzewo97.ballotbox.web.controller.poll;

import com.drzewo97.ballotbox.service.choiceservice.ChoiceService;
import com.drzewo97.ballotbox.service.pollservice.PollService;
import com.drzewo97.ballotbox.web.dto.polldto.PollDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/polls/create")
public class PollCreateController {

    @Autowired
    private PollService pollService;

    @Autowired
    private ChoiceService choiceService;

    @ModelAttribute("poll")
    private PollDto pollDto(){
        return new PollDto();
    }

    @GetMapping
    private String showPollCreate(Model model){
        model.addAttribute("allChoices", choiceService.findAllChoices());
        return "poll_create";
    }

    @PostMapping
    private String createPoll(@ModelAttribute("poll") PollDto pollDto, BindingResult result){
        //TODO: validation: doesn't exist, from < until
        if(result.hasErrors()){
            return "poll_create";
        }

        //Get username
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();

        pollService.save(pollDto, currentPrincipalName);
        return "redirect:/polls/create?success";
    }
}
