package com.cg.humanresource.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.dto.CountriesDTO;
import com.cg.humanresource.entity.Countries;
import com.cg.humanresource.entity.Locations;
import com.cg.humanresource.entity.Regions;
import com.cg.humanresource.exception.CountryNotFoundException;
import com.cg.humanresource.exception.ValidationException;
import com.cg.humanresource.repository.CountriesRepository;
import com.cg.humanresource.repository.RegionsRepository;


@Service
public class CountriesService 
{
	@Autowired
	CountriesRepository countriesRepository;
	
	@Autowired
	RegionsRepository regionsRepository;
	
//	@Transactional(readOnly=true)
//	public List<Countries> getAllCountries() throws ValidationException
//	{
//		List<Countries> rlist =  countriesRepository.findAll();
//		if(rlist.size()!=0)
//			return rlist;
//		throw new ValidationException();
//	}
	
//	@Transactional(readOnly=true) 
//	public Countries getCountriesById(String countryId) throws ValidationException
//	{
//		return countriesRepository.findById(countryId).orElseThrow(() -> new ValidationException());
//	}
//	
	@Transactional
	public Countries addOrModifyCountries(Countries c) throws ValidationException
	{
		Regions region = regionsRepository.findById(c.getRegions().getRegionId())
				.orElseThrow(() -> new ValidationException());
		List<Locations> loc = new ArrayList<>();
		c.setLocations(loc);
		c.setRegions(region);
		return countriesRepository.save(c);
	}
	
	private CountriesDTO convertToDto(Countries country) {
		return new CountriesDTO(
				country.getCountryId(),
				country.getCountryName(),
				country.getRegions().getRegionId(),
				country.getLocations().stream().map(location -> location.getLocationId()).collect(Collectors.toList()) 
				);
	}
	
	@Transactional(readOnly = true)
	public List<CountriesDTO> getAllCountries() throws CountryNotFoundException {
	    List<Countries> countriesList = countriesRepository.findAll();
	    if (countriesList.isEmpty()) {
	        throw new CountryNotFoundException();
	    }
	    
	    return countriesList.stream()
	            .map(this::convertToDto)
	            .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CountriesDTO getCountriesById(String countryId) throws CountryNotFoundException {
	    Countries country = countriesRepository.findById(countryId)
	            .orElseThrow(() -> new CountryNotFoundException());
	            
	    return convertToDto(country);
	}


	
	@Transactional
	public boolean deleteCountriesById(String countryId) throws ValidationException
	{
		long count = countriesRepository.count();
		countriesRepository.deleteById(countryId);
		return countriesRepository.count() < count;
	}
}



