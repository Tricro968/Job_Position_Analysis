package com.example.jobanalysis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.jobanalysis.dto.*;
import com.example.jobanalysis.entity.JobEtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface JobEtlMapper extends BaseMapper<JobEtl> {

    @Select("SELECT industry, COUNT(*) as count FROM job_etl WHERE industry IS NOT NULL AND industry != '' GROUP BY industry ORDER BY count DESC")
    List<IndustryDistributionDTO> getIndustryDistribution();

    @Select("SELECT district, COUNT(*) as count FROM job_etl WHERE city = #{city} AND district IS NOT NULL AND district != '' GROUP BY district ORDER BY count DESC")
    List<DistrictDistributionDTO> getDistrictDistribution(@Param("city") String city);

    @Select("SELECT job_name, AVG(salary_min) as avg_salary_min, AVG(salary_max) as avg_salary_max FROM job_etl GROUP BY job_name")
    List<Map<String, Object>> getSalaryByJobName();

    @Select("SELECT industry, work_year, COUNT(*) as count FROM job_etl WHERE industry IS NOT NULL AND industry != '' AND work_year IS NOT NULL AND work_year != '' GROUP BY industry, work_year")
    List<IndustryWorkYearDTO> getIndustryWorkYearDistribution();

    @Select("SELECT keyword, COUNT(*) as count FROM job_etl WHERE keyword IS NOT NULL AND keyword != '' GROUP BY keyword ORDER BY count DESC LIMIT 10")
    List<KeywordDTO> getTopKeywords();

    @Select("SELECT company_size, AVG(salary_min) as avg_salary_min, AVG(salary_max) as avg_salary_max, COUNT(*) as count FROM job_etl WHERE company_size IS NOT NULL AND company_size != '' GROUP BY company_size")
    List<CompanySizeSalaryDTO> getSalaryByCompanySize();

    @Select("SELECT job_name, salary_min, salary_max, city, district, company_name, keyword FROM job_etl WHERE keyword LIKE CONCAT('%', #{keyword}, '%') LIMIT 10")
    List<JobEtl> getRecommendJobs(@Param("keyword") String keyword);

    @Select("SELECT COUNT(*) as total FROM job_etl WHERE city = #{city}")
    Integer getTotalJobs(@Param("city") String city);

    @Select("SELECT AVG((salary_min + salary_max) / 2) as avg_salary FROM job_etl WHERE city = #{city}")
    Double getAvgSalary(@Param("city") String city);

    @Select("SELECT keyword, MIN(salary_min) as min_salary, COUNT(*) as count FROM job_etl WHERE keyword IS NOT NULL AND keyword != '' AND salary_min IS NOT NULL GROUP BY keyword ORDER BY count DESC")
    List<KeywordSalaryTrendDTO> getKeywordSalaryTrend();
    
    @Select("SELECT publish_date, AVG((salary_min + salary_max) / 2) as avg_salary FROM job_etl WHERE publish_date IS NOT NULL AND salary_min IS NOT NULL AND salary_max IS NOT NULL GROUP BY publish_date ORDER BY publish_date")
    List<ITSectorSalaryTrendDTO> getITSectorSalaryTrend();

    @Select("SELECT salary_min, salary_max FROM job_etl WHERE job_name = #{jobName} ORDER BY salary_min")
    List<Map<String, Object>> getSalaryData(@Param("jobName") String jobName);

    Page<JobEtl> selectPageByCondition(Page<JobEtl> page, @Param("jobName") String jobName, @Param("city") String city, @Param("district") String district, @Param("degree") String degree, @Param("companySize") String companySize);
}