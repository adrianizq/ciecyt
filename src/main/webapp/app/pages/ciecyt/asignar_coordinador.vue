<template>
  <asignar-integrantes
    role="coordinador"
    rol-nombre="Coordinador"
    candidate-role="CIECYT"
    candidate-source="usuarios"
  ></asignar-integrantes>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';
import AsignarIntegrantes from '@/pages/ciecyt/asignar-integrantes.vue';

@Component({
  components: { AsignarIntegrantes },
})
export default class AsignarCoordinadorPage extends Vue {}
</script>
