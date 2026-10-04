<template>
  <div class="menu-lateral">
    <div v-for="item in items" :key="item.to" class="step-item">
      <router-link class="step-btn" :class="{ active: isActive(item.to) }" :to="item.to">
        <span class="step-number">{{ item.number }}</span>
        <span class="step-info">
          <span class="step-title">{{ item.title }}</span>
          <span class="step-desc">{{ item.description }}</span>
        </span>
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';

@Component
export default class PropuestaMenuLateralListado extends Vue {
  items = this.$store.getters.menuLateralListado;

  isActive(to) {
    return this.$route.path === to || this.$route.path.startsWith(to + '/');
  }
}
</script>
