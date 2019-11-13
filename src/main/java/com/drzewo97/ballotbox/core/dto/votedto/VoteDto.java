package com.drzewo97.ballotbox.core.dto.votedto;

import com.drzewo97.ballotbox.core.model.poll.Poll;
import com.drzewo97.ballotbox.core.dto.choicedto.ChoiceDto;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class VoteDto {
    //TODO: DAO
    @NotNull
    private Poll poll;

    @NotNull
    private List<ChoiceDto> choices;

    public VoteDto() {
    }

    public VoteDto(@NotNull Poll poll) {
        this.poll = poll;
        this.choices = poll.getChoices().stream().map(c -> new ChoiceDto(c.getName(), false)).collect(Collectors.toList());
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public List<ChoiceDto> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDto> choices) {
        this.choices = choices;
    }
}
