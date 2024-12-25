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

import com.cg.humanresource.dto.JobsDTO;
import com.cg.humanresource.entity.Jobs;
import com.cg.humanresource.exception.JobNotFoundException;
import com.cg.humanresource.exception.ValidationNotFoundException;
import com.cg.humanresource.service.JobsService;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1/job")
public class JobsController {
	@Autowired
	JobsService jobsService;
	
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<JobsDTO>> getAllJobs() throws JobNotFoundException {
	    List<JobsDTO> jobs = jobsService.getAllJobs();
	    return new ResponseEntity<>(jobs, HttpStatus.OK);
	}

	@PostMapping(consumes="application/json")
	public ResponseEntity<String> insertJob(@RequestBody Jobs jobs){
		boolean isJobAdded = jobsService.addJob(jobs); 
        if(isJobAdded){
            return ResponseEntity.status(HttpStatus.CREATED).body("Record Created Successfully");
        } 
        else{
        	throw new ValidationNotFoundException();
        }
	}
	@PutMapping(consumes="application/json")
	public ResponseEntity<String> modifyJob(@RequestBody Jobs jobs){
	     boolean isJobUpdated = jobsService.modifyJob(jobs);
	     if(isJobUpdated){
	    	 return ResponseEntity.status(HttpStatus.OK).body("Record Modified Successfully");
	     } 
	     else{
	    	 throw new ValidationNotFoundException();
	     }
	}
	@PutMapping(value="/{jobId}/{minSalary}/{maxSalary}")
	public HttpStatus modifySalary(@PathVariable String jobId, @PathVariable Double minSalary, @PathVariable Double maxSalary){
		return jobsService.modifySalary(minSalary,maxSalary,jobId) ? HttpStatus.OK : HttpStatus.NOT_MODIFIED;
	}
	@DeleteMapping(value="/{jobId}")
	public ResponseEntity<String> deleteJobById(@PathVariable String jobId){
        boolean isJobDeleted = jobsService.deleteJobById(jobId);
        if(isJobDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Record deleted Successfully");
        } 
        else{
        	throw new ValidationNotFoundException();
        }
	}
	@GetMapping(value = "/{jobId}", produces = "application/json")
    public ResponseEntity<JobsDTO> getJobById(@PathVariable String jobId) throws JobNotFoundException {
        JobsDTO job = jobsService.getJobById(jobId);
        return new ResponseEntity<>(job, HttpStatus.OK);
    }
}