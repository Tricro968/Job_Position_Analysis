package com.example.jobanalysis.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jobanalysis.dto.*;
import com.example.jobanalysis.entity.JobEtl;
import com.example.jobanalysis.service.JobAnalysisService;
import com.example.jobanalysis.vo.OverviewVO;
import com.example.jobanalysis.vo.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "招聘数据分析", description = "招聘数据查询与分析接口")
public class JobAnalysisController {

    private final JobAnalysisService jobAnalysisService;

    public JobAnalysisController(JobAnalysisService jobAnalysisService) {
        this.jobAnalysisService = jobAnalysisService;
    }

    @GetMapping("/overview")
    @Operation(summary = "获取概览数据", description = "获取总岗位数、平均薪资、热门关键词TOP5")
    public Result<OverviewVO> getOverview(
            @Parameter(description = "城市名称") @RequestParam(defaultValue = "上海") String city) {
        return Result.success(jobAnalysisService.getOverview(city));
    }

    @GetMapping("/industry/distribution")
    @Operation(summary = "获取产业类型分布", description = "按产业类型统计岗位数量")
    public Result<List<IndustryDistributionDTO>> getIndustryDistribution() {
        return Result.success(jobAnalysisService.getIndustryDistribution());
    }

    @GetMapping("/district/distribution")
    @Operation(summary = "获取地区分布", description = "按行政区统计岗位数量")
    public Result<List<DistrictDistributionDTO>> getDistrictDistribution(
            @Parameter(description = "城市名称") @RequestParam(defaultValue = "上海") String city) {
        return Result.success(jobAnalysisService.getDistrictDistribution(city));
    }

    @GetMapping("/salary/job")
    @Operation(summary = "按岗位统计薪资", description = "获取各岗位平均薪资")
    public Result<List<Map<String, Object>>> getSalaryByJobName() {
        return Result.success(jobAnalysisService.getSalaryByJobName());
    }

    @GetMapping("/salary/percentile")
    @Operation(summary = "获取薪资分位数", description = "获取指定岗位的薪资分位数")
    public Result<Map<String, Object>> getSalaryPercentile(
            @Parameter(description = "岗位名称") @RequestParam String jobName) {
        return Result.success(jobAnalysisService.getSalaryPercentile(jobName));
    }

    @GetMapping("/keyword/salary-trend")
    @Operation(summary = "关键词薪资趋势", description = "按关键词分组的薪资趋势")
    public Result<List<KeywordSalaryTrendDTO>> getKeywordSalaryTrend() {
        return Result.success(jobAnalysisService.getKeywordSalaryTrend());
    }

    @GetMapping("/it-sector/salary-trend")
    @Operation(summary = "IT行业薪资趋势", description = "按发布日期统计平均薪资")
    public Result<List<ITSectorSalaryTrendDTO>> getITSectorSalaryTrend() {
        return Result.success(jobAnalysisService.getITSectorSalaryTrend());
    }

    @GetMapping("/industry/work-year")
    @Operation(summary = "产业工作经验分布", description = "按产业类型和工作经验统计岗位数量")
    public Result<List<IndustryWorkYearDTO>> getIndustryWorkYearDistribution() {
        return Result.success(jobAnalysisService.getIndustryWorkYearDistribution());
    }

    @GetMapping("/keyword/top10")
    @Operation(summary = "热门关键词", description = "获取热门关键词TOP10")
    public Result<List<KeywordDTO>> getTopKeywords() {
        return Result.success(jobAnalysisService.getTopKeywords());
    }

    @GetMapping("/company-size/salary")
    @Operation(summary = "企业规模薪资关系", description = "获取不同企业规模的薪资情况")
    public Result<List<CompanySizeSalaryDTO>> getSalaryByCompanySize() {
        return Result.success(jobAnalysisService.getSalaryByCompanySize());
    }

    @GetMapping("/job/recommend")
    @Operation(summary = "岗位推荐", description = "基于关键词推荐相似岗位")
    public Result<List<JobEtl>> getRecommendJobs(
            @Parameter(description = "关键词，多个用逗号分隔") @RequestParam String keywords) {
        return Result.success(jobAnalysisService.getRecommendJobs(keywords));
    }

    @GetMapping("/job/page")
    @Operation(summary = "岗位分页查询", description = "按条件分页查询岗位列表")
    public Result<Page<JobEtl>> getJobPage(JobQueryDTO queryDTO) {
        Page<JobEtl> page = jobAnalysisService.getJobPage(queryDTO);
        return Result.success(page, page.getTotal());
    }
}