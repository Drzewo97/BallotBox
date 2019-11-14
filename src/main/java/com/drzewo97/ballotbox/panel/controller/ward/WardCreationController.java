package com.drzewo97.ballotbox.panel.controller.ward;

import com.drzewo97.ballotbox.core.model.district.DistrictRepository;
import com.drzewo97.ballotbox.core.model.ward.Ward;
import com.drzewo97.ballotbox.core.model.ward.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
@RequestMapping(path = "/panel/ward/create")
public class WardCreationController {
	@Autowired
	private WardRepository wardRepository;
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@ModelAttribute("ward")
	private Ward ward() {
		return new Ward();
	}
	
	@GetMapping
	private String showWardCreate(Model model){
		model.addAttribute("districts", StreamSupport.stream(districtRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return "panel/ward_create";
	}
	
	@PostMapping
	private String createWard(@ModelAttribute("ward") Ward ward, BindingResult result){
		if(wardRepository.existsByNameAndDistrict_Name(ward.getName(), ward.getDistrict().getName())){
			result.rejectValue("name", "name.exist", "Ward already exists.");
		}
		
		if(result.hasErrors()){
			return "panel/ward_create";
		}
		
		wardRepository.save(ward);
		
		return "redirect:create?success";
	}
}
