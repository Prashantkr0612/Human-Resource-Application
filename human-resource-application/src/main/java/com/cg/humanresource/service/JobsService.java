package com.cg.humanresource.service;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.humanresource.dto.JobsDTO;
import com.cg.humanresource.entity.Jobs;
import com.cg.humanresource.exception.JobNotFoundException;
import com.cg.humanresource.exception.ValidationNotFoundException;
import com.cg.humanresource.repository.JobsRepository;
@Service
public class JobsService {
	@Autowired
	JobsRepository jobsRepository;
	
	private JobsDTO convertToDto(Jobs job) {
	    List<Integer> employeeIds = job.getEmployees().stream().map(employee -> employee.getEmployeeId()).collect(Collectors.toList());
	                                

	    List<Integer> jobHistoryIds = job.getJobHistory().stream().map(history -> history.getEmployee().getEmployeeId()).collect(Collectors.toList());
	    return new JobsDTO(job.getJobId(), job.getJobTitle(), job.getMinSalary(), job.getMaxSalary(), employeeIds, jobHistoryIds);
	}

	@Transactional(readOnly = true)
	public List<JobsDTO> getAllJobs() throws JobNotFoundException {
	    List<Jobs> jobs = jobsRepository.findAll();
	    if (jobs.isEmpty()) {
	        throw new JobNotFoundException("No jobs found");
	    }
	    return jobs.stream()
	               .map(this::convertToDto)
	               .collect(Collectors.toList());
	}

	@Transactional
	public boolean addJob(Jobs j) throws ValidationNotFoundException {
	    if (j == null) {
	        throw new ValidationNotFoundException("Validation Failed");
	    }
	    if (j.getJobId() == null || j.getJobId().isEmpty()) {
	        throw new ValidationNotFoundException("Validation Failed");
	    }
	    if (j.getJobTitle() == null || j.getJobTitle().isEmpty()) {
	        throw new ValidationNotFoundException("Validation Failed");
	    }
	    if (jobsRepository.findById(j.getJobId()).isPresent()) {
	        throw new ValidationNotFoundException("Validation Failed");
	    }
	    return jobsRepository.save(j) != null;
	}
	@Transactional
	public boolean modifyJob(Jobs j) throws ValidationNotFoundException{
		if (!jobsRepository.existsById(j.getJobId())) {
	        throw new ValidationNotFoundException("Validation Failed");
	    }
	    return jobsRepository.save(j) != null;
	}
	@Transactional
	public boolean modifySalary(Double minSalary,Double maxSalary,String jobId) throws ValidationNotFoundException{
		if (minSalary == null || maxSalary == null) {
	        return false;
	    }
	    int updatedRows = jobsRepository.updateBalanceByUsername(minSalary, maxSalary, jobId);
	    if (updatedRows == 0) {
	        throw new ValidationNotFoundException("No Job Found");
	    }
	    return updatedRows > 0;
	}
	@Transactional
	public boolean deleteJobById(String jobId) throws ValidationNotFoundException{
		if (!jobsRepository.existsById(jobId)) {
	        throw new ValidationNotFoundException("No Job found");
	    }
		long count = jobsRepository.count();
		jobsRepository.deleteById(jobId);
		return jobsRepository.count() < count;
	}
	@Transactional(readOnly = true)
	public JobsDTO getJobById(String jobId) throws JobNotFoundException {
	    Jobs job = jobsRepository.findById(jobId).orElseThrow(() -> new JobNotFoundException());
	    return convertToDto(job);
	}
}