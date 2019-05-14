package com.drzewo97.ballotbox.web.controller;

import com.drzewo97.ballotbox.model.user.User;
import com.drzewo97.ballotbox.model.user.UserRepository;
import com.drzewo97.ballotbox.service.userservice.UserService;
import com.drzewo97.ballotbox.web.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping(path = "/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserDto userDto(){
        return new UserDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model){
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
                                      BindingResult result){
        Optional<User> registered = userService.findByUsername(userDto.getUsername());

        if(registered.isPresent()){
            result.rejectValue("username", "username.exist", "User already exists.");
        }
        if(result.hasErrors()){
            return "registration";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }
}
