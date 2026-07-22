<script setup>
defineProps({
  icon: {
    type: String,
    default: '📊'
  },
  value: {
    type: [String, Number],
    default: '0'
  },
  label: {
    type: String,
    default: ''
  },
  color: {
    type: String,
    default: '#667eea'
  }
})
</script>

<template>
  <div class="stat-card">
    <div class="stat-icon" :style="{ color: color }">{{ icon }}</div>
    <div class="stat-value" :style="{ color: color }">{{ value }}</div>
    <div class="stat-label">{{ label }}</div>
  </div>
</template>

<style scoped>
</style>