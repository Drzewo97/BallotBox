package com.drzewo97.ballotbox.web.controller.poll;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.service.pollservice.PollService;
import com.drzewo97.ballotbox.core.service.userservice.UserService;
import com.drzewo97.ballotbox.core.dto.votedto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for controlling poll voting form
 */
@Controller
@RequestMapping(path = "/polls/{id}/vote")
public class PollVoteController {

    @Autowired
    private PollService pollService;

    @Autowired
    private UserService userService;

    @GetMapping
    private String showPollVote(@PathVariable Long id, Model model){
        // poll of id passed in parameter
        Optional<Poll> poll = pollService.findById(id);

        //Get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Redirect if user cannot vote on this pole
        String redirection;
        if((redirection = validate(poll, currentPrincipalName)) != null){
            return redirection;
        }

        VoteDto vote = new VoteDto(poll.get());

        model.addAttribute("poll", poll.get());
        model.addAttribute("vote", vote);

        return "poll_vote";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("vote") VoteDto voteDto,
                                      @PathVariable Long id,
                                      BindingResult result){
        // poll of id passed in parameter
        Optional<Poll> poll = pollService.findById(id);

        //Get username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        // Redirect if user cannot vote on this pole
        String redirection;
        if((redirection = validate(poll, currentPrincipalName)) != null){
            return redirection;
        }

        // Register user votes
        pollService.registerVotes(poll.get(), voteDto);

        // Mark that this user voted
        userService.voted(currentPrincipalName, poll.get());

        return "redirect:/polls?success";
    }

    /**
     * Check if user can vote on this poll
     * @param poll poll that the user wants to vote in
     * @param username name of the user
     * @return null if user can vote, string with redirection link otherwise
     */
    private String validate(Optional<Poll> poll, String username){
        // If no such poll
        if(poll.isEmpty()){
            return "redirect:/polls";
        }

        // if user already voted in this poll
        if(poll.get().hasVoted(username)){
            return "redirect:/polls?voted";
        }

        return null;
    }
}
