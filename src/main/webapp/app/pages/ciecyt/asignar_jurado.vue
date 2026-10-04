<template>
  <asignar-integrantes role="jurado"></asignar-integrantes>
</template>

<script lang="ts">
import { Component, Vue } from 'vue-facing-decorator';
import AsignarIntegrantes from '@/pages/ciecyt/asignar-integrantes.vue';

@Component({
  components: { AsignarIntegrantes },
})
export default class AsignarJuradoPage extends Vue {}
</script>
