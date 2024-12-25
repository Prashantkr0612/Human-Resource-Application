package com.cg.humanresource.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.humanresource.entity.Employees;

@Repository
public interface EmployeesRepository extends JpaRepository<Employees,Integer>{
	
	List<Employees> findByFirstName(String firstName);
	Employees findByEmail(String email);
	Employees findByPhoneNumber(String phoneNumber);
	
	@Query(nativeQuery=true,value="select * from employees where commission_pct is null;")
	List<Employees> findAllEmployeeWithNoCommission();
	
	@Query(nativeQuery=true,value="select * from employees where department_id=?1")
	List<Employees> findByDepartmentId(int deptId);
	
	@Query(nativeQuery=true,value="select d.department_name, count(e.employee_id) from employees e join departments d on e.department_id = d.department_id group by d.department_name;")
	List<Map<String, Integer>> countAllEmployeesGroupByDepartment();
	
	@Query(nativeQuery=true,value="select distinct *"
			+ " from employees e"
			+ " where e.employee_id in (select distinct manager_id from employees where manager_id is not null);")
	List<Employees> findAllManagers();

	@Query(nativeQuery=true,value="select count(e.employee_id), l.location_id "
			+ "from employees e "
			+ "join departments d on e.department_id = d.department_id "
			+ "join locations l on d.location_id=l.location_id "
			+ "group by l.location_id;")
	List<Map<Integer, Integer>> countAllEmployeesGroupByLocation();
	
	
	
	
}
