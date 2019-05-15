package com.drzewo97.ballotbox.web.controller.poll;

import com.drzewo97.ballotbox.model.poll.Poll;
import com.drzewo97.ballotbox.service.pollservice.PollService;
import com.drzewo97.ballotbox.service.userservice.UserService;
import com.drzewo97.ballotbox.web.dao.polldao.PollDao;
import com.drzewo97.ballotbox.web.dto.votedto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/polls/{id}/vote")
public class PollVoteController {

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @GetMapping
    private String showPollVote(@PathVariable Long id, Model model){
        // if no poll of such id
        Optional<Poll> poll = pollService.findById(id);
        if(poll.isEmpty()){
            return "redirect:/polls";
        }

        //Get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // if user already voted in this poll
        if(poll.get().hasVoted(currentPrincipalName)){
            return "redirect:/polls?voted=true";
        }

        VoteDto vote = new VoteDto(poll.get());

        model.addAttribute("poll", PollDao.construct(poll.get()));
        model.addAttribute("vote", vote);

        return "poll_vote";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("vote") VoteDto voteDto,
                                      @PathVariable Long id,
                                      BindingResult result){
        //TODO: same validation code
        // if no poll of such id
        Optional<Poll> poll = pollService.findById(id);
        if(poll.isEmpty()){
            return "redirect:/polls";
        }

        //Get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // if user already voted in this poll
        if(poll.get().hasVoted(currentPrincipalName)){
            return "redirect:/polls?voted=true";
        }

        // Register user votes
        pollService.registerVotes(poll.get(), voteDto);

        // Mark that this user voted
        userService.voted(currentPrincipalName, poll.get());

        return "redirect:/polls/" + id + "/vote?success";
    }
}
