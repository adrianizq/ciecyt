<template>
  <div class="menu-lateral">
    <div v-for="(item, index) in items" :key="index" class="step-item">
      <component
        :is="esEnlace(item) ? 'router-link' : 'span'"
        class="step-btn"
        :class="{ active: isActive(item.to), 'step-btn-disabled': !esEnlace(item) }"
        v-bind="enlaceProps(item)"
        :title="esEnlace(item) ? null : 'Seleccione un proyecto para continuar'"
      >
        <span class="step-number">{{ index + 1 }}</span>
        <span class="step-info">
          <span class="step-title">{{ item.title }}</span>
          <span class="step-desc">{{ item.description }}</span>
        </span>
      </component>
    </div>
  </div>
</template>

<script lang="ts">
import Component from 'vue-class-component';
import { Vue, Prop } from 'vue-property-decorator';

@Component
export default class PropuestaMenuLateralCiecyt extends Vue {
  items = this.$store.getters.menuLateralCiecyt;
  @Prop()
  proyectoId: number;

  isActive(to) {
    return this.$route.path.startsWith(to);
  }

  esEnlace(item): boolean {
    return !item.requiereProyecto || !!this.proyectoId;
  }

  enlaceProps(item): any {
    if (!this.esEnlace(item)) {
      return {};
    }
    return { to: item.requiereProyecto ? item.to + '/' + this.proyectoId : item.to };
  }
}
</script>
