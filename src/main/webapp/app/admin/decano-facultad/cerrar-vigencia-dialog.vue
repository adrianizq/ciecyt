<template>
  <b-modal
    ref="modal"
    id="cerrarVigenciaModal"
    :title="$t('decanoFacultad.cerrar.title', { login: item ? item.user.login : '' })"
    :ok-title="isSaving ? $t('decanoFacultad.cerrar.saving') : $t('decanoFacultad.cerrar.confirm')"
    :ok-disabled="isSaving || !fechaHasta"
    :busy="isSaving"
    @ok.prevent="confirm"
  >
    <div data-cy="cerrar-vigencia-body">
      <p v-html="$t('decanoFacultad.cerrar.body', { login: item ? item.user.login : '' })"></p>

      <div class="mb-3">
        <label class="form-control-label" for="cerrarFecha" v-text="$t('decanoFacultad.cerrar.fechaLabel')"></label>
        <input
          id="cerrarFecha"
          type="date"
          class="form-control"
          data-cy="cerrar-fecha"
          v-model="fechaHasta"
          :max="new Date().toISOString().slice(0, 10)"
        />
      </div>

      <div class="alert alert-danger" role="alert" v-if="errorMessage" data-cy="cerrar-error">{{ errorMessage }}</div>
      <div class="alert alert-success" role="alert" v-if="success" data-cy="cerrar-success" v-html="$t('decanoFacultad.cerrar.success', { login: item ? item.user.login : '' })"></div>
    </div>
  </b-modal>
</template>

<script lang="ts" src="./cerrar-vigencia-dialog.component.ts"></script>
