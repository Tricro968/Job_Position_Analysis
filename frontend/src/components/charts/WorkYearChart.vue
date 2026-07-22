<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getIndustryWorkYearDistribution } from '@/api/jobAnalysis'

const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
  if (!chartRef.value) return
  
  chartInstance = echarts.init(chartRef.value)
  fetchData()
}

const fetchData = async () => {
  try {
    const data = await getIndustryWorkYearDistribution()
    renderChart(data)
  } catch (error) {
    console.error('获取产业工作经验数据失败:', error)
  }
}

const renderChart = (data) => {
  if (!chartInstance) return
  
  const industries = [...new Set(data.map(item => item.industry))]
  const workYears = ['应届毕业生', '1-3年', '3-5年', '5-10年', '10年以上', '不限']
  
  const series = workYears.map(year => ({
    name: year,
    type: 'bar',
    stack: 'total',
    barWidth: '50%',
    itemStyle: {
      borderRadius: [4, 4, 0, 0]
    },
    data: industries.map(industry => {
      const item = data.find(d => d.industry === industry && d.workYear === year)
      return item ? item.count : 0
    })
  }))
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    legend: {
      data: workYears,
      bottom: '0%',
      itemWidth: 12,
      itemHeight: 12
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '20%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: industries,
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        fontSize: 11,
        rotate: 30
      }
    },
    yAxis: {
      type: 'value',
      name: '岗位数',
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      splitLine: {
        lineStyle: {
          color: '#f0f0f0',
          type: 'dashed'
        }
      }
    },
    series: series
  }
  
  chartInstance.setOption(option)
}

const handleResize = () => {
  chartInstance?.resize()
}

onMounted(() => {
  initChart()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  chartInstance?.dispose()
})
</script>

<template>
  <div class="chart-card">
    <div class="chart-title">💼 各产业工作经验要求分布</div>
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<style scoped>
</style>