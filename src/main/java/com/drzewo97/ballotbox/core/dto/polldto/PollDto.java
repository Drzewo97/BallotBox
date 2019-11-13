package com.drzewo97.ballotbox.core.dto.polldto;

import com.drzewo97.ballotbox.core.constraint.collectionsize.CollectionSize;
import com.drzewo97.ballotbox.core.constraint.datesorder.DatesOrder;
import com.drzewo97.ballotbox.core.model.choice.Choice;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @see com.drzewo97.ballotbox.core.model.poll.Poll
 */
@DatesOrder(before = "openFrom", after = "openUntil", message = "From must be before Until")
@CollectionSize(size = "choicesCount", collection = "choices")
public class PollDto {
    @NotEmpty
    private String name;

    private String description;

    //TODO: choiceDao/String?
    @NotEmpty
    private Set<Choice> choices;

    @NotNull
    @Positive
    private Integer choicesCount;

    /**
     * Just to simplify, and not deal with enum in template form
     * if true - VotingMode.EXACTLY, false - VotingMode.AT_MOST
     * @see com.drzewo97.ballotbox.core.model.poll.VotingMode
     */
    @NotNull
    private Boolean exactly;

    @NotNull
    @Future
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openFrom;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openUntil;

    public PollDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Choice> getChoices() {
        return choices;
    }

    public void setChoices(Set<Choice> choices) {
        this.choices = choices;
    }

    public Integer getChoicesCount() {
        return choicesCount;
    }

    public void setChoicesCount(Integer choicesCount) {
        this.choicesCount = choicesCount;
    }

    public Boolean getExactly() {
        return exactly;
    }

    public void setExactly(Boolean exactly) {
        this.exactly = exactly;
    }

    public LocalDateTime getOpenFrom() {
        return openFrom;
    }

    public void setOpenFrom(LocalDateTime openFrom) {
        this.openFrom = openFrom;
    }

    public LocalDateTime getOpenUntil() {
        return openUntil;
    }

    public void setOpenUntil(LocalDateTime openUntil) {
        this.openUntil = openUntil;
    }
}
