package com.example.jobanalysis.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jobanalysis.dto.*;
import com.example.jobanalysis.entity.JobEtl;
import com.example.jobanalysis.mapper.JobEtlMapper;
import com.example.jobanalysis.service.JobAnalysisService;
import com.example.jobanalysis.vo.OverviewVO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobAnalysisServiceImpl implements JobAnalysisService {

    private final JobEtlMapper jobEtlMapper;

    public JobAnalysisServiceImpl(JobEtlMapper jobEtlMapper) {
        this.jobEtlMapper = jobEtlMapper;
    }

    @Override
    public OverviewVO getOverview(String city) {
        OverviewVO overview = new OverviewVO();
        overview.setTotalJobs(jobEtlMapper.getTotalJobs(city));
        overview.setAvgSalary(jobEtlMapper.getAvgSalary(city));
        overview.setTopKeywords(new ArrayList<>(jobEtlMapper.getTopKeywords()));
        return overview;
    }

    @Override
    public List<IndustryDistributionDTO> getIndustryDistribution() {
        return jobEtlMapper.getIndustryDistribution();
    }

    @Override
    public List<DistrictDistributionDTO> getDistrictDistribution(String city) {
        return jobEtlMapper.getDistrictDistribution(city);
    }

    @Override
    public List<Map<String, Object>> getSalaryByJobName() {
        return jobEtlMapper.getSalaryByJobName();
    }

    @Override
    public List<IndustryWorkYearDTO> getIndustryWorkYearDistribution() {
        return jobEtlMapper.getIndustryWorkYearDistribution();
    }

    @Override
    public List<KeywordDTO> getTopKeywords() {
        return jobEtlMapper.getTopKeywords();
    }

    @Override
    public List<CompanySizeSalaryDTO> getSalaryByCompanySize() {
        return jobEtlMapper.getSalaryByCompanySize();
    }

    @Override
    public List<JobEtl> getRecommendJobs(String keywords) {
        if (keywords == null || keywords.isEmpty()) {
            return List.of();
        }
        String[] keywordArray = keywords.split(",");
        List<JobEtl> results = jobEtlMapper.getRecommendJobs(keywordArray[0].trim());
        return results;
    }

    @Override
    public Map<String, Object> getSalaryPercentile(String jobName) {
        List<Map<String, Object>> salaryData = jobEtlMapper.getSalaryData(jobName);
        if (salaryData == null || salaryData.isEmpty()) {
            return new HashMap<>();
        }
        List<Integer> salaries = salaryData.stream()
                .map(m -> (Integer) m.get("salary_min"))
                .sorted()
                .toList();
        
        int size = salaries.size();
        Map<String, Object> percentile = new HashMap<>();
        percentile.put("min", salaries.get(0));
        percentile.put("25%", size > 3 ? salaries.get((int) (size * 0.25)) : salaries.get(0));
        percentile.put("50%", salaries.get((int) (size * 0.5)));
        percentile.put("75%", size > 3 ? salaries.get((int) (size * 0.75)) : salaries.get(size - 1));
        percentile.put("max", salaries.get(size - 1));
        return percentile;
    }

    @Override
    public List<KeywordSalaryTrendDTO> getKeywordSalaryTrend() {
        return jobEtlMapper.getKeywordSalaryTrend();
    }

    @Override
    public List<ITSectorSalaryTrendDTO> getITSectorSalaryTrend() {
        return jobEtlMapper.getITSectorSalaryTrend();
    }

    @Override
    public Page<JobEtl> getJobPage(JobQueryDTO queryDTO) {
        Page<JobEtl> page = new Page<>(queryDTO.getPage(), queryDTO.getPageSize());
        return jobEtlMapper.selectPageByCondition(page, queryDTO.getJobName(), queryDTO.getCity(),
                queryDTO.getDistrict(), queryDTO.getDegree(), queryDTO.getCompanySize());
    }
}