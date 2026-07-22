package com.example.jobanalysis.dto;

public class CompanySizeSalaryDTO {

    private String companySize;
    private Double avgSalaryMin;
    private Double avgSalaryMax;
    private Long count;

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public Double getAvgSalaryMin() {
        return avgSalaryMin;
    }

    public void setAvgSalaryMin(Double avgSalaryMin) {
        this.avgSalaryMin = avgSalaryMin;
    }

    public Double getAvgSalaryMax() {
        return avgSalaryMax;
    }

    public void setAvgSalaryMax(Double avgSalaryMax) {
        this.avgSalaryMax = avgSalaryMax;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}