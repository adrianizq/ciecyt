<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.investigacionTipo.home.title')" id="investigacion-tipo-heading">Investigacion Tipo</span>
            <router-link :to="{name: 'InvestigacionTipoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-investigacion-tipo">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.investigacionTipo.home.createLabel')">
                    Create a new Tipo de Investigacion
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
        <div class="alert alert-warning" v-if="!isFetching && investigacionTips && investigacionTips.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')">No investigacionTips found</span>
        </div>
        <div class="table-responsive" v-if="investigacionTips && investigacionTips.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('investigacionTipo')"><span v-text="$t('ciecytApp.investigacionTipo.investigacionTipo')">Investigacion Tipo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('investigacionTipoDescripcion')"><span v-text="$t('ciecytApp.investigacionTipo.investigacionTipoDescripcion')">Investigacion Tipo Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('investigacionTipoTipo')"><span v-text="$t('ciecytApp.investigacionTipo.tipo')">Tipo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('investigacionTipoTipoDescripcion')"><span v-text="$t('ciecytApp.investigacionTipo.tipoDescripcion')">Tipo Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="investigacionTipo in investigacionTips"
                    :key="investigacionTipo.id">
                    <td>
                        <router-link :to="{name: 'InvestigacionTipoView', params: {investigacionTipoId: investigacionTipo.id}}">{{investigacionTipo.id}}</router-link>
                    </td>
                    <td>{{investigacionTipo.investigacionTipo}}</td>
                    <td>{{investigacionTipo.investigacionTipoDescripcion}}</td>
                    <td>{{investigacionTipo.tipo}}</td>
                    <td>{{investigacionTipo.tipoDescripcion}}</td>
                    
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'InvestigacionTipoView', params: {investigacionTipoId: investigacionTipo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'InvestigacionTipoEdit', params: {investigacionTipoId: investigacionTipo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(investigacionTipo)"
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
            <span slot="modal-title"><span id="ciecytApp.investigacionTipo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-investigacionTipo-heading" v-bind:title="$t('ciecytApp.investigacionTipo.delete.question')">Are you sure you want to delete this Linea Investigacion?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-investigacionTipo" v-text="$t('entity.action.delete')" v-on:click="removeInvestigacionTipo()">Delete</button>
            </div>
        </b-modal>
        <div v-show="investigacionTips && investigacionTips.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./investigacion-tipo.component.ts">
</script>
