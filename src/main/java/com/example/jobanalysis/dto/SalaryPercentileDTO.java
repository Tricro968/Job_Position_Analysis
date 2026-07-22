package com.example.jobanalysis.dto;

public class SalaryPercentileDTO {

    private String jobName;
    private Integer min;
    private Integer p25;
    private Integer p50;
    private Integer p75;
    private Integer max;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getP25() {
        return p25;
    }

    public void setP25(Integer p25) {
        this.p25 = p25;
    }

    public Integer getP50() {
        return p50;
    }

    public void setP50(Integer p50) {
        this.p50 = p50;
    }

    public Integer getP75() {
        return p75;
    }

    public void setP75(Integer p75) {
        this.p75 = p75;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }
}