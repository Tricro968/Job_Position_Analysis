package com.example.jobanalysis.dto;

public class DistrictDistributionDTO {

    private String district;
    private Long count;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}