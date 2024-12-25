package com.cg.humanresource.service;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cg.humanresource.entity.Countries;
import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.entity.JobHistory;
import com.cg.humanresource.entity.Jobs;
import com.cg.humanresource.entity.Locations;
import com.cg.humanresource.exception.CountryNotFoundException;
import com.cg.humanresource.exception.DepartmentNotFoundException;
import com.cg.humanresource.exception.EmployeeNotFoundException;
import com.cg.humanresource.exception.JobNotFoundException;
import com.cg.humanresource.exception.ResourceNotFoundException;
import com.cg.humanresource.repository.CountriesRepository;
import com.cg.humanresource.repository.DepartmentsRepository;
import com.cg.humanresource.repository.EmployeesRepository;
import com.cg.humanresource.repository.JobHistoryRepository;
import com.cg.humanresource.repository.JobsRepository;
@Service
public class JobHistoryService {

    @Autowired
    private JobHistoryRepository jobHistoryRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private JobsRepository jobsRepository;

    @Autowired 
    private DepartmentsRepository departmentsRepository;

    // Create Job History (POST)
    @Transactional
    public JobHistory createJobHistory(JobHistory jobHistory,Integer empid,LocalDate startdate, String jobid,Long deptid) throws Exception {
      
        Employees employee = employeesRepository.findById(empid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + empid));

        Jobs job = jobsRepository.findById(jobid)
                .orElseThrow(() -> new ResourceNotFoundException("Job does not exist with ID: " + jobid));
        
        Departments department = departmentsRepository.findById(deptid)
                .orElseThrow(() -> new ResourceNotFoundException("Department does not exist with ID: " + deptid));

        jobHistory.setEmployee(employee);
        jobHistory.setJob(job);
        jobHistory.setDepartment(department);
        jobHistory.setStartDate(startdate);

        return jobHistoryRepository.save(jobHistory);
    }

    @Transactional(readOnly=true)
    public Map<String, Integer> findExperienceOfEmployee(Integer employeeId) {
        Employees employee = employeesRepository.findById(employeeId).orElse(null);
        
        if(employee == null) throw new EmployeeNotFoundException();

        LocalDate endDate = null;
        if(jobHistoryRepository.findByEmployeeId(employeeId).size() > 0) {
        	JobHistory jobHistory = jobHistoryRepository.findCurrentJobHistoryByEmployeeId(employeeId);
        	endDate = jobHistory.getEndDate();
        }
        
        Period period = Period.between(employee.getHireDate(), endDate == null ? LocalDate.now() : endDate);
        int totalYears = period.getYears();
        int totalMonths = period.getMonths();
        int totalDays = period.getDays();

        Map<String, Integer> experience = new HashMap<>();
        experience.put("years", totalYears);
        experience.put("months", totalMonths);
        experience.put("days", totalDays);

        return experience;
    }

    // List Employees with Less than One Year Experience (GET)
    public List<Employees> listAllEmployeesWithLessThanOneYearExperience() {
        List<JobHistory> jobHistories = jobHistoryRepository.findAll();
        List<Employees> employees = new ArrayList<>();

        for (JobHistory jobHistory : jobHistories) {
            Period period = Period.between(jobHistory.getStartDate(), jobHistory.getEndDate() == null ? LocalDate.now() : jobHistory.getEndDate());
            if (period.getYears() < 1) {
                employees.add(jobHistory.getEmployee());
            }
        }

        return employees;
    }

    // Update Job History End Date (PUT)
    @Transactional
    public JobHistory updateJobHistoryEndDate(JobHistory jobHistory,Integer empid, LocalDate endDate) throws ResourceNotFoundException {
    	Employees employee = employeesRepository.findById(empid)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + empid));

        jobHistory.setEmployee(employee);
        jobHistory.setJob(employee.getJob());
        jobHistory.setDepartment(employee.getDepartment());
        jobHistory.setEndDate(endDate);

        return jobHistoryRepository.save(jobHistory);
    }
}

    
    
    
    
    
    
    
    
    
    
    
/*
    
    @GetMapping("/totalyearsofexperience/{emp_id}")
    public String findExperienceOfEmployee(@PathVariable("emp_id") int employeeId) {
            return jobHistoryService.calculateTotalExperience(employeeId);
    }
    
    
       
       
        @GetMapping("/listAllEmployeesWithLessThanOneYearExperience")
        public List<Employees> listAllEmployeesWithLessThanOneYearExperience(){
            return jobHistoryService.listAllEmployeesWithLessThanOneYearExperience();
        }
     
        @PostMapping("/add-job-history")
        public ResponseEntity<String> addJobHistoryForNewEmployee(@RequestBody Employees employee) throws Exception {
            if(employee.getEmployeeId() == 0 ) {
                throw new BadRequestException("Job History details are required");
            }
            jobHistoryService.addNewEmployeeAndJobHistory(employee);
            return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
        }
       
        @PutMapping("/update-job-history/{employeeId}/{newJobId}/{newDepartmentId}")
        public ResponseEntity<String> updateJobHistory(
                @PathVariable int employeeId,
                @PathVariable String newJobId,
                @PathVariable Long newDepartmentId) {
     
            JobHistory updatedJobHist = jobHistoryService.updateJobHistory(employeeId, newJobId, newDepartmentId);
            if (updatedJobHist == null)
                throw new ResourceNotFoundException("Employee with ID "+ employeeId +" not found");
       
    }
    
    
    
    
    
    
    

    // 1. Finding total years, months, and days of experience for an employee
    
   /* public Map<String, Integer> calculateTotalExperience(Integer employeeId) throws Exception {
        List<JobHistory> jobHistories = jobHistoryRepository.findByEmployee_EmployeeId(employeeId);

        if (jobHistories.isEmpty()) {
            throw new Exception("No Job History records found for Employee ID: " + employeeId);
        }

        // Initialize variables to store cumulative experience
        int totalYears = 0, totalMonths = 0, totalDays = 0;

        for (JobHistory history : jobHistories) {
            LocalDate startDate = history.getStartDate();
            LocalDate endDate = history.getEndDate();

            if (startDate == null || endDate == null) {
                throw new Exception("Invalid date range in Job History for Employee ID: " + employeeId);
            }

            // Calculate experience for this particular job history entry
            Period period = Period.between(startDate, endDate);

            totalYears += period.getYears();
            totalMonths += period.getMonths();
            totalDays += period.getDays();
        }

        // Normalize months and days
        if (totalDays >= 30) {
            totalMonths += totalDays / 30;
            totalDays = totalDays % 30;
        }

        if (totalMonths >= 12) {
            totalYears += totalMonths / 12;
            totalMonths = totalMonths % 12;
        }

        // Prepare the response in a Map
        Map<String, Integer> experienceMap = new HashMap<>();
        experienceMap.put("years", totalYears);
        experienceMap.put("months", totalMonths);
        experienceMap.put("days", totalDays);

        return experienceMap;
    }


public List<Employees> getEmployeesWithLessThanOneYearExperience() {
    Map<Employees, Integer> employeeExperienceMap = new HashMap<>();

    // Fetch all job histories
    List<JobHistory> jobHistories = jobHistoryRepository.findAll();

    // Calculate total experience for each employee
    for (JobHistory history : jobHistories) {
        Employees employee = history.getEmployee();
        LocalDate startDate = history.getStartDate();
        LocalDate endDate = history.getEndDate();

        if (startDate != null && endDate != null) {
            // Calculate total days of experience for this job history
            Period period = Period.between(startDate, endDate);
            int totalDays = period.getYears() * 365 + period.getMonths() * 30 + period.getDays();

            // Add experience to the employee's total
            employeeExperienceMap.put(employee, employeeExperienceMap.getOrDefault(employee, 0) + totalDays);
        }
    }

    // Filter employees with less than one year of experience (365 days)
    return employeeExperienceMap.entrySet()
            .stream()
            .filter(entry -> entry.getValue() < 365)
            .map(Map.Entry::getKey) // Extract Employee objects
            .collect(Collectors.toList());
}

@Transactional
public JobHistory createJobHistory(JobHistory jobHistory) throws Exception {
    // Validate the existence of the associated employee
    if (jobHistory.getEmployee() == null || jobHistory.getEmployee().getEmployeeId() == null) {
        throw new IllegalArgumentException("Employee ID is required.");
    }

    Employees employee = employeesRepository.findById(jobHistory.getEmployee().getEmployeeId())
            .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with ID: " + jobHistory.getEmployee().getEmployeeId()));

    // Validate the existence of the associated job
    if (jobHistory.getJob() == null || jobHistory.getJob().getJobId() == null) {
        throw new IllegalArgumentException("Job ID is required.");
    }

    Jobs job = jobsRepository.findById(jobHistory.getJob().getJobId())
            .orElseThrow(() -> new JobNotFoundException("Job does not exist with ID: " + jobHistory.getJob().getJobId()));

    // Validate the existence of the associated department
    if (jobHistory.getDepartment() == null || jobHistory.getDepartment().getDepartmentId() == null) {
        throw new IllegalArgumentException("Department ID is required.");
    }

    Departments department = departmentsRepository.findById(jobHistory.getDepartment().getDepartmentId())
            .orElseThrow(() -> new DepartmentNotFoundException("Department does not exist with ID: " + jobHistory.getDepartment().getDepartmentId()));

    // Set the validated employee, job, and department to the job history
    jobHistory.setEmployee(employee);
    jobHistory.setJob(job);
    jobHistory.setDepartment(department);
    jobHistory.setStartDate(LocalDate.now());

    // Save the job history entity
    return jobHistoryRepository.save(jobHistory);
}



@Transactional
public JobHistory updateJobHistory(Integer employeeId, String newJobId, Long newDepartmentId) {
    // Step 1: Fetch Employee
    Employees employee = employeesRepository.findById(employeeId)
            .orElseThrow(() -> new ResourceNotFoundException("Employee with ID " + employeeId + " not found"));

    // Step 2: Validate Current Job and Department
    Jobs currentJob = employee.getJob();
    Departments currentDepartment = employee.getDepartment();

    if (currentJob == null || currentDepartment == null) {
        throw new ResourceNotFoundException("Employee does not have an assigned job or department.");
    }

    // Step 3: Fetch New Job and Department
    Jobs newJob = jobsRepository.findById(newJobId)
            .orElseThrow(() -> new ResourceNotFoundException("Job not found for ID: " + newJobId));
    Departments newDepartment = departmentsRepository.findById(newDepartmentId)
            .orElseThrow(() -> new ResourceNotFoundException("Department not found for ID: " + newDepartmentId));

    // Step 4: Check if Job and Department Are Changing
    if (!currentJob.equals(newJob) || !currentDepartment.equals(newDepartment)) {
        // Update end date for current Job History
        JobHistory latestJobHistory = jobHistoryRepository.findLatestJobHistByEmployeeId(employeeId);
        if (latestJobHistory != null) {
            latestJobHistory.setEndDate(LocalDate.now());
            jobHistoryRepository.save(latestJobHistory);
        }

        // Update Employee's Job and Department
        employee.setJob(newJob);
        employee.setDepartment(newDepartment);
        employeesRepository.save(employee);

        // Step 5: Create a New JobHistory Entry
        JobHistory newJobHistory = new JobHistory();
        newJobHistory.setEmployee(employee);
        newJobHistory.setJob(newJob);
        newJobHistory.setDepartment(newDepartment);
        newJobHistory.setStartDate(LocalDate.now());

        // Save and Return New Job History
        return jobHistoryRepository.save(newJobHistory);
    } else {
        throw new IllegalArgumentException("New job and department are the same as the current ones.");
    }
}





*/





        
    


  /*

    // 3. Add a new job history entry
    @Transactional
    public String addJobHistory(Integer empId, LocalDate startDate, String jobId, Long departmentId) {
        Employees employee = employeesRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + empId));
        // Rest of the code...
    }

   /* @Transactional
    public String addJobHistory(Long empId, LocalDate startDate, String jobId, Long departmentId) {
        Employees employee = employeesRepository.findById(empId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        Jobs job = jobsRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));
        Departments department = departmentsRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found"));

        JobHistory newHistory = new JobHistory(employee, startDate, LocalDate.now(), job, department);
        jobHistoryRepository.save(newHistory);
        return "Record Created Successfully";
    }

    // 4. Update job history with end date
    @Transactional
    public String modifyJobHistoryEndDate(Long empId, LocalDate endDate) {
        List<JobHistory> histories = jobHistoryRepository.findByEmployeeId(empId);
        if (histories.isEmpty()) {
            throw new ResourceNotFoundException("No job history found for employee with ID: " + empId);
        }

        // Update the latest job history entry
        JobHistory latestHistory = histories.get(histories.size() - 1);
        latestHistory.setEndDate(endDate);
        jobHistoryRepository.save(latestHistory);

        return "Record Created Successfully";
    }
    */



