<template>
  <div class="menu-lateral">
    <div v-for="(item, index) in items" :key="item.to" class="step-item">
      <component
        :is="esEnlace(item) ? 'router-link' : 'span'"
        class="step-btn"
        :class="{ active: isActive(item.to), 'step-btn-disabled': !esEnlace(item) }"
        v-bind="enlaceProps(item)"
        :title="esEnlace(item) ? null : 'Seleccione un proyecto para continuar'"
        :aria-current="esEnlace(item) && isActive(item.to) ? 'step' : undefined"
        :aria-disabled="!esEnlace(item) ? 'true' : undefined"
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
import { Component, Vue, Prop } from 'vue-facing-decorator';

/**
 * Sidebar de wizard compartido por propuesta, propuesta-diplomado,
 * propuesta-pasantia y proyecto. Recibe el nombre del getter del store
 * (e.g. 'menuLateral', 'menuLateralDiplomado', 'menuLateralProyecto') y
 * los items del store se renderizan como pasos del wizard.
 *
 * Antes este codigo estaba duplicado en 4 archivos casi identicos
 * (99.4% ratio via difflib); ahora vive aqui.
 */
@Component
export default class MenuLateralWizard extends Vue {
  @Prop({ required: true })
  storeKey: string;

  get items(): any[] {
    return this.$store.getters[this.storeKey] || [];
  }

  isActive(to) {
    return this.$route.path === to || this.$route.path.startsWith(to + '/');
  }

  esEnlace(item): boolean {
    return !item.requiereProyecto || !!this.$route.params.proyectoId;
  }

  enlaceProps(item): any {
    if (!this.esEnlace(item)) {
      return {};
    }
    return { to: item.requiereProyecto ? item.to + '/' + this.$route.params.proyectoId : item.to };
  }
}
</script>
