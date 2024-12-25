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

import com.cg.humanresource.dto.RegionsDTO;
import com.cg.humanresource.entity.Regions;
import com.cg.humanresource.exception.RegionNotFoundException;
import com.cg.humanresource.service.RegionsService;

import jakarta.validation.Valid;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/regions")
public class RegionsController 
{
	@Autowired
	RegionsService regionsService;
 
	    @GetMapping(produces = "application/json")
	    public ResponseEntity<List<RegionsDTO>> getAllRegions() {
	        List<RegionsDTO> regions = regionsService.getAllRegions();
	        return new ResponseEntity<>(regions, HttpStatus.OK);
	    }

	    @GetMapping(value = "/{regionId}", produces = "application/json")
	    public ResponseEntity<RegionsDTO> getRegionsById(@PathVariable int regionId) {
	        RegionsDTO region = regionsService.getRegionsById(regionId);
	        return new ResponseEntity<>(region, HttpStatus.OK);
	    }
	

	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> insertRegions(@RequestBody @Valid  Regions regions)
	{
		boolean isSuccess = regionsService.addOrModifyRegions(regions);
	    if (isSuccess)
	    {
	        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
	    }
	    else
	    {
	        throw new RegionNotFoundException("Validation failed");
	    }
	}
	
	@PutMapping(consumes = "application/json")
	public ResponseEntity<String> modifyRegions(@RequestBody Regions regions) 
	{
	    boolean isSuccess = regionsService.addOrModifyRegions(regions);
	    if (isSuccess) 
	    {
	        return new ResponseEntity<>("Record Modified Successfully", HttpStatus.OK);
	    } 
	    else 
	    {
	        throw new RegionNotFoundException("Modification failed");
	    }
	}
	
	@DeleteMapping(value = "/{regionId}")
	public ResponseEntity<String> deleteRegionsById(@PathVariable int regionId) 
	{
	    boolean isSuccess = regionsService.deleteRegionsById(regionId);
	    if (isSuccess) 
	    {
	        return new ResponseEntity<>("Record Deleted Successfully", HttpStatus.GONE);
	    } 
	    else 
	    {
	  
	        throw new RegionNotFoundException("Deletion failed");
	    }
	}

}
