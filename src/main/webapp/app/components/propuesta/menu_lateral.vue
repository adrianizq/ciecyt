<template>
  <menu-lateral-wizard store-key="menuLateral"></menu-lateral-wizard>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';

// Re-export del sidebar compartido como <menu-lateral> para los
// componentes <propuesta/informacion_general> que ya lo usan.
@Component
export default class PropuestaMenuLateral extends Vue {}
</script>
