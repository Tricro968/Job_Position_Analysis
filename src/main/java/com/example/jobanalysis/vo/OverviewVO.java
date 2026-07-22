package com.example.jobanalysis.vo;

import com.example.jobanalysis.dto.KeywordDTO;

import java.util.List;

public class OverviewVO {

    private Integer totalJobs;
    private Double avgSalary;
    private List<KeywordDTO> topKeywords;

    public Integer getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(Integer totalJobs) {
        this.totalJobs = totalJobs;
    }

    public Double getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(Double avgSalary) {
        this.avgSalary = avgSalary;
    }

    public List<KeywordDTO> getTopKeywords() {
        return topKeywords;
    }

    public void setTopKeywords(List<KeywordDTO> topKeywords) {
        this.topKeywords = topKeywords;
    }
}