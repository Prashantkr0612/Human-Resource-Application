package com.cg.humanresource.entity;
 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
 
@Entity
@Table(name="regions")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="regionId")
public class Regions
{
	@Id
	@Column(name="region_id",nullable=false)
	private int regionId;
	
	@Column(nullable=false)
    @NotNull(message = "Region name cannot be null")
    @NotBlank(message = "Region name cannot be blank")
    private String regionName;
	
	@OneToMany(mappedBy="regions",cascade = CascadeType.REMOVE)
	List<Countries> Countries;
	
	public Regions() {}
 
	public Regions(int regionId, @NotBlank String regionName,List<Countries> Countries)
	{
		this.regionId = regionId;
		this.regionName = regionName;
		this.Countries = Countries;
	}
 
	public int getRegionId()
	{
		return regionId;
	}
 
	public void setRegionId(int regionId)
	{
		this.regionId = regionId;
	}
 
	public String getRegionName()
	{
		return regionName;
	}
 
	public void setRegionName(String regionName)
	{
		this.regionName = regionName;
	}
 
	public List<Countries> getCountries() {
		return Countries;
	}
 
	public void setCountries(List<Countries> countries) {
		Countries = countries;
	}
	
	
}