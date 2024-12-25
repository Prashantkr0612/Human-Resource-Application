package com.cg.humanresource.entity;
 

 
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
 
@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="startDate")
@Table(name = "job_history")
public class JobHistory {
	
	@Id
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
	private Employees employee;
	
	
	@Column(name = "end_date")
	private LocalDate endDate;
	
	@ManyToOne
	@JoinColumn(name = "job_id", referencedColumnName = "job_id")
//	@JsonManagedReference
	private Jobs job;
	
	@ManyToOne
	@JoinColumn(name = "department_id", referencedColumnName = "department_id")
//	 @JsonBackReference
	private Departments department;  
 
	public JobHistory() {
		super();
	}
 
	public JobHistory(Employees employee, LocalDate startDate, LocalDate endDate, Jobs job,
			Departments department) {
		super();
		this.employee = employee;
		this.startDate = startDate;
		this.endDate = endDate;
		this.job = job;
		this.department = department;
	}
 
	public Employees getEmployee() {
		return employee;
	}
 
	public void setEmployee(Employees employee) {
		this.employee = employee;
	}
 
	public LocalDate getStartDate() {
		return startDate;
	}
 
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
 
	public LocalDate getEndDate() {
		return endDate;
	}
 
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
 
	public Jobs getJob() {
		return job;
	}
 
	public void setJob(Jobs job) {
		this.job = job;
	}
 
	public Departments getDepartment() {
		return department;
	}
 
	public void setDepartment(Departments department) {
		this.department = department;
	}
 
	@Override
	public String toString() {
		return "JobHistoryEntity [employee=" + employee + ", startDate=" + startDate + ", endDate=" + endDate + ", job="
				+ job + ", department=" + department + "]";
	}
}