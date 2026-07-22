<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getKeywordSalaryTrend } from '@/api/jobAnalysis'

const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
  if (!chartRef.value) return
  
  chartInstance = echarts.init(chartRef.value)
  fetchData()
}

const fetchData = async () => {
  try {
    const data = await getKeywordSalaryTrend()
    renderChart(data)
  } catch (error) {
    console.error('获取关键词最低薪资数据失败:', error)
  }
}

const renderChart = (data) => {
  if (!chartInstance) return
  
  const sortedData = [...data].sort((a, b) => a.minSalary - b.minSalary)
  
  const xAxisData = sortedData.map(item => item.keyword)
  const minSalaryData = sortedData.map(item => Math.round(item.minSalary / 1000))
  const countData = sortedData.map(item => item.count)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross'
      },
      formatter: (params) => {
        const keyword = params[0].name
        const minSalary = params[0].value
        const count = params[1].value
        return `${keyword}<br/>最低薪资: ${minSalary}K<br/>岗位数: ${count}`
      }
    },
    legend: {
      data: ['最低薪资', '岗位数'],
      bottom: '0%',
      itemWidth: 12,
      itemHeight: 12
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '15%',
      top: '10%',
      containLabel: true
    },
    xAxis: {
      type: 'category',
      data: xAxisData,
      axisLine: {
        lineStyle: {
          color: '#ddd'
        }
      },
      axisLabel: {
        fontSize: 10,
        rotate: 45
      }
    },
    yAxis: [
      {
        type: 'value',
        name: '最低薪资(K)',
        position: 'left',
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
      {
        type: 'value',
        name: '岗位数',
        position: 'right',
        axisLine: {
          show: false
        },
        axisTick: {
          show: false
        },
        splitLine: {
          show: false
        },
        minInterval: 1
      }
    ],
    series: [
      {
        name: '最低薪资',
        type: 'bar',
        barWidth: '40%',
        itemStyle: {
          borderRadius: [4, 4, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#4facfe' },
            { offset: 1, color: '#00f2fe' }
          ])
        },
        data: minSalaryData
      },
      {
        name: '岗位数',
        type: 'line',
        yAxisIndex: 1,
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: {
          width: 2,
          color: '#43e97b'
        },
        itemStyle: {
          color: '#43e97b',
          borderWidth: 2,
          borderColor: '#fff'
        },
        data: countData
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
    <div class="chart-title">🔑 各关键词与最低薪资关系</div>
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<style scoped>
</style>