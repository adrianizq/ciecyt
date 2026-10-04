<template>
  <asignar-integrantes role="asesor"></asignar-integrantes>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';
import AsignarIntegrantes from '@/pages/ciecyt/asignar-integrantes.vue';

@Component({
  components: { AsignarIntegrantes },
})
export default class AsignarAsesorPage extends Vue {}
</script>
