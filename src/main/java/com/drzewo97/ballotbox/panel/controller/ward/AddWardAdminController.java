package com.drzewo97.ballotbox.panel.controller.ward;

import com.drzewo97.ballotbox.core.model.role.Role;
import com.drzewo97.ballotbox.core.model.role.RoleRepository;
import com.drzewo97.ballotbox.core.model.user.UserRepository;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import com.drzewo97.ballotbox.core.model.wardadmin.WardAdmin;
import com.drzewo97.ballotbox.core.model.wardadmin.WardAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping(path = "/panel/ward/admin/add")
public class AddWardAdminController {
	
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private WardAdminRepository wardAdminRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@ModelAttribute("ward")
	private Ward ward(){
		return new Ward();
	}
	
	@ModelAttribute("admin")
	private WardAdmin admin(){
		return new WardAdmin();
	}
	
	@GetMapping
	private String showWardAdminAdd(Model model){
		model.addAttribute("wards", wardRepository.findAllByWardAdminIsNull());
		model.addAttribute("users", userRepository.findAll());
		
		return "panel/ward_add_admin";
	}
	
	@PostMapping
	private String createWard(@ModelAttribute("admin") WardAdmin admin, BindingResult result){
		// Add ward admin role
		Set<Role> userRoles = admin.getUser().getRoles();
		Optional<Role> wardAdminRole = roleRepository.findByName("ROLE_WARDADMIN");
		if(wardAdminRole.isEmpty()){
			throw new RuntimeException();
		}
		userRoles.add(wardAdminRole.get());
		admin.getUser().setRoles(userRoles);
		userRepository.save(admin.getUser());
		
		wardAdminRepository.save(admin);
		
		admin.getWard().setWardAdmin(admin);
		wardRepository.save(admin.getWard());
		
		return "redirect:add?success";
	}
}
