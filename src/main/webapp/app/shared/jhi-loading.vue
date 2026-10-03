<template>
  <div class="jhi-loading text-center py-4" role="status" aria-live="polite">
    <div class="spinner-border text-primary mb-2" aria-hidden="true"></div>
    <p class="mb-0 text-muted">{{ texto }}</p>
  </div>
</template>

<script lang="ts">
import { Component, Prop, Vue } from 'vue-facing-decorator';

@Component({})
export default class JhiLoading extends Vue {
  @Prop({ default: 'Cargando…' })
  public texto: string;
}
</script>

<style scoped>
.jhi-loading {
  min-height: 6rem;
}
</style>
