package com.drzewo97.ballotbox.core.service.pollservice;

import com.drzewo97.ballotbox.core.model.candidate.Candidate;
import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.model.poll.PollRepository;
import com.drzewo97.ballotbox.core.model.poll.VotingMode;
import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.model.vote.Vote;
import com.drzewo97.ballotbox.core.model.vote.VoteRepository;
import com.drzewo97.ballotbox.core.service.userservice.UserService;
import com.drzewo97.ballotbox.core.dto.candidatedto.CandidateDto;
import com.drzewo97.ballotbox.core.dto.polldto.PollDto;
import com.drzewo97.ballotbox.core.dto.votedto.VoteDto;
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
        Set<Candidate> candidates = new HashSet<>();

        // Pick those choices from poll.choices that match chosen from voteDto.choices
        //TODO: refactor choices in vote to list
        for(CandidateDto c : voteDto.getCandidates()){
            if(c.getChosen()){
                for(Candidate ch : poll.getCandidates()){
                    if(c.getName().equals(ch.getName())) {
                        candidates.add(ch);
                        break;
                    }
                }
            }
        }

        // Construct new vote
        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setCandidate(candidates);
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
        poll.setName(pollDto.getName());
        poll.setDescription(pollDto.getDescription());
        poll.setOpenFrom(pollDto.getOpenFrom());
        poll.setOpenUntil(pollDto.getOpenUntil());
        poll.setCandidatesCount(pollDto.getCandidatesCount());
        // Just to simplify, and not deal with enum in template form
        if(pollDto.getExactly()){
            poll.setVotingMode(VotingMode.EXACTLY);
        }
        else{
            poll.setVotingMode(VotingMode.AT_MOST);
        }

        // username from security context - caller (PollCreateController) requires USER role, so it has to be authenticated previously
        // unless something happens between caller receiving request and program reaches this point
        poll.setCreator(user.get());
        user.get().appendPollsCreated(poll);

        pollRepository.save(poll);
    }

    @Override
    public Boolean existsByName(String name) {
        return pollRepository.existsByName(name);
    }
}
