<template>
  <b-modal
    ref="modal"
    id="asignarDecanoModal"
    :title="$t('decanoFacultad.asignar.title')"
    :ok-title="isSaving ? $t('decanoFacultad.asignar.saving') : $t('decanoFacultad.asignar.confirm')"
    :ok-disabled="isSaving || !formValid"
    :busy="isSaving"
    @ok.prevent="confirm"
  >
    <div data-cy="asignar-decano-body">
      <p v-html="$t('decanoFacultad.asignar.body')"></p>

      <div class="mb-3">
        <label
          class="form-control-label"
          for="asignarLogin"
          v-text="$t('decanoFacultad.asignar.loginLabel')"
        ></label>
        <input
          id="asignarLogin"
          type="text"
          class="form-control"
          data-cy="asignar-login"
          v-model="form.userLogin"
          v-on:input="onUserLoginInput"
          v-bind:placeholder="$t('decanoFacultad.asignar.loginPlaceholder')"
        />
        <small
          class="form-text text-success"
          v-if="resolvedUser"
          data-cy="asignar-login-resuelto"
        >
          <span v-text="$t('decanoFacultad.asignar.loginResolved', { nombre: userName(resolvedUser) })"></span>
        </small>
        <small
          class="form-text text-danger"
          v-if="loginError"
          data-cy="asignar-login-error"
        >
          <span v-text="loginError"></span>
        </small>
      </div>

      <div class="mb-3">
        <label class="form-control-label" for="asignarFacultad" v-text="$t('decanoFacultad.asignar.facultadLabel')"></label>
        <select
          id="asignarFacultad"
          class="form-control"
          data-cy="asignar-facultad"
          v-model="form.facultadId"
        >
          <option :value="null" disabled v-text="$t('decanoFacultad.asignar.facultadPlaceholder')"></option>
          <option v-for="facultad in facultades" :key="facultad.id" :value="facultad.id">
            {{ facultad.codigoFacultad }} · {{ facultad.facultad }}
          </option>
        </select>
      </div>

      <div class="mb-3">
        <label class="form-control-label" for="asignarCargo" v-text="$t('decanoFacultad.asignar.cargoLabel')"></label>
        <input
          id="asignarCargo"
          type="text"
          class="form-control"
          data-cy="asignar-cargo"
          v-model="form.cargo"
        />
      </div>

      <div class="mb-3">
        <label class="form-control-label" for="asignarFechaDesde" v-text="$t('decanoFacultad.asignar.fechaDesdeLabel')"></label>
        <input
          id="asignarFechaDesde"
          type="date"
          class="form-control"
          data-cy="asignar-fecha-desde"
          v-model="form.fechaDesde"
        />
      </div>

      <div class="mb-3">
        <label class="form-control-label" for="asignarActo" v-text="$t('decanoFacultad.asignar.actoLabel')"></label>
        <input
          id="asignarActo"
          type="text"
          class="form-control"
          data-cy="asignar-acto"
          v-model="form.actoResolucion"
          v-bind:placeholder="$t('decanoFacultad.asignar.actoPlaceholder')"
        />
      </div>

      <div class="mb-3 form-check" v-if="form.esDelegado !== undefined">
        <input
          id="asignarDelegado"
          type="checkbox"
          class="form-check-input"
          data-cy="asignar-delegado"
          v-model="form.esDelegado"
        />
        <label class="form-check-label" for="asignarDelegado" v-text="$t('decanoFacultad.asignar.delegadoLabel')"></label>
      </div>

      <div class="mb-3">
        <label class="form-control-label" for="asignarObservaciones" v-text="$t('decanoFacultad.asignar.observacionesLabel')"></label>
        <textarea
          id="asignarObservaciones"
          class="form-control"
          data-cy="asignar-observaciones"
          v-model="form.observaciones"
          rows="2"
        ></textarea>
      </div>

      <div class="alert alert-danger" role="alert" v-if="errorMessage" data-cy="asignar-error">{{ errorMessage }}</div>
      <div class="alert alert-success" role="alert" v-if="success" data-cy="asignar-success" v-html="$t('decanoFacultad.asignar.success', { login: form.userLogin })"></div>
    </div>
  </b-modal>
</template>

<script lang="ts" src="./asignar-decano-dialog.component.ts"></script>
