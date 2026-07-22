import request from '@/utils/request'

export const getOverview = (city = '上海') => {
  return request.get('/overview', { params: { city } })
}

export const getIndustryDistribution = () => {
  return request.get('/industry/distribution')
}

export const getDistrictDistribution = (city = '上海') => {
  return request.get('/district/distribution', { params: { city } })
}

export const getSalaryByJobName = () => {
  return request.get('/salary/job')
}

export const getSalaryPercentile = (jobName) => {
  return request.get('/salary/percentile', { params: { jobName } })
}

export const getKeywordSalaryTrend = () => {
  return request.get('/keyword/salary-trend')
}

export const getITSectorSalaryTrend = () => {
  return request.get('/it-sector/salary-trend')
}

export const getIndustryWorkYearDistribution = () => {
  return request.get('/industry/work-year')
}

export const getTopKeywords = () => {
  return request.get('/keyword/top10')
}

export const getSalaryByCompanySize = () => {
  return request.get('/company-size/salary')
}

export const getRecommendJobs = (keywords) => {
  return request.get('/job/recommend', { params: { keywords } })
}

export const getJobPage = (params) => {
  return request.get('/job/page', { params })
}