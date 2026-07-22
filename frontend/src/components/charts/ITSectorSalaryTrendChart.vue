<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getITSectorSalaryTrend } from '@/api/jobAnalysis'

const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
  if (!chartRef.value) return
  
  chartInstance = echarts.init(chartRef.value)
  fetchData()
}

const fetchData = async () => {
  try {
    const data = await getITSectorSalaryTrend()
    renderChart(data)
  } catch (error) {
    console.error('获取IT行业薪资趋势数据失败:', error)
  }
}

const renderChart = (data) => {
  if (!chartInstance) return
  
  const xAxisData = data.map(item => {
    const date = new Date(item.publishDate)
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`
  })
  const avgSalaryData = data.map(item => Math.round(item.avgSalary / 1000))
  
  const option = {
    tooltip: {
      trigger: 'axis',
      formatter: (params) => {
        const date = params[0].name
        const avgSalary = params[0].value
        return `${date}<br/>平均薪资: ${avgSalary}K`
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
      data: xAxisData,
      boundaryGap: false,
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
    yAxis: {
      type: 'value',
      name: '平均薪资(K)',
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
        name: '平均薪资',
        type: 'line',
        smooth: true,
        symbol: 'circle',
        symbolSize: 8,
        lineStyle: {
          width: 3,
          color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
            { offset: 0, color: '#43e97b' },
            { offset: 1, color: '#38f9d7' }
          ])
        },
        itemStyle: {
          color: '#43e97b',
          borderWidth: 2,
          borderColor: '#fff'
        },
        areaStyle: {
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: 'rgba(67, 233, 123, 0.3)' },
            { offset: 1, color: 'rgba(67, 233, 123, 0.05)' }
          ])
        },
        data: avgSalaryData
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
    <div class="chart-title">💻 IT行业薪资变化趋势</div>
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<style scoped>
</style>