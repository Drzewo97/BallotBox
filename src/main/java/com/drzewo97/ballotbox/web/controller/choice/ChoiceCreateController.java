package com.drzewo97.ballotbox.web.controller.choice;

import com.drzewo97.ballotbox.service.choiceservice.ChoiceService;
import com.drzewo97.ballotbox.web.dto.choicedto.ChoiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/manage/choices/create")
public class ChoiceCreateController {

    @Autowired
    private ChoiceService choiceService;


    @ModelAttribute("choice")
    public ChoiceDto choiceDto(){
        return new ChoiceDto();
    }

    @GetMapping
    private String showChoiceCreate(Model model){
        return "choice_create";
    }

    //TODO: choiceDto validation (now it's case sensitive, "choice" and "ChOiCe" is different, maybe warning when exists same?)
    @PostMapping
    private String createChoice(@ModelAttribute("choice") ChoiceDto choiceDto, BindingResult result){

        if(choiceService.existsByName(choiceDto.getName())){
            result.rejectValue("name", "name.exists", "Choice already exists.");
        }

        if(result.hasErrors()){
            return "choice_create";
        }

        choiceService.save(choiceDto);
        return "redirect:create?success";
    }
}
