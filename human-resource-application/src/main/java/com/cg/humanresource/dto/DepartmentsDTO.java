package com.cg.humanresource.dto;

import java.util.List;

public class DepartmentsDTO {

    private Long departmentId;
    private String departmentName;
    private Integer managerId;
    private Long locationId;
    private List<Integer> employeeIds;

    public DepartmentsDTO() {}

    public DepartmentsDTO(Long departmentId, String departmentName, Integer managerId, Long locationId, List<Integer> employeeIds) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.locationId = locationId;
        this.employeeIds = employeeIds;
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

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    @Override
    public String toString() {
        return "DepartmentsDTO{" +
                "departmentId=" + departmentId +
                ", departmentName='" + departmentName + '\'' +
                ", managerId=" + managerId +
                ", locationId=" + locationId +
                ", employeeIds=" + employeeIds +
                '}';
    }
}
