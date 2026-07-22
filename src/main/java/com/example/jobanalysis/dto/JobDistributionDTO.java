package com.example.jobanalysis.dto;

public class JobDistributionDTO {

    private String jobName;
    private Long count;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}