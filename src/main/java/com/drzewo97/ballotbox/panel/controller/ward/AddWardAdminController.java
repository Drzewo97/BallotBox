package com.drzewo97.ballotbox.panel.controller.ward;

import com.drzewo97.ballotbox.core.model.role.Role;
import com.drzewo97.ballotbox.core.model.role.RoleRepository;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.panel.dto.WardAdminDto;
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
@RequestMapping(path = "/panel/ward/admin/add")
public class AddWardAdminController {
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@ModelAttribute("admin")
	private WardAdminDto admin(){
		return new WardAdminDto();
	}
	
	@GetMapping
	private String showWardAdminAdd(Model model){
		model.addAttribute("wards", wardRepository.findAllByWardAdminIsNull());
		model.addAttribute("users", userRepository.findAll());
		
		return "panel/ward_add_admin";
	}
	
	@PostMapping
	private String createWard(@ModelAttribute("admin") WardAdminDto wardAdminDto, BindingResult result){
		// Add ward admin role
		Optional<Role> wardAdminRole = roleRepository.findByName("ROLE_WARDADMIN");
		if(wardAdminRole.isEmpty()){
			throw new RuntimeException();
		}
		if(!wardAdminDto.getUser().getRoles().contains(wardAdminRole.get())){
			wardAdminDto.getUser().getRoles().add(wardAdminRole.get());
			userRepository.save(wardAdminDto.getUser());
		}
		
		wardAdminDto.getWard().setWardAdmin(wardAdminDto.getUser());
		wardRepository.save(wardAdminDto.getWard());
		
		return "redirect:add?success";
	}
}
