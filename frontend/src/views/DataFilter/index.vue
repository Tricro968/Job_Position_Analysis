<script setup>
import { ref, onMounted } from 'vue'
import { getJobPage } from '@/api/jobAnalysis'

const searchForm = ref({
  jobName: '',
  city: '上海',
  district: '',
  degree: '',
  companySize: ''
})

const tableData = ref([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(20)

const districts = ['浦东新区', '徐汇区', '静安区', '黄浦区', '虹口区', '杨浦区', '长宁区', '普陀区', '闵行区', '宝山区']
const degrees = ['本科', '硕士', '大专', '不限', '博士']
const companySizes = ['少于10人', '10-49人', '50-99人', '100-499人', '500-999人', '1000-9999人', '10000人以上']

const fetchData = async () => {
  try {
    const data = await getJobPage({
      ...searchForm.value,
      page: currentPage.value,
      pageSize: pageSize.value
    })
    tableData.value = data.records
    total.value = data.total
  } catch (error) {
    console.error('获取岗位数据失败:', error)
  }
}

const handleSearch = () => {
  currentPage.value = 1
  fetchData()
}

const handleReset = () => {
  searchForm.value = {
    jobName: '',
    city: '上海',
    district: '',
    degree: '',
    companySize: ''
  }
  currentPage.value = 1
  fetchData()
}

const handlePageChange = (page) => {
  currentPage.value = page
  fetchData()
}

onMounted(() => {
  fetchData()
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
      <h1>数据筛选</h1>
      <p>多维度筛选岗位数据，精准定位目标职位</p>
    </div>
    
    <div class="chart-card" style="margin-bottom: 20px;">
      <div class="chart-title">筛选条件</div>
      <el-form :inline="true" :model="searchForm" class="search-form">
        <el-form-item label="岗位名称">
          <el-input v-model="searchForm.jobName" placeholder="请输入岗位名称" style="width: 200px;" />
        </el-form-item>
        <el-form-item label="城市">
          <el-select v-model="searchForm.city" style="width: 120px;">
            <el-option label="上海" value="上海" />
          </el-select>
        </el-form-item>
        <el-form-item label="行政区">
          <el-select v-model="searchForm.district" placeholder="请选择" style="width: 150px;">
            <el-option label="全部" value="" />
            <el-option v-for="d in districts" :key="d" :label="d" :value="d" />
          </el-select>
        </el-form-item>
        <el-form-item label="学历要求">
          <el-select v-model="searchForm.degree" placeholder="请选择" style="width: 120px;">
            <el-option label="全部" value="" />
            <el-option v-for="d in degrees" :key="d" :label="d" :value="d" />
          </el-select>
        </el-form-item>
        <el-form-item label="企业规模">
          <el-select v-model="searchForm.companySize" placeholder="请选择" style="width: 150px;">
            <el-option label="全部" value="" />
            <el-option v-for="s in companySizes" :key="s" :label="s" :value="s" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="chart-card">
      <div class="chart-title">岗位列表</div>
      <el-table :data="tableData" border :height="400">
        <el-table-column prop="jobName" label="岗位名称" min-width="150" />
        <el-table-column prop="city" label="城市" width="80" />
        <el-table-column prop="district" label="行政区" width="100" />
        <el-table-column prop="salaryMin" label="薪资下限" width="100">
          <template #default="scope">{{ scope.row.salaryMin ? scope.row.salaryMin + 'K' : '-' }}</template>
        </el-table-column>
        <el-table-column prop="salaryMax" label="薪资上限" width="100">
          <template #default="scope">{{ scope.row.salaryMax ? scope.row.salaryMax + 'K' : '-' }}</template>
        </el-table-column>
        <el-table-column prop="degree" label="学历要求" width="100" />
        <el-table-column prop="workYear" label="经验要求" width="100" />
        <el-table-column prop="companySize" label="企业规模" width="120" />
        <el-table-column prop="companyName" label="公司名称" min-width="150" />
      </el-table>
      <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="total"
        layout="total, prev, pager, next"
        @current-change="handlePageChange"
        style="margin-top: 20px; text-align: right;"
      />
    </div>
    
    <div class="layout-footer">
      © 2026 招聘数据分析平台 | 数据来源：猎聘网
    </div>
  </div>
</template>

<style scoped>
.search-form {
  padding: 10px 0;
}
</style>