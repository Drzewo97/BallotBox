package com.drzewo97.ballotbox.panel.controller.committee;

import com.drzewo97.ballotbox.core.model.role.Role;
import com.drzewo97.ballotbox.core.model.role.RoleRepository;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import com.drzewo97.ballotbox.core.model.committee.CommitteeRepository;
import com.drzewo97.ballotbox.panel.dto.CommitteeAdminDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/panel/committee/admin/add")
public class AddCommitteeAdminController {
	
	@Autowired
	private CommitteeRepository committeeRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@ModelAttribute("admin")
	private CommitteeAdminDto admin(){
		return new CommitteeAdminDto();
	}
	
	@GetMapping
	private String showCommitteeAdminAdd(Model model){
		model.addAttribute("committees", committeeRepository.findAllByCommitteeAdminIsNull());
		model.addAttribute("users", userRepository.findAll());
		
		return "panel/committee_add_admin";
	}
	
	@PostMapping
	private String createCommittee(@ModelAttribute("admin") CommitteeAdminDto committeeAdminDto, BindingResult result){
		// Add committee admin role
		Optional<Role> committeeAdminRole = roleRepository.findByName("ROLE_COMMITTEEADMIN");
		if(committeeAdminRole.isEmpty()){
			throw new RuntimeException();
		}
		if(!committeeAdminDto.getUser().getRoles().contains(committeeAdminRole.get())){
			committeeAdminDto.getUser().getRoles().add(committeeAdminRole.get());
			userRepository.save(committeeAdminDto.getUser());
		}
		
		committeeAdminDto.getCommittee().setCommitteeAdmin(committeeAdminDto.getUser());
		committeeRepository.save(committeeAdminDto.getCommittee());
		
		return "redirect:add?success";
	}
}
