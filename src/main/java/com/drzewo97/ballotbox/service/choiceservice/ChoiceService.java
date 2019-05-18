package com.drzewo97.ballotbox.service.choiceservice;

import com.drzewo97.ballotbox.model.choice.Choice;
import com.drzewo97.ballotbox.web.dto.choicedto.ChoiceDto;

import java.util.List;

public interface ChoiceService {
    Boolean existsByName(String name);
    void save(ChoiceDto choiceDto);
    List<Choice> findAllChoices();
}
