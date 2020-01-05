package com.drzewo97.ballotbox.wardpanel.controller.root;

import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/wardpanel")
public class WardSelectionController {
	
	@Autowired
	private WardRepository wardRepository;
	
	@GetMapping
	private String showWardAdminAdd(Model model){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		Set<Ward> adminsWards = wardRepository.findAllByWardAdminUsername(currentPrincipalName);
		if(adminsWards.isEmpty()){
			return "redirect:/";
		}
		
		model.addAttribute("adminsWards", adminsWards);
		
		return "wardpanel/wardchoice";
	}
}
