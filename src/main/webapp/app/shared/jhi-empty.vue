<template>
  <div class="jhi-empty text-center py-4" role="status">
    <font-awesome-icon :icon="icono" class="jhi-empty-icon text-muted mb-2" size="2x" />
    <p class="mb-1 fw-semibold">{{ titulo }}</p>
    <p v-if="mensaje" class="mb-0 text-muted small">{{ mensaje }}</p>
    <slot></slot>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-facing-decorator';

@Component({})
export default class JhiEmpty extends Vue {
  @Prop({ default: 'list' })
  public icono: string;

  @Prop({ default: 'No hay registros' })
  public titulo: string;

  @Prop({ default: 'Todavía no hay nada que mostrar aquí.' })
  public mensaje: string;
}
</script>

<style scoped>
.jhi-empty-icon {
  opacity: 0.5;
}
</style>
