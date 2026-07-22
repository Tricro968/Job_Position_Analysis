package com.example.jobanalysis.dto;

public class IndustryDistributionDTO {

    private String industry;
    private Long count;

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}