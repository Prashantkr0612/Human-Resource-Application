package com.cg.humanresource.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.humanresource.entity.Locations;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationsRepository extends JpaRepository<Locations, Long> {
	
}
