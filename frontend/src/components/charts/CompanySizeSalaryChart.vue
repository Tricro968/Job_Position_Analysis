<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import * as echarts from 'echarts'
import { getSalaryByCompanySize } from '@/api/jobAnalysis'

const chartRef = ref(null)
let chartInstance = null

const initChart = () => {
  if (!chartRef.value) return
  
  chartInstance = echarts.init(chartRef.value)
  fetchData()
}

const fetchData = async () => {
  try {
    const data = await getSalaryByCompanySize()
    renderChart(data)
  } catch (error) {
    console.error('获取企业规模薪资数据失败:', error)
  }
}

const renderChart = (data) => {
  if (!chartInstance) return
  
  const sizeOrder = ['少于50人', '50-150人', '150-500人', '500-1000人', '1000-5000人', '5000-10000人', '10000人以上']
  
  const sortedData = [...data].sort((a, b) => {
    const idxA = sizeOrder.indexOf(a.companySize)
    const idxB = sizeOrder.indexOf(b.companySize)
    return idxA === -1 ? 1 : idxB === -1 ? -1 : idxA - idxB
  })
  
  const xAxisData = sortedData.map(item => item.companySize)
  const avgSalaryData = sortedData.map(item => Math.round((item.avgSalaryMin + item.avgSalaryMax) / 2 / 1000))
  const countData = sortedData.map(item => item.count)
  
  const option = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross'
      },
      formatter: (params) => {
        const size = params[0].name
        const salary = params[0].value
        const count = params[1].value
        return `${size}<br/>平均薪资: ${salary}K<br/>岗位数: ${count}`
      }
    },
    legend: {
      data: ['平均薪资', '岗位数'],
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
        name: '薪资(K)',
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
        }
      }
    ],
    series: [
      {
        name: '平均薪资',
        type: 'bar',
        barWidth: '40%',
        itemStyle: {
          borderRadius: [4, 4, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#667eea' },
            { offset: 1, color: '#764ba2' }
          ])
        },
        data: avgSalaryData
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
          color: '#f093fb'
        },
        itemStyle: {
          color: '#f093fb',
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
    <div class="chart-title">🏢 企业规模与薪资关系</div>
    <div ref="chartRef" class="chart-container"></div>
  </div>
</template>

<style scoped>
</style>