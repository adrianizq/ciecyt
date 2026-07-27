<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.cronograma.home.title')" id="cronograma-heading">Cronogramas</span>
            <router-link :to="{name: 'CronogramaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cronograma">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.cronograma.home.createLabel')">
                    Create a new Cronograma
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
        <div class="alert alert-warning" v-if="!isFetching && cronogramas && cronogramas.length === 0">
            <span v-text="$t('ciecytApp.cronograma.home.notFound')">No cronogramas found</span>
        </div>
        <div class="table-responsive" v-if="cronogramas && cronogramas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('actividad')"><span v-text="$t('ciecytApp.cronograma.actividad')">Actividad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('duracion')"><span v-text="$t('ciecytApp.cronograma.duracion')">Duracion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.cronograma.fechaInicio')">Fecha Inicio</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.cronograma.fechaFin')">Fecha Fin</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ordenVista')"><span v-text="$t('ciecytApp.cronograma.ordenVista')">Orden Vista</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaProyectoTitulo')"><span v-text="$t('ciecytApp.cronograma.cronogramaProyecto')">Cronograma Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="cronograma in cronogramas"
                    :key="cronograma.id">
                    <td>
                        <router-link :to="{name: 'CronogramaView', params: {cronogramaId: cronograma.id}}">{{cronograma.id}}</router-link>
                    </td>
                    <td>{{cronograma.actividad}}</td>
                    <td>{{cronograma.duracion}}</td>
                    <td>{{cronograma.fechaInicio}}</td>
                    <td>{{cronograma.fechaFin}}</td>
                    <td>{{cronograma.ordenVista}}</td>
                    <td>
                        <div v-if="cronograma.cronogramaProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {cronogramaProyectoId: cronograma.cronogramaProyectoId}}">{{cronograma.cronogramaProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CronogramaView', params: {cronogramaId: cronograma.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CronogramaEdit', params: {cronogramaId: cronograma.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(cronograma)"
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
            <span slot="modal-title"><span id="ciecytApp.cronograma.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-cronograma-heading" v-bind:title="$t('ciecytApp.cronograma.delete.question')">Are you sure you want to delete this Cronograma?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cronograma" v-text="$t('entity.action.delete')" v-on:click="removeCronograma()">Delete</button>
            </div>
        </b-modal>
        <div v-show="cronogramas && cronogramas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma.component.ts">
</script>
