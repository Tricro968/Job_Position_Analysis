<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getTopKeywords } from '@/api/jobAnalysis'

const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
  if (!chartRef.value) return
  
  chartInstance = echarts.init(chartRef.value)
  fetchData()
}

const fetchData = async () => {
  try {
    const data = await getTopKeywords()
    renderChart(data)
  } catch (error) {
    console.error('获取热门关键词失败:', error)
  }
}

const renderChart = (data) => {
  if (!chartInstance) return
  
  const sortedData = [...data].sort((a, b) => a.count - b.count)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'shadow'
      },
      formatter: '{b}<br/>出现次数: {c}'
    },
    grid: {
      left: '15%',
      right: '5%',
      bottom: '5%',
      top: '5%',
      containLabel: true
    },
    xAxis: {
      type: 'value',
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
    yAxis: {
      type: 'category',
      data: sortedData.map(item => item.keyword),
      axisLine: {
        show: false
      },
      axisTick: {
        show: false
      },
      axisLabel: {
        fontSize: 13,
        fontWeight: 500
      }
    },
    series: [
      {
        type: 'bar',
        barWidth: '60%',
        data: sortedData.map((item, index) => ({
          value: item.count,
          itemStyle: {
            borderRadius: [0, 8, 8, 0],
            color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
              { offset: 0, color: ['#667eea', '#f093fb', '#4facfe', '#43e97b', '#fa709a'][index % 5] },
              { offset: 1, color: ['#764ba2', '#f5576c', '#00f2fe', '#38f9d7', '#fb923c'][index % 5] }
            ])
          }
        }))
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
    <div class="chart-title">🔑 热门关键词TOP10</div>
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<style scoped>
</style>