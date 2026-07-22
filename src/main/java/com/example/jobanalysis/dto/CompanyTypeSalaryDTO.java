package com.example.jobanalysis.dto;

public class CompanyTypeSalaryDTO {

    private String companyType;
    private Double avgSalaryMin;
    private Double avgSalaryMax;
    private Long count;

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
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