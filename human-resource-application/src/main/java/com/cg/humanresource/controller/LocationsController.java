package com.cg.humanresource.controller;

import com.cg.humanresource.dto.LocationsDTO;
import com.cg.humanresource.entity.Locations;
import com.cg.humanresource.exception.ResourceNotFoundException;
import com.cg.humanresource.service.LocationsService;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;


@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/locations")
public class LocationsController {
	

	@Autowired
	LocationsService locationsService;


//    @GetMapping
//    public ResponseEntity<List<Locations>> getAllLocations() {
//        List<Locations> locations = LocationsService.getAllLocations();
//        return ResponseEntity.ok(locations);
//    }
//
//    @GetMapping("/{location_id}")
//    public ResponseEntity<Locations> getLocationById(@PathVariable("location_id") Long locationId) {
//        return LocationsService.getLocationById(locationId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
//    }


    @GetMapping(produces = "application/json")
    public ResponseEntity<List<LocationsDTO>> getAllLocations() {
        return ResponseEntity.ok(locationsService.getAllLocations());
    }

    @GetMapping(value = "/{location_id}", produces = "application/json")
    public ResponseEntity<LocationsDTO> getLocationById(@PathVariable("location_id") Long locationId) {
        return ResponseEntity.ok(locationsService.getLocationById(locationId));
    }
   //  3. POST: Add New Location
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> createLocation(@Valid @RequestBody Locations location) throws Exception {
        // Check if location ID is provided
        if (location.getLocationId() == null) {
            throw new IllegalArgumentException("Location ID is required.");
        }

        // Call service to save the location
        Locations createdLocation = locationsService.createLocation(location);

        return new ResponseEntity<>("Location added successfully with ID: " + createdLocation.getLocationId(),
                HttpStatus.CREATED);
    }

    

    // 4. PUT: Modify Location
    @PutMapping(consumes = "application/json")
    public ResponseEntity<String> updateLocation(@RequestBody Locations location) {
        locationsService.updateLocation(location.getLocationId(), location);
        return ResponseEntity.ok("Record Modified Successfully");
    }

    // 5. DELETE: Delete Location by ID
    @DeleteMapping("/{location_id}")
    public ResponseEntity<String> deleteLocation(@PathVariable("location_id") Long locationId) {
        locationsService.deleteLocation(locationId);
        return ResponseEntity.ok("Record Deleted Successfully");
    }
}

