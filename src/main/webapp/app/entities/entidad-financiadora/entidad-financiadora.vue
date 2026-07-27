<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.entidadFinanciadora.home.title')" id="entidad-financiadora-heading">Entidad Financiadoras</span>
            <router-link :to="{name: 'EntidadFinanciadoraCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-entidad-financiadora">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.entidadFinanciadora.home.createLabel')">
                    Create a new Entidad Financiadora
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
        <div class="alert alert-warning" v-if="!isFetching && entidadFinanciadoras && entidadFinanciadoras.length === 0">
            <span v-text="$t('ciecytApp.entidadFinanciadora.home.notFound')">No entidadFinanciadoras found</span>
        </div>
        <div class="table-responsive" v-if="entidadFinanciadoras && entidadFinanciadoras.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('valor')"><span v-text="$t('ciecytApp.entidadFinanciadora.valor')">Valor</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('aprobada')"><span v-text="$t('ciecytApp.entidadFinanciadora.aprobada')">Aprobada</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('entidadFinanciadoraEntidadEntidad')"><span v-text="$t('ciecytApp.entidadFinanciadora.entidadFinanciadoraEntidad')">Entidad Financiadora Entidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('entidadFinanciadoraProyectoTitulo')"><span v-text="$t('ciecytApp.entidadFinanciadora.entidadFinanciadoraProyecto')">Entidad Financiadora Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="entidadFinanciadora in entidadFinanciadoras"
                    :key="entidadFinanciadora.id">
                    <td>
                        <router-link :to="{name: 'EntidadFinanciadoraView', params: {entidadFinanciadoraId: entidadFinanciadora.id}}">{{entidadFinanciadora.id}}</router-link>
                    </td>
                    <td>{{entidadFinanciadora.valor}}</td>
                    <td>{{entidadFinanciadora.aprobada}}</td>
                    <td>
                        <div v-if="entidadFinanciadora.entidadFinanciadoraEntidadId">
                            <router-link :to="{name: 'EntidadView', params: {entidadFinanciadoraEntidadId: entidadFinanciadora.entidadFinanciadoraEntidadId}}">{{entidadFinanciadora.entidadFinanciadoraEntidadEntidad}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="entidadFinanciadora.entidadFinanciadoraProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {entidadFinanciadoraProyectoId: entidadFinanciadora.entidadFinanciadoraProyectoId}}">{{entidadFinanciadora.entidadFinanciadoraProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'EntidadFinanciadoraView', params: {entidadFinanciadoraId: entidadFinanciadora.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'EntidadFinanciadoraEdit', params: {entidadFinanciadoraId: entidadFinanciadora.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(entidadFinanciadora)"
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
            <span slot="modal-title"><span id="ciecytApp.entidadFinanciadora.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-entidadFinanciadora-heading" v-bind:title="$t('ciecytApp.entidadFinanciadora.delete.question')">Are you sure you want to delete this Entidad Financiadora?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-entidadFinanciadora" v-text="$t('entity.action.delete')" v-on:click="removeEntidadFinanciadora()">Delete</button>
            </div>
        </b-modal>
        <div v-show="entidadFinanciadoras && entidadFinanciadoras.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./entidad-financiadora.component.ts">
</script>
