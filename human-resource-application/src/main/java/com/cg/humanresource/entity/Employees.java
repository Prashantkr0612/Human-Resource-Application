package com.cg.humanresource.entity;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="employeeId")
@Table(name = "employees")
public class Employees
{
	
	@Id
	@Column(name = "employee_id")
	private Integer employeeId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	private String email;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "hire_date")
	private LocalDate hireDate;
	
	private double salary;
	
	@Column(name = "commission_pct",nullable=true)
	private Double commissionPct;
	
	@ManyToOne
//	@JsonBackReference
	@JoinColumn(name = "manager_id", referencedColumnName = "employee_id")
	private Employees manager;
	
	@ManyToOne
//	@JsonBackReference 
	@JoinColumn(name = "department_id", referencedColumnName = "department_id")
	private Departments department;

	@ManyToOne
//	@JsonBackReference 
    @JoinColumn(name = "job_id", referencedColumnName = "job_id")
    private Jobs job;
	
	    
	public Employees() {
		super();
	}

	public Employees(int employeeId, String firstName, String lastName, String email, String phoneNumber, LocalDate hireDate,
			Jobs job, double salary, double commissionPct, Employees manager, Departments department) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.hireDate = hireDate;
		this.job= job;
		this.salary = salary;
		this.commissionPct = commissionPct;
		this.manager = manager;
		this.department = department;
	}

	
	public int getEmployeeId() {
		return employeeId;
	}

	

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Jobs getJob() {
		return job;
	}

	public void setJob(Jobs job) {
		this.job= job;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public Double getCommissionPct() {
		return commissionPct;
	}

	public void setCommissionPct(Double commissionPct) {
		this.commissionPct = commissionPct;
	}

	public Employees getManager() {
		return manager;
	}

	public void setManager(Employees manager) {
		this.manager = manager;
	}

	public Departments getDepartment() {
		return department;
	}

	public void setDepartment(Departments department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "EmployeeEntity [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", hireDate=" + hireDate + ", job=" + job+ ", salary=" + salary
				+ ", commissionPct=" + commissionPct + ", manager=" + manager + ", department=" + department
				+"]";
	}

}	