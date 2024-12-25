package com.cg.humanresource.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.cg.humanresource.entity.Jobs;

@Repository
public interface JobsRepository extends JpaRepository<Jobs,String>{
	@Modifying
	@Transactional
	@Query(nativeQuery=true,value="update jobs set min_salary=?1, max_salary=?2 where job_id=?3")
	int updateBalanceByUsername(Double minSalary,Double maxSalary,String jobId);
	
	 @Query(nativeQuery=true,value="select j from jobs j where j.job_id not in "
	 		+ "(select e.job_id from employees e where e.job_id is not null)")
	 List<Object> findAllOpenPositions();
	 
	 @Query(nativeQuery=true,value="select j from jobs j "
	 		+ "where j.job_id not in "
	 		+ "(select e.job_id from employees e where e.job_id is not null and e.department_id = ?1)")
	 List<Object> findAllOpenPositionsByDepartmentId(int deptId);
}