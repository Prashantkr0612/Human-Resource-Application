package com.cg.humanresource.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.humanresource.entity.Countries;

public interface CountriesRepository extends JpaRepository<Countries,String>
{

}
