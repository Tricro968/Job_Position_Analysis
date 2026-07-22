package com.example.jobanalysis.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jobanalysis.dto.*;
import com.example.jobanalysis.entity.JobEtl;
import com.example.jobanalysis.vo.OverviewVO;

import java.util.List;
import java.util.Map;

public interface JobAnalysisService {

    OverviewVO getOverview(String city);

    List<IndustryDistributionDTO> getIndustryDistribution();

    List<DistrictDistributionDTO> getDistrictDistribution(String city);

    List<Map<String, Object>> getSalaryByJobName();

    List<IndustryWorkYearDTO> getIndustryWorkYearDistribution();

    List<KeywordDTO> getTopKeywords();

    List<CompanySizeSalaryDTO> getSalaryByCompanySize();

    List<JobEtl> getRecommendJobs(String keywords);

    Map<String, Object> getSalaryPercentile(String jobName);

    List<KeywordSalaryTrendDTO> getKeywordSalaryTrend();

    List<ITSectorSalaryTrendDTO> getITSectorSalaryTrend();

    Page<JobEtl> getJobPage(JobQueryDTO queryDTO);
}