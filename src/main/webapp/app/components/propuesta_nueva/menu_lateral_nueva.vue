<template>
  <div class="menu-lateral">
    <div v-for="(item, index) in itemsVisibles" :key="item.to" class="step-item">
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
import { Component, Vue, Prop, Inject } from 'vue-facing-decorator';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';

@Component
export default class PropuestaMenuLateralNueva extends Vue {
  @Inject private proyectoService: () => ProyectoService;
  @Inject private rolesModalidadService: () => RolesModalidadService;

  items = this.$store.getters.menuLateralNueva;
  @Prop()
  proyectoId: number;

  itemsVisibles: any[] = [];

  async created() {
    this.itemsVisibles = this.items.slice();
    if (this.proyectoId) {
      try {
        const proyecto = await this.proyectoService().find(this.proyectoId);
        const modalidadId = proyecto.proyectoModalidadId;
        const tieneJurado = modalidadId != null;
        if (tieneJurado) {
          const rolJurado = await this.rolesModalidadService().findRolModalidad('Jurado', modalidadId);
          const necesitaJurado = !!rolJurado && !!rolJurado.id;
          this.itemsVisibles = this.items.filter(item => {
            if (item.title === 'Jurado') {
              return necesitaJurado;
            }
            return true;
          });
        }
      } catch (e) {
        console.error('Error al filtrar el menu lateral por modalidad:', e);
      }
    }
  }

  isActive(to) {
    return this.$route.path === to || this.$route.path.startsWith(to + '/');
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
