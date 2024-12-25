package com.cg.humanresource.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

import com.cg.humanresource.dto.EmployeesDTO;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.exception.EmployeeNotFoundException;
import com.cg.humanresource.exception.OpenJobPositionsNotFoundException;
import com.cg.humanresource.exception.ResourceNotFoundException;
import com.cg.humanresource.service.EmployeesService;
import com.cg.humanresource.service.JobsService;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeesController{

	
	@Autowired
	EmployeesService employeesService;
	
	@Autowired
	JobsService jobsService;
	
	@GetMapping(value = "/findfname/{firstname}", produces = "application/json")
	public ResponseEntity<List<EmployeesDTO>> getEmployeesByFirstName(@PathVariable String firstname) {
	    List<EmployeesDTO> employeesDTOs = employeesService.findByFirstName(firstname);

	    if (employeesDTOs.isEmpty()) {
	        throw new EmployeeNotFoundException("No employees found with the first name: " + firstname);
	    }

	    return new ResponseEntity<>(employeesDTOs, HttpStatus.OK);
	}
	
	    @GetMapping(value="/findemail/{email}", produces="application/json")
	    public ResponseEntity<EmployeesDTO> getEmployeesByEmail(@PathVariable String email){
	        EmployeesDTO employee = employeesService.findByEmail(email);
	        if(employee == null)
	            throw new EmployeeNotFoundException();
	        return new ResponseEntity<EmployeesDTO>(employee, HttpStatus.OK);
	    }

	    @GetMapping(value="/findphone/{phone}", produces="application/json")
	    public ResponseEntity<EmployeesDTO> getEmployeesByPhoneNumber(@PathVariable String phone){
	        EmployeesDTO employee = employeesService.findByPhoneNumber(phone);
	        if(employee == null)
	            throw new EmployeeNotFoundException();
	        return new ResponseEntity<EmployeesDTO>(employee, HttpStatus.OK);
	    }

	    @GetMapping(value="/findAllEmployeeWithNoCommission", produces="application/json")
	    public ResponseEntity<List<EmployeesDTO>> getAllEmployeeWithNoCommission(){
	        List<EmployeesDTO> employees = employeesService.findAllEmployeeWithNoCommission();
	        if(employees.isEmpty())
	            throw new EmployeeNotFoundException();
	        return new ResponseEntity<List<EmployeesDTO>>(employees, HttpStatus.OK);
	    }

	    @GetMapping(value="/findTotalCommissionIssuedToEmployee/{department_id}", produces="application/json")
	    public ResponseEntity<Map<String, Integer>> findTotalCommissionIssuedToEmployee(@PathVariable int department_id){
	        Map<String, Integer> response = new LinkedHashMap<>();
	        response.put("departmentid", department_id);
	        response.put("sum", employeesService.findTotalCommissionIssuedToEmployeeByDepartmentId(department_id));
	        return new ResponseEntity<Map<String, Integer>>(response, HttpStatus.OK);
	    }

	    @GetMapping(value="/listAllEmployees/{department_id}", produces="application/json")
	    public ResponseEntity<List<EmployeesDTO>> listAllEmployeesByDepartmentId(@PathVariable int department_id){
	        List<EmployeesDTO> employees = employeesService.listAllEmployeesByDepartmentId(department_id);
	        if(employees.isEmpty())
	            throw new EmployeeNotFoundException();
	        return new ResponseEntity<List<EmployeesDTO>>(employees, HttpStatus.OK);
	    }

	    @GetMapping(value="/employees_departmentwise_count", produces="application/json")
	    public ResponseEntity<List<Map<String, Integer>>> getEmployeesCountByDepartmentWise(){
	        return new ResponseEntity<List<Map<String, Integer>>>(employeesService.countEmployeesGroupByDepartment(), HttpStatus.OK);
	    }

	    @GetMapping(value="/listallmanagerdetails", produces="application/json")
	    public ResponseEntity<List<EmployeesDTO>> getAllManagers(){
	        List<EmployeesDTO> managers = employeesService.getAllManagers();
	        if(managers.isEmpty())
	            throw new EmployeeNotFoundException();
	        return new ResponseEntity<List<EmployeesDTO>>(managers, HttpStatus.OK);
	    }

	    @GetMapping(value="/locationwisecountofemployees", produces="application/json")
	    public ResponseEntity<List<Map<Integer, Integer>>> getEmployeesCountByLocationWise(){
	        return new ResponseEntity<List<Map<Integer, Integer>>>(employeesService.countEmployeesGroupByLocation(), HttpStatus.OK);
	    }

	    @GetMapping(value="/{empid}/findmaxsalaryofjob", produces="application/json")
	    public ResponseEntity<Map<String, Object>> findMaxSalaryOfJobByEmployeeId(@PathVariable int empid){
	        Map<String, Object> maxSalaryInfo = employeesService.getMaxSalaryOfJobByEmployeeId(empid);
	        return new ResponseEntity<Map<String, Object>>(maxSalaryInfo, HttpStatus.OK);
	    }

	    @GetMapping(value="/findAllOpenPositions", produces="application/json")
	    public ResponseEntity<List<Object>> findAllOpenPositions() throws OpenJobPositionsNotFoundException{
	        List<Object> positions = employeesService.getAllOpenPositions();
	        if(positions.isEmpty()) {
	            throw new OpenJobPositionsNotFoundException();
	        }
	        return new ResponseEntity<List<Object>>(positions, HttpStatus.OK);
	    }

	    @GetMapping(value="/findAllOpenPositions/{department_id}", produces="application/json")
	    public ResponseEntity<List<Object>> findAllOpenPositionsByDepartmentId(@PathVariable int department_id)
	            throws OpenJobPositionsNotFoundException{

	        List<Object> positions = employeesService.getAllOpenPositionsByDepartment(department_id);
	        if(positions.isEmpty()) {
	            throw new OpenJobPositionsNotFoundException();
	        }
	        return new ResponseEntity<List<Object>>(positions, HttpStatus.OK);
	    }

	    @GetMapping(value="/listallemployeebyhiredate/{from_hiredate}/{to_hiredate}", produces="application/json")
	    public ResponseEntity<List<EmployeesDTO>> findAllEmployeeByHireDateRange(@PathVariable LocalDate from_hiredate,
	                                                                              @PathVariable LocalDate to_hiredate){
	        List<EmployeesDTO> employees = employeesService.getAllEmployeeByHireDate(from_hiredate, to_hiredate);
	        return new ResponseEntity<List<EmployeesDTO>>(employees, HttpStatus.OK);
	    }
	

	@PostMapping(consumes =MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addNewEmployee(@RequestBody Employees employee) throws Exception{
	    
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		 employeesService.addOrModifyEmployee(employee);
			return new ResponseEntity<>("Record created successfully.",HttpStatus.CREATED);
		}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> modifyEmployee(@RequestBody Employees employee) throws Exception{
	    
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		 employeesService.addOrModifyEmployee(employee);
			return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
		}
	
	@PutMapping(value="/{jobId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assignEmployeeJobByJobId(@RequestBody Employees employee,@PathVariable String jobId) throws Exception{
		
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		employeesService.assignEmployeeJobByJobId(employee,jobId);
		return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
	}
	@PutMapping(value="/manager/{managerId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assignEmployeeManagerByManagerId(@RequestBody Employees employee,@PathVariable Integer managerId) throws Exception{
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		employeesService.assignEmployeeManagerByManagerId(employee,managerId);
		return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
	}
	@PutMapping(value="/department/{deptId}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assignDepartmentToEmployeeByDepartmentId(@RequestBody Employees employee,@PathVariable Long deptId) throws Exception{
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		employeesService.assignEmployeeDepartmentByDepartmentId(employee,deptId);
		return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
	}
	
	@PutMapping(value="/{deptId}/{sal}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> assignDepartmentToEmployeeByDepartmentIdAndUpdateCommission
		(@RequestBody Employees employee,@PathVariable Long deptId,
		@PathVariable Double sal) throws Exception{
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		employeesService.assignEmployeeDepartmentByDepartmentIdAndUpdateCommission(employee,deptId,sal);
		return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
	}
	
	@PutMapping(value="/email/{email}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateEmployeeEmailId(@RequestBody Employees employee,@PathVariable String email) throws Exception{
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		employeesService.updateEmployeeEmail(employee,email);
		return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
	}
	@PutMapping(value="/phone/{phone}",consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateEmployeePhoneNumber(@RequestBody Employees employee,@PathVariable String phone) throws Exception{
		if(employee.getEmployeeId() == 0) {
			throw new ResourceNotFoundException("Employee details are required");
		}
		employeesService.updateEmployeePhoneNumber(employee,phone);
		return new ResponseEntity<>("Record modified successfully.",HttpStatus.OK);
	}

	@DeleteMapping("/{employeeId}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable int employeeId) {
	    Employees deletedEmployee = employeesService.deleteEmployeeById(employeeId);
	    
	    if (deletedEmployee == null) {
	        throw new ResourceNotFoundException("Employee details are required");
	    }
	    
	    return new ResponseEntity<>("Record deleted successfully.", HttpStatus.GONE);
	}

	@GetMapping(value="/{employeeId}", produces = "application/json")
	public ResponseEntity<EmployeesDTO> getEmployeeById(@PathVariable int employeeId){
		return new ResponseEntity<EmployeesDTO>(employeesService.getEmployeeById(employeeId),HttpStatus.FOUND);
	}
}
	
	
	
	

