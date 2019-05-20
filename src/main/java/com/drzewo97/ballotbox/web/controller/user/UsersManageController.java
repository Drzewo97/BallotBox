package com.drzewo97.ballotbox.web.controller.user;

import com.drzewo97.ballotbox.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/manage/users")
public class UsersManageController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showRegistrationForm(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "manage_users";
    }

    @GetMapping
    @RequestMapping(path = "/toggle-moderator/{userId}")
    public String toggleModerator(@PathVariable Long userId, Model model){
        userService.toggleModeratorRole(userId);
        return "redirect:/manage/users";
    }
}