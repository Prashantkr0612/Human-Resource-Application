package com.cg.humanresource.entity;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "locations")
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="locationId")
public class Locations {
	
    @Id
    @Column(name = "location_id")
    private Long locationId;
    
    @Column(name = "street_address")
    private String streetAddress;
    
    @Column(name = "postal_code")
    private String postalCode;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state_province")
    private String stateProvince;
    
    @ManyToOne
    @JoinColumn(name = "country_id")
//    @JsonManagedReference 
    private Countries country;
    
    @OneToMany(mappedBy = "location")
//    @JsonBackReference
    private List<Departments> departments;
    
    public Locations() {}
	public Locations(Long locationId, String streetAddress, String postalCode, String city, String stateProvince,
			Countries country, List<Departments> departments) {
		this.locationId = locationId;
		this.streetAddress = streetAddress;
		this.postalCode = postalCode;
		this.city = city;
		this.stateProvince = stateProvince;
		this.country = country;
		this.departments = departments;
	}
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateProvince() {
		return stateProvince;
	}
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}
	public Countries getCountry() {
		return country;
	}
	public void setCountry(Countries country) {
		this.country = country;
	}
	public List<Departments> getDepartments() {
		return departments;
	}
	public void setDepartments(List<Departments> departments) {
		this.departments = departments;
	}
}