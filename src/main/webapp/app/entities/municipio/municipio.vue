<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.municipio.home.title')" id="municipio-heading">Municipios</span>
            <router-link :to="{name: 'MunicipioCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-municipio">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.municipio.home.createLabel')">
                    Create a new Municipio
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && municipios && municipios.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')">No municipios found</span>
        </div>
        <div class="table-responsive" v-if="municipios && municipios.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('region')"><span v-text="$t('ciecytApp.municipio.region')">Municipio</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('codigoDaneDepartamento')"><span v-text="$t('ciecytApp.municipio.codigoDaneDepartamento')">Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('departamento')"><span v-text="$t('ciecytApp.municipio.departamento')">Codigo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('codigoDaneMunicipio')"><span v-text="$t('ciecytApp.municipio.codigoDaneMunicipio')">Fecha</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('municipio')"><span v-text="$t('ciecytApp.municipio.municipio')">Fecha</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="municipio in municipios"
                    :key="municipio.id">
                    <td>
                        <router-link :to="{name: 'MunicipioView', params: {municipioId: municipio.id}}">{{municipio.id}}</router-link>
                    </td>
                    <td>{{municipio.id}}</td>
                    <td>{{municipio.region}}</td>
                    <td>{{municipio.codigoDaneDepartamento}}</td>
                    <td>{{municipio.departamento}}</td>
                    <td>{{municipio.codigoDaneMunicipio}}</td>
                    <td>{{municipio.municipio}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MunicipioView', params: {municipioId: municipio.codigoDaneMunicipio}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MunicipioEdit', params: {municipioId: municipio.codigoDaneMunicipio}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(municipio)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="ciecytApp.municipio.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-municipio-heading" v-bind:title="$t('ciecytApp.municipio.delete.question')">Are you sure you want to delete this Municipio?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-municipio" v-text="$t('entity.action.delete')" v-on:click="removeMunicipio()">Delete</button>
            </div>
        </b-modal>
        <div v-show="municipios && municipios.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./municipio.component.ts">
</script>
