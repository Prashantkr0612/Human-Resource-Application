package com.cg.humanresource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.cg.humanresource.entity.Departments;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DepartmentsRepository extends JpaRepository<Departments, Long> {

    @Query(nativeQuery = true, value = "SELECT MAX(salary) AS max_salary FROM employees WHERE department_id = ?1")
    Double findMaxSalaryByDepartmentId(Long departmentId);

    @Query(nativeQuery = true, value = "SELECT MIN(salary) AS min_salary FROM employees WHERE department_id = ?1")
    Double findMinSalaryByDepartmentId(Long departmentId);
    
    @Transactional
    @Modifying
    @Query(nativeQuery=true,value="DELETE FROM Department d WHERE d.departmentId = :departmentId")
    void deleteDepartmentById(Long departmentId);
}
