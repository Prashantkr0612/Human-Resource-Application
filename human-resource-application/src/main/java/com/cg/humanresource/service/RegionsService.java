package com.cg.humanresource.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.dto.RegionsDTO;
import com.cg.humanresource.entity.Countries;
import com.cg.humanresource.entity.Regions;
import com.cg.humanresource.exception.RegionNotFoundException;
import com.cg.humanresource.exception.ResourceNotFoundException;
import com.cg.humanresource.repository.RegionsRepository;

@Service
public class RegionsService 
{
	@Autowired
	RegionsRepository regionsRepository;
	
	private RegionsDTO convertEntityToDTO(Regions region) {
		List<String> countryNames = region.getCountries()
				.stream()
				.map(Countries::getCountryName) 
				.collect(Collectors.toList());
		return new RegionsDTO(region.getRegionId(), region.getRegionName(), countryNames);
	}
	
	@Transactional(readOnly = true)
	public List<RegionsDTO> getAllRegions() throws RegionNotFoundException {
	    List<Regions> regionsList = regionsRepository.findAll();
	    if (!regionsList.isEmpty()) {
	        return regionsList.stream()
	                          .map(this::convertEntityToDTO)
	                          .collect(Collectors.toList());
	    }
	    throw new RegionNotFoundException();
	}

	@Transactional(readOnly = true)
	public RegionsDTO getRegionsById(int regionId) throws RegionNotFoundException {
	    Regions region = regionsRepository.findById(regionId)
	                                       .orElseThrow(() -> new RegionNotFoundException());
	    return convertEntityToDTO(region);
	}
	
	@Transactional
	public boolean addOrModifyRegions(Regions r) throws ResourceNotFoundException
	{
	
		return regionsRepository.save(r) != null;
	}
	
	@Transactional
	public boolean deleteRegionsById(int regionId) throws ResourceNotFoundException
	{
		long count = regionsRepository.count();
		regionsRepository.deleteById(regionId);
		return regionsRepository.count() < count;
	}
}
