package com.cg.humanresource.entity;
 
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
 
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class,property="countryId")
@Entity
public class Countries {
 
    @Id
    @Column(nullable = false)
    @NotNull(message = "Country ID cannot be null")
    @NotBlank(message = "Country ID cannot be blank")
    private String countryId;
 
    @Column(nullable = false)
    @NotNull(message = "Country name cannot be null")
    @NotBlank(message = "Country name cannot be blank")
    private String countryName;
 
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Regions regions;
 
    @OneToMany(mappedBy = "country", cascade = CascadeType.REMOVE)
    private List<Locations> locations;
 
    public Countries() {}
 
    public Countries(@NotBlank String countryId, @NotBlank String countryName, Regions regions, List<Locations> locations) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.regions = regions;
        this.locations = locations;
    }
 
    public String getCountryId() {
        return countryId;
    }
 
    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }
 
    public String getCountryName() {
        return countryName;
    }
 
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
 
    public Regions getRegions() {
        return regions;
    }
 
    public void setRegions(Regions regions) {
        this.regions = regions;
    }
 
    public List<Locations> getLocations() {
        return locations;
    }
 
    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }
}
 
