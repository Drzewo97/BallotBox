package com.drzewo97.ballotbox.service.pollservice;

import com.drzewo97.ballotbox.model.choice.Choice;
import com.drzewo97.ballotbox.model.poll.Poll;
import com.drzewo97.ballotbox.model.poll.PollRepository;
import com.drzewo97.ballotbox.model.poll.VotingMode;
import com.drzewo97.ballotbox.model.user.User;
import com.drzewo97.ballotbox.model.vote.Vote;
import com.drzewo97.ballotbox.model.vote.VoteRepository;
import com.drzewo97.ballotbox.service.userservice.UserService;
import com.drzewo97.ballotbox.web.dto.choicedto.ChoiceDto;
import com.drzewo97.ballotbox.web.dto.polldto.PollDto;
import com.drzewo97.ballotbox.web.dto.votedto.VoteDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class PollServiceImpl implements PollService {

    @Autowired
    private UserService userService;

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public Boolean hasVoted(String username, Long pollId) {
        // find user
        Optional<User> user = userService.findByUsername(username);

        if(user.isPresent()){
            for(Poll poll : user.get().getPollsVoted()){
                // Check if there is a poll of this id in user's pollsVoted
                if(poll.getId().equals(pollId))
                    return true;
            }
        }

        return false;
    }

    @Override
    public Optional<Poll> findById(Long id) {
        return pollRepository.findById(id);
    }

    @Override
    public void registerVotes(Poll poll, VoteDto voteDto) {
        Set<Choice> choices = new HashSet<>();

        // Pick those choices from poll.choices that match chosen from voteDto.choices
        //TODO: refactor choices in vote to list
        for(ChoiceDto c : voteDto.getChoices()){
            if(c.getChosen()){
                for(Choice ch : poll.getChoices()){
                    if(c.getName().equals(ch.getName())) {
                        choices.add(ch);
                        break;
                    }
                }
            }
        }

        // Construct new vote
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setChoice(choices);
        voteRepository.save(vote);

        // TODO: Probably not the best practice
        poll.appendVote(vote);
        pollRepository.save(poll);
    }

    @Override
    public void save(PollDto pollDto, String creatorUsername) {
        // get user based on name of creatorUsername
        Optional<User> user = userService.findByUsername(creatorUsername);

        // Set all fields
        Poll poll = new Poll();
        // username from security context - caller (PollCreateController) requires USER role, so it has to be authenticated previously
        // unless something happens between caller receiving request and program reaches this point
        poll.setCreator(user.get());
        poll.setName(pollDto.getName());
        poll.setDescription(pollDto.getDescription());
        poll.setChoices(pollDto.getChoices());
        poll.setOpenFrom(pollDto.getOpenFrom());
        poll.setOpenUntil(pollDto.getOpenUntil());
        poll.setChoicesCount(pollDto.getChoicesCount());

        // Just to simplify, and not deal with enum in template form
        if(pollDto.getExactly()){
            poll.setVotingMode(VotingMode.EXACTLY);
        }
        else{
            poll.setVotingMode(VotingMode.AT_MOST);
        }

        pollRepository.save(poll);
    }
}
