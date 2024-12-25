package com.cg.humanresource.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.humanresource.entity.Users;

public interface UsersRepository extends JpaRepository<Users,String> {

	@Query(value="SELECT * FROM users WHERE username = ?1 AND pass_word = ?2;",nativeQuery=true)
    Users findByUsernameAndPassword(String username,String password);
}
