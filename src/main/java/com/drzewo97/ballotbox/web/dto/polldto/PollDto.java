package com.drzewo97.ballotbox.web.dto.polldto;

import com.drzewo97.ballotbox.model.choice.Choice;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @see com.drzewo97.ballotbox.model.poll.Poll
 */
public class PollDto {
    private String name;

    private String description;

    //TODO: choiceDao/String?
    private Set<Choice> choices;

    private Integer choicesCount;

    /**
     * Just to simplify, and not deal with enum in template form
     * if true - VotingMode.EXACTLY, false - VotingMode.AT_MOST
     * @see com.drzewo97.ballotbox.model.poll.VotingMode
     */
    private Boolean exactly;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime openFrom;

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
