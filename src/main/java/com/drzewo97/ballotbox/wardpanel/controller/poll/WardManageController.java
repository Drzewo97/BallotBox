package com.drzewo97.ballotbox.wardpanel.controller.poll;

import com.drzewo97.ballotbox.core.service.ward.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wardpanel/{wardId}")
public class WardManageController {
	
	@Autowired
	private WardService wardService;
	
	@GetMapping
	private String showWardManage(@PathVariable("wardId") Long wardId){
		//Get username
		String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
		if(!wardService.isWardAdmin(currentPrincipalName, wardId)){
			return "redirect:/";
		}
		
		return "wardpanel/wardpanel";
	}
}
