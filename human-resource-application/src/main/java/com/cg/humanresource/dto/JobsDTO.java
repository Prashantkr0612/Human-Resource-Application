package com.cg.humanresource.dto;

import java.util.List;

public class JobsDTO {
    private String jobId;
    private String jobTitle;
    private double minSalary;
    private double maxSalary;

    // Optional: Include if you want to expose associated data
    private List<Integer> employeeIds;
    private List<Integer> jobHistoryIds;

    public JobsDTO() {
        super();
    }

    public JobsDTO(String jobId, String jobTitle, double minSalary, double maxSalary, List<Integer> employeeIds, List<Integer> jobHistoryIds) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.employeeIds = employeeIds;
        this.jobHistoryIds = jobHistoryIds;
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

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public List<Integer> getJobHistoryIds() {
        return jobHistoryIds;
    }

    public void setJobHistoryIds(List<Integer> jobHistoryIds) {
        this.jobHistoryIds = jobHistoryIds;
    }

    @Override
    public String toString() {
        return "JobsDTO [jobId=" + jobId + ", jobTitle=" + jobTitle + ", minSalary=" + minSalary + ", maxSalary=" + maxSalary + "]";
    }
}
