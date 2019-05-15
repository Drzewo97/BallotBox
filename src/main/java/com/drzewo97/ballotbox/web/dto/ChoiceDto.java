package com.drzewo97.ballotbox.web.dto;

public class ChoiceDto {

    private String name;
    private Boolean chosen;

    public ChoiceDto() {
    }

    public ChoiceDto(String name, Boolean chosen) {
        this.name = name;
        this.chosen = chosen;
    }

    public ChoiceDto(String name) {
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
