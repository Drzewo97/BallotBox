package com.drzewo97.ballotbox.core.dto.candidatedto;

public class CandidateDto {

    private String name;
    private Boolean chosen;

    public CandidateDto() {
    }

    public CandidateDto(String name, Boolean chosen) {
        this.name = name;
        this.chosen = chosen;
    }

    public CandidateDto(String name) {
        this.name = name;
        this.chosen = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getChosen() {
        return chosen;
    }

    public void setChosen(Boolean chosen) {
        this.chosen = chosen;
    }
}
