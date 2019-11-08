package com.drzewo97.ballotbox.web.controller.user;

import com.drzewo97.ballotbox.core.model.user.User;
import com.drzewo97.ballotbox.core.service.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/manage/users/{id}")
public class UserManageController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showUserManage(@PathVariable Long id, Model model){
        Optional<User> user = userService.findById(id);

        if(user.isEmpty()){
            return "redirect:/manage/users";
        }

        model.addAttribute("user", user.get());

        return "manage_user";
    }

    @GetMapping
    @RequestMapping(path = "/toggle-moderator")
    public String toggleModerator(@PathVariable Long id, Model model){
        if(!userService.existsById(id)){
            return "redirect:/manage/users";
        }

        userService.toggleModeratorRole(id);
        return "redirect:/manage/users/" + id;
    }
}
