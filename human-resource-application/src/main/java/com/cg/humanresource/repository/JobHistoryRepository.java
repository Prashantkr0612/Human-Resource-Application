package com.cg.humanresource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cg.humanresource.entity.JobHistory;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface JobHistoryRepository extends JpaRepository<JobHistory, LocalDate> {

    @Query("SELECT jh FROM JobHistory jh WHERE jh.employee.employeeId = :employeeId ORDER BY jh.startDate DESC")
    List<JobHistory> findByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("SELECT jh FROM JobHistory jh WHERE jh.employee.employeeId = :employeeId")
    JobHistory findCurrentJobHistoryByEmployeeId(@Param("employeeId") Integer employeeId);
}


 

 