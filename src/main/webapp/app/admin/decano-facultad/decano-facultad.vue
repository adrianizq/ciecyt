<template>
  <div>
    <h2 class="mb-4">
      <span id="decano-facultad-page-heading" v-text="$t('decanoFacultad.home.title')"></span>
      <button
        id="jh-create-entity"
        class="btn btn-primary btn-md float-right jh-create-entity"
        v-on:click="openAsignarDialog()"
      >
        <font-awesome-icon icon="plus"></font-awesome-icon>
        <span v-text="$t('decanoFacultad.home.createLabel')"></span>
      </button>
    </h2>

    <b-alert
      :show="dismissCountDown"
      dismissible
      :variant="alertType"
      @dismissed="dismissCountDown = 0"
      @dismiss-count-down="countDownChanged"
    >
      {{ alertMessage }}
    </b-alert>

    <div class="table-responsive" v-if="asignaciones">
      <table class="table table-striped" aria-describedby="decano-facultad-page-heading">
        <thead>
          <tr>
            <th scope="col"><span v-text="$t('decanoFacultad.facultad')"></span></th>
            <th scope="col"><span v-text="$t('decanoFacultad.login')"></span></th>
            <th scope="col"><span v-text="$t('decanoFacultad.nombre')"></span></th>
            <th scope="col"><span v-text="$t('decanoFacultad.cargo')"></span></th>
            <th scope="col"><span v-text="$t('decanoFacultad.fechaDesde')"></span></th>
            <th scope="col"><span v-text="$t('decanoFacultad.fechaHasta')"></span></th>
            <th scope="col"><span v-text="$t('decanoFacultad.actoResolucion')"></span></th>
            <th scope="col" class="text-right">
              <span class="visually-hidden" v-text="$t('global.menu.actions')"></span>
            </th>
          </tr>
        </thead>
        <tbody data-cy="decano-facultad-tbody">
          <tr v-for="item in asignaciones" :key="item.id" :id="'decano-facultad-' + item.id">
            <td>{{ item.facultad && item.facultad.facultad ? item.facultad.facultad : item.facultad && item.facultad.id }}</td>
            <td>{{ item.user ? item.user.login : '' }}</td>
            <td>{{ formatUserName(item.user) }}</td>
            <td>{{ item.cargo }}</td>
            <td>{{ formatDate(item.fechaDesde) }}</td>
            <td>
              <span v-if="!item.fechaHasta" class="badge badge-success" v-text="$t('decanoFacultad.vigente')"></span>
              <span v-else class="badge badge-secondary" v-text="formatDate(item.fechaHasta)"></span>
            </td>
            <td>{{ item.actoResolucion }}</td>
            <td class="text-right">
              <div class="btn-group">
                <button
                  v-if="!item.fechaHasta"
                  class="btn btn-warning btn-sm cerrar"
                  data-cy="cerrar-vigencia"
                  :aria-label="$t('decanoFacultad.actions.cerrar')"
                  v-on:click="openCerrarDialog(item)"
                >
                  <font-awesome-icon icon="lock"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('decanoFacultad.actions.cerrar')"></span>
                </button>
                <button
                  class="btn btn-danger btn-sm delete"
                  data-cy="delete-decano-facultad"
                  :aria-label="$t('entity.action.delete')"
                  v-on:click="prepareRemove(item)"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')"></span>
                </button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>

      <b-modal ref="removeDecanoFacultad" id="removeDecanoFacultad" :title="$t('entity.delete.title')" @ok="deleteDecanoFacultad()">
        <div class="modal-body">
          <p
            id="jhi-delete-decanoFacultad-heading"
            v-text="$t('decanoFacultad.delete.question', {
              login: removeId ? (removeId.user ? removeId.user.login : '') : '',
              facultad: removeId ? removeId.facultad && removeId.facultad.facultad : ''
            })"
          ></p>
        </div>
        <template #modal-footer>
          <div>
            <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
            <button type="button" class="btn btn-primary" id="confirm-delete-decano-facultad" v-text="$t('entity.action.delete')" v-on:click="deleteDecanoFacultad()"></button>
          </div>
        </template>
      </b-modal>

      <asignar-decano-dialog ref="asignarDialog" @saved="loadAll()"></asignar-decano-dialog>
      <cerrar-vigencia-dialog ref="cerrarDialog" @closed="loadAll()"></cerrar-vigencia-dialog>
    </div>
  </div>
</template>

<script lang="ts" src="./decano-facultad.component.ts"></script>
