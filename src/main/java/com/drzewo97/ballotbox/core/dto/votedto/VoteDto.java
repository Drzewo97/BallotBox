package com.drzewo97.ballotbox.core.dto.votedto;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.dto.candidatedto.CandidateDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class VoteDto {
    //TODO: DAO
    @NotNull
    private Poll poll;

    @NotNull
    private List<CandidateDto> candidates;

    public VoteDto() {
    }

    public VoteDto(@NotNull Poll poll) {
        this.poll = poll;
        this.candidates = poll.getCandidates().stream().map(c -> new CandidateDto(c.getName(), false)).collect(Collectors.toList());
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public List<CandidateDto> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDto> candidates) {
        this.candidates = candidates;
    }
}
