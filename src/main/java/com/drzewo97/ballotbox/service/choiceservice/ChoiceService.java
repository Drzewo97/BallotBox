package com.drzewo97.ballotbox.service.choiceservice;

import com.drzewo97.ballotbox.web.dto.choicedto.ChoiceDto;

public interface ChoiceService {
    Boolean existsByName(String name);
    void save(ChoiceDto choiceDto);
}
