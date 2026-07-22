<script setup>
import { ref, onMounted } from 'vue'
import { getOverview } from '@/api/jobAnalysis'
import StatCard from '@/components/StatCard.vue'
import JobDistributionChart from '@/components/charts/JobDistributionChart.vue'
import SalaryTrendChart from '@/components/charts/SalaryTrendChart.vue'
import DegreeDistributionChart from '@/components/charts/DegreeDistributionChart.vue'
import WorkYearChart from '@/components/charts/WorkYearChart.vue'
import KeywordChart from '@/components/charts/KeywordChart.vue'
import CompanySizeSalaryChart from '@/components/charts/CompanySizeSalaryChart.vue'
import ITSectorSalaryTrendChart from '@/components/charts/ITSectorSalaryTrendChart.vue'

const overview = ref({
  totalJobs: 0,
  avgSalary: 0,
  topKeywords: []
})

const loading = ref(true)

const fetchOverview = async () => {
  try {
    const data = await getOverview()
    overview.value = data
    overview.value.avgSalary = Math.round(data.avgSalary / 1000)
  } catch (error) {
    console.error('获取概览数据失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchOverview()
})
</script>

<template>
  <div class="dashboard-container">
    <div class="nav-bar">
      <div class="nav-links">
        <router-link to="/">📊 数据概览</router-link>
        <router-link to="/filter">🔍 数据筛选</router-link>
        <router-link to="/recommend">🎯 岗位推荐</router-link>
      </div>
    </div>
    
    <div class="dashboard-header">
      <h1>招聘数据分析平台</h1>
      <p>实时监控岗位市场动态，助力职业发展决策</p>
    </div>
    
    <div v-if="loading" class="loading-text">
      <el-loading text="数据加载中..." />
    </div>
    
    <div v-else>
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6">
          <StatCard 
            icon="💼" 
            :value="overview.totalJobs" 
            label="总岗位数" 
            color="#667eea" 
          />
        </el-col>
        <el-col :span="6">
          <StatCard 
            icon="💰" 
            :value="overview.avgSalary + 'K'" 
            label="平均薪资" 
            color="#f093fb" 
          />
        </el-col>
        <el-col :span="6">
          <StatCard 
            icon="🔥" 
            :value="overview.topKeywords.length > 0 ? overview.topKeywords[0].keyword : '-'" 
            label="最热关键词" 
            color="#4facfe" 
          />
        </el-col>
        <el-col :span="6">
          <StatCard 
            icon="📅" 
            value="实时" 
            label="数据更新" 
            color="#43e97b" 
          />
        </el-col>
      </el-row>

      <div class="chart-list">
        <JobDistributionChart />
        <SalaryTrendChart />
        <DegreeDistributionChart />
        <WorkYearChart />
        <KeywordChart />
        <CompanySizeSalaryChart />
        <ITSectorSalaryTrendChart />
      </div>
    </div>
    
    <div class="layout-footer">
      © 2026 招聘数据分析平台 | 数据来源：猎聘网
    </div>
  </div>
</template>

<style scoped>
.loading-text {
  text-align: center;
  padding: 40px;
}

.chart-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
}
</style>