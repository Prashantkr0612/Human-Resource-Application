package com.cg.humanresource.entity;
 
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
 
import java.util.List;
 
@Entity
@Table(name = "departments")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="departmentId")
public class Departments {
 
    @Id
	@Column(name = "department_id", nullable = false)
	@NotNull(message = "Department ID cannot be null")
	private Long departmentId;
 
	@Column(name = "department_name", nullable = false)
	@NotBlank(message = "Department name cannot be blank")
	private String departmentName;
	    
    @Column(name = "manager_id")
    private Integer managerId;
 
    @ManyToOne
    @JoinColumn(name = "location_id")
//    @JsonBackReference
    private Locations location;
 
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
//    @JsonBackReference
    private List<Employees> employees;
 
    public Departments() {}
 
	public Departments(Long departmentId, String departmentName, Integer managerId, Locations location,
			List<Employees> employees) {
		this.departmentId = departmentId;
		this.departmentName = departmentName;
		this.managerId = managerId;
		this.location = location;
		this.employees = employees;
	}
 
	public Long getDepartmentId() {
		return departmentId;
	}
 
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
 
	public String getDepartmentName() {
		return departmentName;
	}
 
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
 
	public Integer getManagerId() {
		return managerId;
	}
 
	public void setManagerId(Integer managerId) {
		this.managerId = managerId;
	}
 
	public Locations getLocation() {
		return location;
	}
 
	public void setLocation(Locations location) {
		this.location = location;
	}
 
	public List<Employees> getEmployees() {
		return employees;
	}
 
	public void setEmployees(List<Employees> employees) {
		this.employees = employees;
	}
    
}