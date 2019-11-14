package com.drzewo97.ballotbox.panel.controller.district;

import com.drzewo97.ballotbox.core.model.country.CountryRepository;
import com.drzewo97.ballotbox.core.model.district.District;
import com.drzewo97.ballotbox.core.model.district.DistrictRepository;
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
@RequestMapping(path = "/panel/district/create")
public class DistrictCreationController {
	
	@Autowired
	private DistrictRepository districtRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@ModelAttribute("district")
	private District district() {
		return new District();
	}
	
	@GetMapping
	private String showDistrictCreate(Model model){
		model.addAttribute("countries", StreamSupport.stream(countryRepository.findAll().spliterator(), false).collect(Collectors.toList()));
		return "panel/district_create";
	}
	
	@PostMapping
	private String createDistrict(@ModelAttribute("district") District district, BindingResult result){
		if(districtRepository.existsByNameAndCountry_Name(district.getName(), district.getCountry().getName())){
			result.rejectValue("name", "name.exist", "District already exists.");
		}
		
		if(result.hasErrors()){
			return "panel/district_create";
		}
		
		districtRepository.save(district);
		
		return "redirect:create?success";
	}
}
