package com.example.jobanalysis.dto;

import java.util.Date;

public class ITSectorSalaryTrendDTO {

    private Date publishDate;
    private Double avgSalary;

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Double getAvgSalary() {
        return avgSalary;
    }

    public void setAvgSalary(Double avgSalary) {
        this.avgSalary = avgSalary;
    }
}