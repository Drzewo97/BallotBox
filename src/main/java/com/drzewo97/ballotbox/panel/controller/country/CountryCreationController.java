package com.drzewo97.ballotbox.panel.controller.country;

import com.drzewo97.ballotbox.core.model.country.Country;
import com.drzewo97.ballotbox.core.model.country.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/panel/country/create")
public class CountryCreationController {
	
	@Autowired
	private CountryRepository countryRepository;
	
	@ModelAttribute("country")
	private Country country(){
		return new Country();
	}
	
	@GetMapping
	private String showCountryCreate(Model model){
		return "panel/country_create";
	}
	
	@PostMapping
	private String createCountry(@ModelAttribute("country") @Valid Country country, BindingResult result){
		if(countryRepository.existsByName(country.getName())){
			result.rejectValue("name", "name.exist", "Country already exists.");
		}
		
		if(result.hasErrors()){
			return "panel/country_create";
		}
		
		countryRepository.save(country);
		
		return "redirect:create?success";
	}
}
