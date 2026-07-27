<template>
  <div class="menu-lateral">
    <div v-for="(item, index) in items" :key="index" class="step-item">
      <router-link
        class="step-btn"
        :class="{ active: isActive(item.to) }"
        :to="proyectoId ? item.to + '/' + proyectoId : item.to"
        :event="proyectoId ? 'click' : ''"
        :clickable="!!proyectoId"
      >
        <span class="step-number">{{ index + 1 }}</span>
        <span class="step-info">
          <span class="step-title">{{ item.title }}</span>
          <span class="step-desc">{{ item.description }}</span>
        </span>
      </router-link>
    </div>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import { Vue, Prop } from 'vue-property-decorator';

@Component
export default class PropuestaMenuLateral extends Vue {
  items = this.$store.getters.menuLateral;
  @Prop()
  proyectoId: number;

  isActive(to) {
    return this.$route.path.startsWith(to);
  }
}
</script>
