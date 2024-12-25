package com.cg.humanresource.service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.dto.DepartmentsDTO;
import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.entity.Locations;
import com.cg.humanresource.exception.ValidationException;
import com.cg.humanresource.repository.DepartmentsRepository;
import com.cg.humanresource.repository.EmployeesRepository;
import com.cg.humanresource.repository.LocationsRepository;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentsService {
	
    @Autowired
    DepartmentsRepository departmentRepository;
    
    @Autowired
    LocationsRepository locationsRepository;
    
    @Autowired
    EmployeesRepository employeeRepository;
    
    @Transactional
    public Departments addDepartment(Departments department) throws Exception{
        Locations location = locationsRepository.findById(department.getLocation().getLocationId())
                .orElseThrow(() -> new ValidationException("Validation failed"));
 
        Employees manager = null;
        manager = employeeRepository.findById(department.getManagerId())
                .orElseThrow(() -> new ValidationException("Validation failed"));
 
        department.setLocation(location);
        department.setManagerId(manager != null ? manager.getEmployeeId() : null);
 
        return departmentRepository.save(department);
    }

    @Transactional
    public Departments updateDepartment(Departments department) throws Exception {
        if (department.getDepartmentId() == null) {
            throw new ValidationException("Validation failed");
        }

        // Fetch existing department, throw error if not found
        Departments existingDepartment = departmentRepository.findById(department.getDepartmentId())
                .orElseThrow(() -> new ValidationException("Validation failed"));

        // Update department name if provided
        if (department.getDepartmentName() != null) {
            existingDepartment.setDepartmentName(department.getDepartmentName());
        }

        // Update location if provided
        if (department.getLocation() != null && department.getLocation().getLocationId() != null) {
            Locations location = locationsRepository.findById(department.getLocation().getLocationId())
                    .orElseThrow(() -> new ValidationException("Validation failed"));
            existingDepartment.setLocation(location);
        }

        // Update manager if provided
        if (department.getManagerId() != null) {
            Employees manager = employeeRepository.findById(department.getManagerId())
                    .orElseThrow(() -> new ValidationException("Validation failed"));
            existingDepartment.setManagerId(manager.getEmployeeId());
        }

        // Save and return updated department
        return departmentRepository.save(existingDepartment);
    }


 
    @Transactional(readOnly=true)
    public Optional<Departments> findById(Long departmentId) {
        return departmentRepository.findById(departmentId);
    }
    
    /*@Transactional
    public boolean deleteDepartmentById(Long departmentId) {
        long countBefore = departmentRepository.count();
        if (!departmentRepository.existsById(departmentId))
        {
            throw new ValidationException("Validation failed");
        }
        departmentRepository.deleteById(departmentId);
        long countAfter = departmentRepository.count();
        return countAfter < countBefore;
    }*/
    
    /*@Transactional
    public boolean deleteDepartmentById(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ValidationException("Validation failed: Department not found with ID " + departmentId);
        }

        departmentRepository.deleteById(departmentId);
        return !departmentRepository.existsById(departmentId);
    }*/
    
    @Transactional
    public boolean deleteDepartmentById(Long departmentId) {
    	if (!departmentRepository.existsById(departmentId)) {
            throw new ValidationException("Validation failed: Department not found with ID " + departmentId);
        }

        departmentRepository.deleteById(departmentId);
        return !departmentRepository.existsById(departmentId);
    }
    
    /*@Transactional
    public void deleteDepartmentById(Long departmentId) {
        departmentRepository.deleteById(departmentId);
    }*/

 
    /*@Transactional(readOnly=true)
    public Double findMaxSalaryByDepartmentId(Long departmentId) {
        return departmentRepository.findMaxSalaryByDepartmentId(departmentId);
    }
    @Transactional(readOnly=true)
    public Double findMinSalaryByDepartmentId(Long departmentId) {
        return departmentRepository.findMinSalaryByDepartmentId(departmentId);
    }*/
    
    @Transactional(readOnly=true)
    public Double findMaxSalaryByDepartmentId(Long departmentId) {
    	if (!departmentRepository.existsById(departmentId)) {
            throw new ValidationException("Validation failed: Department not found with ID " + departmentId);
        }
    	return departmentRepository.findMaxSalaryByDepartmentId(departmentId);
    }
    
    @Transactional(readOnly=true)
    public Double findMinSalaryByDepartmentId(Long departmentId) {
    	if (!departmentRepository.existsById(departmentId)) {
            throw new ValidationException("Validation failed: Department not found with ID " + departmentId);
        }
    	return departmentRepository.findMinSalaryByDepartmentId(departmentId);
    }
    
    @Transactional(readOnly = true)
    public List<DepartmentsDTO> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private DepartmentsDTO convertToDTO(Departments department) {
    	List<Integer> employees = department.getEmployees().stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList());
        return new DepartmentsDTO(
                department.getDepartmentId(),
                department.getDepartmentName(),
                department.getManagerId(),
                department.getLocation() != null ? department.getLocation().getLocationId() : null,
                employees 
        );
    }

}