<template>
  <div class="menu-lateral">
    <div v-for="(item, index) in itemsVisibles" :key="index" class="step-item">
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
import { Component } from 'vue-facing-decorator';
import { Vue, Prop, Inject } from 'vue-facing-decorator';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';

@Component
export default class PropuestaMenuLateralNueva extends Vue {
  @Inject  private proyectoService: () => ProyectoService;
  @Inject  private rolesModalidadService: () => RolesModalidadService;

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
    return this.$route.path.startsWith(to);
  }
}
</script>