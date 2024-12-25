package com.cg.humanresource.entity;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="jobId")
@Entity
public class Jobs {
    @Id
    @Column(name = "job_id", nullable = false)
    private String jobId;
    private String jobTitle;
    private double minSalary;
    private double maxSalary;
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    private List<Employees> employees;
    @OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE)
    private List<JobHistory> jobHistory;
    public Jobs() {
    	super();
    }
    public Jobs(String jobId, String jobTitle, double minSalary, double maxSalary, List<Employees> employees,
    		List<JobHistory> jobHistory) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.employees = employees;
        this.jobHistory = jobHistory;
    }
    public String getJobId() {
        return jobId;
    }
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public double getMinSalary() {
        return minSalary;
    }
    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }
    public double getMaxSalary() {
        return maxSalary;
    }
    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }
    public List<Employees> getEmployees() {
        return employees;
    }
    public void setEmployees(List<Employees> employees) {
        this.employees = employees;
    }
	public List<JobHistory> getJobHistory() {
		return jobHistory;
	}
	public void setJobHistory(List<JobHistory> jobHistory) {
		this.jobHistory = jobHistory;
	}
	@Override
	public String toString() {
		return "JobEntity [jobId=" + jobId + ", jobTitle=" + jobTitle + ", minSalary=" + minSalary + ", maxSalary=" + maxSalary+"]";
	}
}