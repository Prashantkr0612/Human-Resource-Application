package com.cg.humanresource.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.humanresource.dto.CountriesDTO;
import com.cg.humanresource.entity.Countries;
import com.cg.humanresource.entity.Regions;
import com.cg.humanresource.exception.CountryNotFoundException;
import com.cg.humanresource.service.CountriesService;
import com.cg.humanresource.service.RegionsService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/country")
@Tag(name="Country controller")
public class CountriesController 
{
	@Autowired
	CountriesService countriesService;
 
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<CountriesDTO>> getAllCountries() {
	    List<CountriesDTO> countriesDTOList = countriesService.getAllCountries();
	    return ResponseEntity.ok(countriesDTOList);
	}

	@GetMapping(value = "/{countryId}", produces = "application/json")
	public ResponseEntity<CountriesDTO> getCountriesById(@PathVariable String countryId) {
	    CountriesDTO countriesDTO = countriesService.getCountriesById(countryId);
	    return ResponseEntity.ok(countriesDTO);
	}
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> insertCountries(@RequestBody @Valid  Countries countries)
	{
		countriesService.addOrModifyCountries(countries);
		return new ResponseEntity<String>("Record created successfully",HttpStatus.CREATED);
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<String> modifyCountries(@RequestBody @Valid Countries countries) 
	{
		countriesService.addOrModifyCountries(countries);
		return new ResponseEntity<String>("Record created successfully",HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{countryId}")
	public ResponseEntity<String> deleteCountriesById(@PathVariable String countryId) 
	{
	    boolean isSuccess = countriesService.deleteCountriesById(countryId);
	    if (isSuccess) 
	    {
	        return new ResponseEntity<>("Record Deleted Successfully", HttpStatus.GONE);
	    } 
	    else 
	    {
	  
	        throw new CountryNotFoundException("Deletion failed");
	    }
	}

}

