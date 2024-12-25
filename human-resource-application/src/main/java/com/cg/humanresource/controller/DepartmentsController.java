package com.cg.humanresource.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cg.humanresource.dto.DepartmentsDTO;
import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.exception.ResourceNotFoundException;
import com.cg.humanresource.exception.ValidationException;
import com.cg.humanresource.service.DepartmentsService;

import jakarta.validation.Valid;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/department")
public class DepartmentsController {
	@Autowired
	DepartmentsService departmentService;

    @PostMapping(consumes = "application/json")
	public ResponseEntity<String> addDepartment(@Valid @RequestBody Departments department) throws Exception
	{
		departmentService.addDepartment(department);
		return new ResponseEntity<String>("Record created successfully",HttpStatus.CREATED);
	}
    
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateDepartment(@Valid @RequestBody Departments department) throws Exception {
    	departmentService.updateDepartment(department);
        return new ResponseEntity<String>("Record Modified successfully",HttpStatus.OK);
    }
    
    @DeleteMapping("/{department_id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable("department_id") Long departmentId) {
        
            boolean isDeleted = departmentService.deleteDepartmentById(departmentId);

            if (isDeleted) {
                return new ResponseEntity<>("Record Deleted Successfully", HttpStatus.OK);
            } else {
                throw new ValidationException("Validation failed");
            }
       
    }
    

	@GetMapping("/findminsalary/{department_id}")
    public ResponseEntity<Map<String, Object>> findMinSalary(@PathVariable("department_id") Long departmentId) {
        Double minSalary = departmentService.findMinSalaryByDepartmentId(departmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("department_name", departmentService.findById(departmentId).get().getDepartmentName());
        response.put("min salary", minSalary);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@GetMapping("/findmaxsalary/{department_id}")
    public ResponseEntity<Map<String, Object>> findMaxSalary(@PathVariable("department_id") Long departmentId) {
        Double maxSalary = departmentService.findMaxSalaryByDepartmentId(departmentId);
        Map<String, Object> response = new HashMap<>();
        response.put("department_name", departmentService.findById(departmentId).get().getDepartmentName());
        response.put("max salary", maxSalary);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
//	@GetMapping(produces = "application/json")
//	public ResponseEntity<List<Departments>> getAllDepartments() {
//	    List<Departments> departments = departmentService.getAllDepartments();
//	    if (departments.isEmpty()) {
//	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	    }
//	    return new ResponseEntity<>(departments, HttpStatus.OK);
//	}
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<DepartmentsDTO>> getAllDepartments() {
	    List<DepartmentsDTO> departmentDTOs = departmentService.getAllDepartments();
	    if (departmentDTOs.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(departmentDTOs, HttpStatus.OK);
	}

 
}