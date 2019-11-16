package com.drzewo97.ballotbox.web.controller.candidate;

import com.drzewo97.ballotbox.core.service.candidateservice.CandidateService;
import com.drzewo97.ballotbox.core.dto.candidatedto.CandidateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/manage/candidates/create")
public class CandidateCreateController {

    @Autowired
    private CandidateService candidateService;


    @ModelAttribute("candidate")
    public CandidateDto candidateDto(){
        return new CandidateDto();
    }

    @GetMapping
    private String showCandidateCreate(Model model){
        return "candidate_create";
    }

    //TODO: choiceDto validation (now it's case sensitive, "choice" and "ChOiCe" is different, maybe warning when exists same?)
    @PostMapping
    private String createCandidate(@ModelAttribute("candidate") CandidateDto candidateDto, BindingResult result){

        if(candidateService.existsByName(candidateDto.getName())){
            result.rejectValue("name", "name.exists", "Candidate already exists.");
        }

        if(result.hasErrors()){
            return "candidate_create";
        }

        candidateService.save(candidateDto);
        return "redirect:create?success";
    }
}
