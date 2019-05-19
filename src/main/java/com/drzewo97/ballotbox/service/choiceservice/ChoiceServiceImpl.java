package com.drzewo97.ballotbox.service.choiceservice;

import com.drzewo97.ballotbox.model.choice.Choice;
import com.drzewo97.ballotbox.model.choice.ChoiceRepository;
import com.drzewo97.ballotbox.web.dto.choicedto.ChoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChoiceServiceImpl implements ChoiceService {

    @Autowired
    private ChoiceRepository choiceRepository;

    @Override
    public Boolean existsByName(String name) {
        return choiceRepository.existsByName(name);
    }

    @Override
    public void save(ChoiceDto choiceDto) {
        Choice choice = new Choice();
        choice.setName(choiceDto.getName());

        choiceRepository.save(choice);
    }

    @Override
    public List<Choice> findAllChoices() {
        List<Choice> choices = new ArrayList<>();
        choiceRepository.findAll().forEach(choices::add);

        return choices;
    }
}
