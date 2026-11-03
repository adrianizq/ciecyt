<template>
  <asignar-integrantes
    role="asesor-internacional"
    rol-nombre="Asesor Internacional"
    candidate-role="ASESOR"
  ></asignar-integrantes>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';
import AsignarIntegrantes from '@/pages/ciecyt/asignar-integrantes.vue';

@Component({
  components: { AsignarIntegrantes },
})
export default class AsignarAsesorInternacionalPage extends Vue {}
</script>
