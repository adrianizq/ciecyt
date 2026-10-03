<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.rolesModalidad.home.title')" id="roles-modalidad-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'RolesModalidadCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-roles-modalidad" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.rolesModalidad.home.createLabel')"></span>
            </button></router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <jhi-loading v-if="isFetching"></jhi-loading>
        <jhi-empty v-if="!isFetching && rolesModalidads && rolesModalidads.length === 0" :titulo="$t('ciecytApp.general.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="rolesModalidads && rolesModalidads.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('rol')"><span v-text="$t('ciecytApp.rolesModalidad.rol')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cantidad')"><span v-text="$t('ciecytApp.rolesModalidad.cantidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('calificador')"><span v-text="$t('ciecytApp.rolesModalidad.calificador')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('rolesModalidadModalidadModalidad')"><span v-text="$t('ciecytApp.rolesModalidad.rolesModalidadModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('rolesModalidadAuthorityName')"><span v-text="$t('ciecytApp.rolesModalidad.rolesModalidadAuthority')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="rolesModalidad in rolesModalidads"
                    :key="rolesModalidad.id">
                    <td>
                        <router-link :to="{name: 'RolesModalidadView', params: {rolesModalidadId: rolesModalidad.id}}">{{rolesModalidad.id}}</router-link>
                    </td>
                    <td>{{rolesModalidad.rol}}</td>
                    <td>{{rolesModalidad.cantidad}}</td>
                    <td>{{rolesModalidad.calificador ? 'Si' : 'No' }}</td>
                    <td>
                        <div v-if="rolesModalidad.rolesModalidadModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {modalidadId: rolesModalidad.rolesModalidadModalidadId}}">{{rolesModalidad.rolesModalidadModalidadModalidad}}</router-link>
                        </div>
                    </td>
                    <td>
                      {{rolesModalidad.rolesModalidadAuthorityName}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'RolesModalidadView', params: {rolesModalidadId: rolesModalidad.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'RolesModalidadEdit', params: {rolesModalidadId: rolesModalidad.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(rolesModalidad)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')"></span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <template #modal-title>
<span ><span id="ciecytApp.rolesModalidad.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-rolesModalidad-heading" v-bind:title="$t('ciecytApp.rolesModalidad.delete.question')">Are you sure you want to delete this Roles Modalidad?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-rolesModalidad" v-text="$t('entity.action.delete')" v-on:click="removeRolesModalidad()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="rolesModalidads && rolesModalidads.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./roles-modalidad.component.ts">
</script>
