<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.resultadosEsperados.home.title')" id="resultados-esperados-heading">Resultados Esperados</span>
            <router-link :to="{name: 'ResultadosEsperadosCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-resultados-esperados">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.resultadosEsperados.home.createLabel')">
                    Create a new Resultados Esperados
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
        <div class="alert alert-warning" v-if="!isFetching && resultadosEsperados && resultadosEsperados.length === 0">
            <span v-text="$t('ciecytApp.resultadosEsperados.home.notFound')">No resultadosEsperados found</span>
        </div>
        <div class="table-responsive" v-if="resultadosEsperados && resultadosEsperados.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resultado')"><span v-text="$t('ciecytApp.resultadosEsperados.resultado')">Resultado</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('indicador')"><span v-text="$t('ciecytApp.resultadosEsperados.indicador')">Indicador</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('beneficiario')"><span v-text="$t('ciecytApp.resultadosEsperados.beneficiario')">Beneficiario</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ordenVista')"><span v-text="$t('ciecytApp.resultadosEsperados.ordenVista')">Orden Vista</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resultadosEsperadosProyectoTitulo')"><span v-text="$t('ciecytApp.resultadosEsperados.resultadosEsperadosProyecto')">Resultados Esperados Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="resultadosEsperados in resultadosEsperados"
                    :key="resultadosEsperados.id">
                    <td>
                        <router-link :to="{name: 'ResultadosEsperadosView', params: {resultadosEsperadosId: resultadosEsperados.id}}">{{resultadosEsperados.id}}</router-link>
                    </td>
                    <td>{{resultadosEsperados.resultado}}</td>
                    <td>{{resultadosEsperados.indicador}}</td>
                    <td>{{resultadosEsperados.beneficiario}}</td>
                    <td>{{resultadosEsperados.ordenVista}}</td>
                    <td>
                        <div v-if="resultadosEsperados.resultadosEsperadosProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {resultadosEsperadosProyectoId: resultadosEsperados.resultadosEsperadosProyectoId}}">{{resultadosEsperados.resultadosEsperadosProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ResultadosEsperadosView', params: {resultadosEsperadosId: resultadosEsperados.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ResultadosEsperadosEdit', params: {resultadosEsperadosId: resultadosEsperados.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(resultadosEsperados)"
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
            <span slot="modal-title"><span id="ciecytApp.resultadosEsperados.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-resultadosEsperados-heading" v-bind:title="$t('ciecytApp.resultadosEsperados.delete.question')">Are you sure you want to delete this Resultados Esperados?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-resultadosEsperados" v-text="$t('entity.action.delete')" v-on:click="removeResultadosEsperados()">Delete</button>
            </div>
        </b-modal>
        <div v-show="resultadosEsperados && resultadosEsperados.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./resultados-esperados.component.ts">
</script>
