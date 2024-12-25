package com.cg.humanresource.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.dto.LocationsDTO;
import com.cg.humanresource.entity.Countries;
import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.entity.Locations;
import com.cg.humanresource.exception.CountryNotFoundException;
import com.cg.humanresource.exception.DepartmentNotFoundException;
import com.cg.humanresource.exception.LocationNotFoundException;
import com.cg.humanresource.repository.CountriesRepository;
import com.cg.humanresource.repository.DepartmentsRepository;
import com.cg.humanresource.repository.LocationsRepository;


@Service
public class LocationsService {

	@Autowired 
	LocationsRepository locationsRepository;
	
	@Autowired
    CountriesRepository countriesRepository;

    @Autowired
    DepartmentsRepository departmentRepository; 
    
    private LocationsDTO convertToDto(Locations location) {
        return new LocationsDTO(
                location.getLocationId(),
                location.getStreetAddress(),
                location.getPostalCode(),
                location.getCity(),
                location.getStateProvince(),
                location.getCountry() != null ? location.getCountry().getCountryId() : null
        );
    }

       
//    @Transactional(readOnly=true)
//    public List<Locations> getAllLocations() {
//        return LocationsRepository.findAll();
//    }
//
//    @Transactional(readOnly=true)
//    public Optional<Locations> getLocationById(Long locationId) {
//        return LocationsRepository.findById(locationId);
//    }
    @Transactional(readOnly = true)
    public List<LocationsDTO> getAllLocations() {
        List<Locations> locations = locationsRepository.findAll();
        if(locations.isEmpty())
        	throw new LocationNotFoundException();
        return locations.stream()
                .map(this::convertToDto) 
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public LocationsDTO getLocationById(Long locationId) {
        Locations location = locationsRepository.findById(locationId)
                .orElseThrow(() -> new LocationNotFoundException());
        return convertToDto(location); 
    }

    
    @Transactional
    public Locations createLocation(Locations location) throws Exception {
        // Validate the existence of the associated country
        if (location.getCountry() == null || location.getCountry().getCountryId() == null) {
            throw new IllegalArgumentException("Country ID is required.");
        }

        Countries country = countriesRepository.findById(location.getCountry().getCountryId())
                .orElseThrow(() -> new CountryNotFoundException("Country does not exist"));

        // Validate the existence of all departments
        List<Departments> validatedDepartments = new ArrayList<>();
        if (location.getDepartments() != null) {
            for (Departments dept : location.getDepartments()) {
                if (dept.getDepartmentId() != null) {
                    Departments department = departmentRepository.findById(dept.getDepartmentId())
                            .orElseThrow(() -> new DepartmentNotFoundException("Department does not exist with ID: " + dept.getDepartmentId()));
                    validatedDepartments.add(department);
                }
            }
        }

        // Set the validated departments and country to the location
        location.setDepartments(validatedDepartments);
        location.setCountry(country);

        // Save the location entity
        return locationsRepository.save(location);
    }

    
    // Update Existing Location
    public Locations updateLocation(Long locationId, Locations updatedLocation) {
        return locationsRepository.findById(locationId).map(existingLocation -> {
            existingLocation.setStreetAddress(updatedLocation.getStreetAddress());
            existingLocation.setPostalCode(updatedLocation.getPostalCode());
            existingLocation.setCity(updatedLocation.getCity());
            existingLocation.setStateProvince(updatedLocation.getStateProvince());
            existingLocation.setCountry(updatedLocation.getCountry());
            return locationsRepository.save(existingLocation);
        }).orElseThrow(() -> new RuntimeException("Location with ID " + locationId + " not found."));
    }

    // Delete Location by ID
    public String deleteLocation(Long locationId) 
    {
        if (locationsRepository.existsById(locationId)) {
            locationsRepository.deleteById(locationId);
            return "Record Deleted Successfully";
        } else {
            throw new RuntimeException("Location with ID " + locationId + " not found.");
        }
    }
}

	