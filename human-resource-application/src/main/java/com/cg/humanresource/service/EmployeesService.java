package com.cg.humanresource.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.dto.EmployeesDTO;
import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.entity.Jobs;
import com.cg.humanresource.exception.DepartmentNotFoundException;
import com.cg.humanresource.exception.EmployeeNotFoundException;
import com.cg.humanresource.exception.JobNotFoundException;
import com.cg.humanresource.repository.DepartmentsRepository;
import com.cg.humanresource.repository.EmployeesRepository;
import com.cg.humanresource.repository.JobsRepository;

@Service
public class EmployeesService {
	
	@Autowired
	EmployeesRepository employeeRepository;
	
	@Autowired
	JobsRepository jobsRepository;
	
	@Autowired
	DepartmentsRepository departmentRepository;

	public EmployeesDTO convertEntityToDTO(Employees entity) {
	    if (entity == null) {
	        return null;
	    }
	    if(entity.getManager()==null) {
	    	
	    	return new EmployeesDTO(
	    			entity.getEmployeeId(),
	    			entity.getFirstName(),
	    			entity.getLastName(),
	    			entity.getEmail(),
	    			entity.getPhoneNumber(),
	    			entity.getHireDate(),
	    			entity.getSalary(),
	    			entity.getCommissionPct(),
	    			null,
	    			entity.getDepartment().getDepartmentId(),
	    			entity.getJob().getJobId()
	    			);
	    }
	    return new EmployeesDTO(
	            entity.getEmployeeId(),
	            entity.getFirstName(),
	            entity.getLastName(),
	            entity.getEmail(),
	            entity.getPhoneNumber(),
	            entity.getHireDate(),
	            entity.getSalary(),
	            entity.getCommissionPct(),
	            entity.getManager().getEmployeeId(),
	            entity.getDepartment().getDepartmentId(),
	            entity.getJob().getJobId()
	    );
	}

	@Transactional(readOnly = true)
	public List<EmployeesDTO> findByFirstName(String fname) {
	    List<Employees> employees = employeeRepository.findByFirstName(fname);
	    return employees.stream()
	                    .map(this::convertEntityToDTO)
	                    .collect(Collectors.toList());
	}
	
	@Transactional(readOnly=true)
	public EmployeesDTO findByEmail(String email){
	    Employees employee = employeeRepository.findByEmail(email);
	    return convertEntityToDTO(employee);
	}

	@Transactional(readOnly=true)
	public EmployeesDTO findByPhoneNumber(String phoneNumber){
	    Employees employee = employeeRepository.findByPhoneNumber(phoneNumber);
	    return convertEntityToDTO(employee);
	}

	@Transactional(readOnly=true)
	public List<EmployeesDTO> findAllEmployeeWithNoCommission(){
	    List<Employees> employees = employeeRepository.findAllEmployeeWithNoCommission();
	    return employees.stream()
	                    .map(this::convertEntityToDTO)
	                    .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public int findTotalCommissionIssuedToEmployeeByDepartmentId(int deptId) {
	    if(employeeRepository.findByDepartmentId(deptId).isEmpty()) {
	        throw new DepartmentNotFoundException();
	    }
	    int sum = 0;
	    List<Employees> employees = employeeRepository.findByDepartmentId(deptId);
	    for (Employees employee : employees) {
	        if (employee.getCommissionPct() != null) {
	            sum += (int) (employee.getSalary() * employee.getCommissionPct());
	        }
	    }
	    return sum;
	}

	@Transactional(readOnly = true)
	public List<EmployeesDTO> listAllEmployeesByDepartmentId(int deptId) {
	    if(employeeRepository.findByDepartmentId(deptId).isEmpty()) {
	        throw new DepartmentNotFoundException();
	    }
	    List<Employees> employees = employeeRepository.findByDepartmentId(deptId);
	    return employees.stream()
	                    .map(this::convertEntityToDTO)
	                    .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<Map<String, Integer>> countEmployeesGroupByDepartment() {
	    return employeeRepository.countAllEmployeesGroupByDepartment();
	}

	@Transactional(readOnly = true)
	public List<EmployeesDTO> getAllManagers(){
	    List<Employees> managers = employeeRepository.findAllManagers();
	    return managers.stream()
	                   .map(this::convertEntityToDTO)
	                   .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<Map<Integer, Integer>> countEmployeesGroupByLocation() {
	    return employeeRepository.countAllEmployeesGroupByLocation();
	}

	@Transactional(readOnly=true)
	public Map<String,Object> getMaxSalaryOfJobByEmployeeId(int employeeId){
	    Map<String,Object> result = new LinkedHashMap<>();
	    Employees employee = employeeRepository.findById(employeeId)
	                .orElseThrow(() -> new EmployeeNotFoundException());
	    result.put("Job_Title", employee.getJob().getJobTitle());
	    result.put("Max_Salary", employee.getJob().getMaxSalary());
	    return result;
	}

	@Transactional(readOnly=true)
	public List<Object> getAllOpenPositions(){
	    return jobsRepository.findAllOpenPositions();
	}

	@Transactional(readOnly=true)
	public List<Object> getAllOpenPositionsByDepartment(int deptId){
	    if(employeeRepository.findByDepartmentId(deptId).isEmpty()) {
	        throw new DepartmentNotFoundException();
	    }
	    return jobsRepository.findAllOpenPositionsByDepartmentId(deptId);
	}

	@Transactional(readOnly=true)
	public List<EmployeesDTO> getAllEmployeeByHireDate(LocalDate startDate, LocalDate endDate){
	    List<EmployeesDTO> result = new ArrayList<>();
	    for(Employees e : employeeRepository.findAll()) {
	        LocalDate dateToCheck = e.getHireDate();
	        if((dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate)) &&
	           (dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate))) {
	            result.add(convertEntityToDTO(e));
	        }
	    }
	    if(result.isEmpty()) {
	        throw new EmployeeNotFoundException();
	    }
	    return result;
	}

	@Transactional
	public Employees addOrModifyEmployee(Employees employee) throws Exception {
		Departments department = departmentRepository
				.findById(employee.getDepartment().getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
		
	    Jobs job = jobsRepository
	                .findById(employee.getJob().getJobId())
	                .orElseThrow(() -> new JobNotFoundException("Job does not exist"));

	    Employees manager = null;
	    if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
	        manager = employeeRepository
	                    .findById(employee.getManager().getEmployeeId())
	                    .orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
	    }
	    employee.setJob(job);
	    employee.setDepartment(department);
	    employee.setManager(manager);

	    return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employees assignEmployeeJobByJobId(Employees employee,String jobId) throws Exception {
		Departments department = departmentRepository
				.findById(employee.getDepartment().getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
		
	    Jobs job = jobsRepository
	                .findById(jobId)
	                .orElseThrow(() -> new JobNotFoundException("Job does not exist"));

	    Employees manager = null;
	    if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
	        manager = employeeRepository
	                    .findById(employee.getManager().getEmployeeId())
	                    .orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
	    }
	    employee.setJob(job);
	    employee.setDepartment(department);
	    employee.setManager(manager);

	    return employeeRepository.save(employee);
	}
	@Transactional
	public Employees assignEmployeeManagerByManagerId(Employees employee,Integer managerId) throws Exception {
		Departments department = departmentRepository
				.findById(employee.getDepartment().getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
		
		Jobs job = jobsRepository
				.findById(employee.getJob().getJobId())
				.orElseThrow(() -> new JobNotFoundException("Job does not exist"));
		
		Employees manager = null;
		if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
			manager = employeeRepository
					.findById(managerId)
					.orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
		}
		employee.setJob(job);
		employee.setDepartment(department);
		employee.setManager(manager);
		
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employees assignEmployeeDepartmentByDepartmentId(Employees employee,Long departmentId) throws Exception {
		Departments department = departmentRepository
				.findById(departmentId)
				.orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
		
		Jobs job = jobsRepository
				.findById(employee.getJob().getJobId())
				.orElseThrow(() -> new JobNotFoundException("Job does not exist"));
		
		Employees manager = null;
		if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
			manager = employeeRepository
					.findById(employee.getManager().getEmployeeId())
					.orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
		}
		employee.setJob(job);
		employee.setDepartment(department);
		employee.setManager(manager);
		
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employees assignEmployeeDepartmentByDepartmentIdAndUpdateCommission(Employees employee,
			Long departmentId, Double commission) throws Exception {
	    Departments department = departmentRepository
	            .findById(departmentId)
	            .orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
	    
	    Jobs job = jobsRepository
	            .findById(employee.getJob().getJobId())
	            .orElseThrow(() -> new JobNotFoundException("Job does not exist"));
	    
	    Employees manager = null;
	    if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
	        manager = employeeRepository
	                .findById(employee.getManager().getEmployeeId())
	                .orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
	    }
	    employee.setJob(job);
	    employee.setDepartment(department);
	    employee.setManager(manager);
	    
	    if ("Sales".equalsIgnoreCase(department.getDepartmentName())) {
	        employee.setCommissionPct(commission); 
	    }
	    
	    return employeeRepository.save(employee);
	}

	@Transactional
	public Employees updateEmployeeEmail(Employees employee,String email) throws Exception {
		Departments department = departmentRepository
				.findById(employee.getDepartment().getDepartmentId())
				.orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
		
		Jobs job = jobsRepository
				.findById(employee.getJob().getJobId())
				.orElseThrow(() -> new JobNotFoundException("Job does not exist"));
		
		Employees manager = null;
		if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
			manager = employeeRepository
					.findById(employee.getManager().getEmployeeId())
					.orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
		}
		employee.setJob(job);
		employee.setDepartment(department);
		employee.setManager(manager);
		employee.setEmail(email);
		
		return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employees updateEmployeePhoneNumber(Employees employee, String phoneNumber) throws Exception {
	    Departments department = departmentRepository
	            .findById(employee.getDepartment().getDepartmentId())
	            .orElseThrow(() -> new DepartmentNotFoundException("Department does not exist"));
	    
	    Jobs job = jobsRepository
	            .findById(employee.getJob().getJobId())
	            .orElseThrow(() -> new JobNotFoundException("Job does not exist"));
	    
	    Employees manager = null;
	    if (employee.getManager() != null && employee.getManager().getEmployeeId() != 0) {
	        manager = employeeRepository
	                .findById(employee.getManager().getEmployeeId())
	                .orElseThrow(() -> new EmployeeNotFoundException("Manager does not exist"));
	    }

	    employee.setJob(job);
	    employee.setDepartment(department);
	    employee.setManager(manager);
	    employee.setPhoneNumber(phoneNumber);
	    
	    return employeeRepository.save(employee);
	}
	
	@Transactional
	public Employees deleteEmployeeById(int employeeId) {
	    Employees employee = null;
	    employee = employeeRepository.findById(employeeId)
	            .orElseThrow(() -> new EmployeeNotFoundException());
	    
	    employeeRepository.deleteById(employeeId);
	    return employee;
	}

	@Transactional
	public EmployeesDTO getEmployeeById(int employeeId) {
		Employees employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new EmployeeNotFoundException());
	    return convertEntityToDTO(employee);
	}
	
	
	
	
	
	
	
	
	
	
}
