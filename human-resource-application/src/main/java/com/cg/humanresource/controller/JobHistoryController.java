package com.cg.humanresource.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.humanresource.entity.Departments;
import com.cg.humanresource.entity.Employees;
import com.cg.humanresource.entity.JobHistory;
import com.cg.humanresource.entity.Jobs;
import com.cg.humanresource.service.JobHistoryService;
@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/job_history")
public class JobHistoryController {

    @Autowired
    private JobHistoryService jobHistoryService;

    // Get Experience of Employee (GET)
    @GetMapping("/totalyearsofexperience/{empId}")
    public ResponseEntity<Map<String, Integer>> findExperienceOfEmployee(@PathVariable("empId") int empId) {
        Map<String, Integer> experience = jobHistoryService.findExperienceOfEmployee(empId);
        return new ResponseEntity<>(experience, HttpStatus.OK);
    }

    // List Employees with Less Than One Year Experience (GET)
    @GetMapping("/lessthanoneyearexperience")
    public ResponseEntity<List<Employees>> listAllEmployeesWithLessThanOneYearExperience() {
        List<Employees> employees = jobHistoryService.listAllEmployeesWithLessThanOneYearExperience();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    // Add Job History Entry (POST)
   /* @PostMapping("/{empId}/{startDate}/{jobId}/{departmentId}")
    public ResponseEntity<String> addJobHistory(@PathVariable int employeeId,
                                                @PathVariable LocalDate startDate,
                                                @PathVariable String jobId,
                                                @PathVariable Long departmentId) {
        JobHistory jobHistory = new JobHistory();
        jobHistory.setEmployee(new Employees(employeeId));
        jobHistory.setStartDate(startDate);
        jobHistory.setJob(new Jobs(jobId));
        jobHistory.setDepartment(new Departments(departmentId));

        jobHistoryService.createJobHistory(jobHistory);

        return new ResponseEntity<>("Record Created Successfully", HttpStatus.CREATED);
    }
    */  
    @PostMapping(value="/{empid}/{startdate}/{job_id}/{department_id}",consumes="application/json")
    public ResponseEntity<String> addJobHistory (@RequestBody JobHistory jobHistory,
            @PathVariable("empid") int empId,
            @PathVariable("startdate") LocalDate startDate,
            @PathVariable("job_id") String jobId,
            @PathVariable("department_id") Long departmentId) throws Exception {

             jobHistoryService.createJobHistory(jobHistory, empId, startDate, jobId, departmentId);
            return new ResponseEntity<>("Record created successfully", HttpStatus.CREATED);
        }
    

    // Update Job History End Date (PUT)
    @PutMapping("/{empId}/{endDate}")
    public ResponseEntity<String> updateJobHistoryEndDate(@RequestBody JobHistory jobHistory,@PathVariable Integer empId,
                                                          @PathVariable LocalDate endDate) {
        jobHistoryService.updateJobHistoryEndDate(jobHistory,empId, endDate);
        return new ResponseEntity<>("Job History Modified", HttpStatus.OK);
    }
}


/*
    // 3. Add a job history entry
    @PostMapping("/{empid}/{startdate}/{job_id}/{department_id}")
    public ResponseEntity<String> addJobHistory(
            @PathVariable("empid") Integer empId,
            @PathVariable("startdate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable("job_id") String jobId,
            @PathVariable("department_id") Long departmentId) {

        try {
            String response = jobHistoryService.addJobHistory(empId, startDate, jobId, departmentId);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>("{\"timeStamp\":\"" + LocalDate.now() + "\", \"message\":\"" + ex.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }

    // 4. Modify job history with end date
    @PutMapping("/{empid}/{enddate}")
    public ResponseEntity<String> modifyJobHistoryEndDate(
            @PathVariable("empid") Long empId,
            @PathVariable("enddate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        try {
            String response = jobHistoryService.modifyJobHistoryEndDate(empId, endDate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ResourceNotFoundException ex) {
            return new ResponseEntity<>("{\"timeStamp\":\"" + LocalDate.now() + "\", \"message\":\"" + ex.getMessage() + "\"}", HttpStatus.BAD_REQUEST);
        }
    }
}
*/
