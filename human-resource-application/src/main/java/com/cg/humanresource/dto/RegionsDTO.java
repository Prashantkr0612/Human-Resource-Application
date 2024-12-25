package com.cg.humanresource.dto;

import java.util.List;

public class RegionsDTO {

    private int regionId;
    private String regionName;
    private List<String> countries; 

    public RegionsDTO() {}

    public RegionsDTO(int regionId, String regionName, List<String> countries) {
        this.regionId = regionId;
        this.regionName = regionName;
        this.countries = countries;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }
}
