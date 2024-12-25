package com.cg.humanresource.dto;

import java.util.List;

public class CountriesDTO {

    private String countryId;
    private String countryName;
    private int regionId; 
    private List<Long> locationIds;
    
    public CountriesDTO() {}

    public CountriesDTO(String countryId, String countryName, int regionId, List<Long> locationIds) {
        this.countryId = countryId;
        this.countryName = countryName;
        this.regionId = regionId;
        this.locationIds = locationIds;
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

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public List<Long> getLocationIds() {
        return locationIds;
    }

    public void setLocationIds(List<Long> locationIds) {
        this.locationIds = locationIds;
    }
}
