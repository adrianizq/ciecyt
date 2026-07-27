<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.impactosEsperados.home.title')" id="impactos-esperados-heading">Impactos Esperados</span>
            <router-link :to="{name: 'ImpactosEsperadosCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-impactos-esperados">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.impactosEsperados.home.createLabel')">
                    Create a new Impactos Esperados
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
        <div class="alert alert-warning" v-if="!isFetching && impactosEsperados && impactosEsperados.length === 0">
            <span v-text="$t('ciecytApp.impactosEsperados.home.notFound')">No impactosEsperados found</span>
        </div>
        <div class="table-responsive" v-if="impactosEsperados && impactosEsperados.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('impacto')"><span v-text="$t('ciecytApp.impactosEsperados.impacto')">Impacto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('plazo')"><span v-text="$t('ciecytApp.impactosEsperados.plazo')">Plazo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('indicador')"><span v-text="$t('ciecytApp.impactosEsperados.indicador')">Indicador</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('supuestos')"><span v-text="$t('ciecytApp.impactosEsperados.supuestos')">Supuestos</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ordenVista')"><span v-text="$t('ciecytApp.impactosEsperados.ordenVista')">Orden Vista</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('impactosEsperadoProyectoTitulo')"><span v-text="$t('ciecytApp.impactosEsperados.impactosEsperadoProyecto')">Impactos Esperado Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="impactosEsperados in impactosEsperados"
                    :key="impactosEsperados.id">
                    <td>
                        <router-link :to="{name: 'ImpactosEsperadosView', params: {impactosEsperadosId: impactosEsperados.id}}">{{impactosEsperados.id}}</router-link>
                    </td>
                    <td>{{impactosEsperados.impacto}}</td>
                    <td>{{impactosEsperados.plazo}}</td>
                    <td>{{impactosEsperados.indicador}}</td>
                    <td>{{impactosEsperados.supuestos}}</td>
                    <td>{{impactosEsperados.ordenVista}}</td>
                    <td>
                        <div v-if="impactosEsperados.impactosEsperadoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {impactosEsperadoProyectoId: impactosEsperados.impactosEsperadoProyectoId}}">{{impactosEsperados.impactosEsperadoProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ImpactosEsperadosView', params: {impactosEsperadosId: impactosEsperados.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ImpactosEsperadosEdit', params: {impactosEsperadosId: impactosEsperados.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(impactosEsperados)"
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
            <span slot="modal-title"><span id="ciecytApp.impactosEsperados.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-impactosEsperados-heading" v-bind:title="$t('ciecytApp.impactosEsperados.delete.question')">Are you sure you want to delete this Impactos Esperados?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-impactosEsperados" v-text="$t('entity.action.delete')" v-on:click="removeImpactosEsperados()">Delete</button>
            </div>
        </b-modal>
        <div v-show="impactosEsperados && impactosEsperados.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./impactos-esperados.component.ts">
</script>
