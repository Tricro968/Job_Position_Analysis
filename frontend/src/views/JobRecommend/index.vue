<script setup>
import { ref } from 'vue'
import { getRecommendJobs } from '@/api/jobAnalysis'

const keywords = ref('')
const recommendJobs = ref([])
const loading = ref(false)

const handleRecommend = async () => {
  if (!keywords.value.trim()) {
    return
  }
  
  loading.value = true
  try {
    const data = await getRecommendJobs(keywords.value)
    recommendJobs.value = data
  } catch (error) {
    console.error('获取推荐岗位失败:', error)
  } finally {
    loading.value = false
  }
}

const getSalaryRange = (min, max) => {
  if (!min && !max) return '-'
  if (!max) return `${min}K+`
  if (!min) return `${max}K`
  return `${min}-${max}K`
}
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
      <h1>岗位推荐</h1>
      <p>输入关键词，智能推荐匹配岗位</p>
    </div>
    
    <div class="chart-card" style="margin-bottom: 20px;">
      <div class="chart-title">关键词搜索</div>
      <div style="display: flex; gap: 16px; align-items: center;">
        <el-input 
          v-model="keywords" 
          placeholder="请输入关键词，多个用逗号分隔（如：Java,SpringBoot）" 
          style="flex: 1; max-width: 600px;"
          @keyup.enter="handleRecommend"
        />
        <el-button type="primary" @click="handleRecommend" :loading="loading">
          🔍 搜索推荐
        </el-button>
      </div>
      <div style="margin-top: 16px; font-size: 14px; color: #666;">
        💡 提示：输入您熟悉的技术栈或岗位名称，系统会推荐匹配度高的岗位
      </div>
    </div>
    
    <div v-if="loading" style="text-align: center; padding: 40px;">
      <el-loading text="正在搜索匹配岗位..." />
    </div>
    
    <div v-else-if="recommendJobs.length > 0" class="job-cards">
      <div v-for="job in recommendJobs" :key="job.id" class="job-card">
        <div class="job-header">
          <h3>{{ job.jobName }}</h3>
          <span class="salary">{{ getSalaryRange(job.salaryMin, job.salaryMax) }}</span>
        </div>
        <div class="job-info">
          <div class="info-item">
            <span class="label">📍</span>
            <span>{{ job.city }} {{ job.district }}</span>
          </div>
          <div class="info-item">
            <span class="label">📚</span>
            <span>{{ job.degree }}</span>
          </div>
          <div class="info-item">
            <span class="label">💼</span>
            <span>{{ job.workYear }}</span>
          </div>
          <div class="info-item">
            <span class="label">🏢</span>
            <span>{{ job.companySize }}</span>
          </div>
        </div>
        <div class="company-name">{{ job.companyName }}</div>
        <div v-if="job.keyword" class="keywords">
          <span class="keyword-tag" v-for="kw in job.keyword.split(',')" :key="kw">{{ kw.trim() }}</span>
        </div>
      </div>
    </div>
    
    <div v-else-if="keywords.value && !loading" style="text-align: center; padding: 40px;">
      <div style="font-size: 48px; margin-bottom: 16px;">🔍</div>
      <div style="font-size: 18px; color: #666;">未找到匹配的岗位，请尝试其他关键词</div>
    </div>
    
    <div v-else style="text-align: center; padding: 40px;">
      <div style="font-size: 48px; margin-bottom: 16px;">🎯</div>
      <div style="font-size: 18px; color: #666;">输入关键词开始智能岗位推荐</div>
    </div>
    
    <div class="layout-footer">
      © 2026 招聘数据分析平台 | 数据来源：猎聘网
    </div>
  </div>
</template>

<style scoped>
.job-cards {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(320px, 1fr));
  gap: 20px;
}

.job-card {
  background: rgba(255, 255, 255, 0.95);
  border-radius: 12px;
  padding: 20px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s, box-shadow 0.3s;
}

.job-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.12);
}

.job-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
}

.job-header h3 {
  font-size: 18px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.salary {
  font-size: 20px;
  font-weight: 700;
  color: #667eea;
}

.job-info {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
  margin-bottom: 12px;
}

.info-item {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #666;
  gap: 4px;
}

.label {
  font-size: 14px;
}

.company-name {
  font-size: 14px;
  color: #999;
  margin-bottom: 12px;
}

.keywords {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.keyword-tag {
  background: rgba(102, 126, 234, 0.1);
  color: #667eea;
  padding: 4px 10px;
  border-radius: 12px;
  font-size: 12px;
}
</style>