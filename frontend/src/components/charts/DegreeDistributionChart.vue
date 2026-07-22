<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getDistrictDistribution } from '@/api/jobAnalysis'

const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
  if (!chartRef.value) return
  
  chartInstance = echarts.init(chartRef.value)
  fetchData()
}

const fetchData = async () => {
  try {
    const data = await getDistrictDistribution()
    renderChart(data)
  } catch (error) {
    console.error('获取地区分布数据失败:', error)
  }
}

const renderChart = (data) => {
  if (!chartInstance) return
  
  const districts = data.map(item => item.district)
  const counts = data.map(item => item.count)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '3%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: districts,
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        fontSize: 12,
        rotate: 45
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
    series: [
      {
        name: '岗位数',
        type: 'bar',
        barWidth: '60%',
        itemStyle: {
          borderRadius: [6, 6, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#4facfe' },
            { offset: 1, color: '#00f2fe' }
          ])
        },
        data: counts
      }
    ]
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
    <div class="chart-title">📍 各地区岗位数统计</div>
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<style scoped>
</style>