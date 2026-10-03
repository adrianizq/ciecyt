<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.cronograma.home.title')" id="cronograma-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-cronograma" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.cronograma.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && cronogramas && cronogramas.length === 0" :titulo="$t('ciecytApp.cronograma.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="cronogramas && cronogramas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('actividad')"><span v-text="$t('ciecytApp.cronograma.actividad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('duracion')"><span v-text="$t('ciecytApp.cronograma.duracion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.cronograma.fechaInicio')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.cronograma.fechaFin')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ordenVista')"><span v-text="$t('ciecytApp.cronograma.ordenVista')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cronogramaProyectoTitulo')"><span v-text="$t('ciecytApp.cronograma.cronogramaProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaView', params: {cronogramaId: cronograma.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CronogramaEdit', params: {cronogramaId: cronograma.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(cronograma)"
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
<span ><span id="ciecytApp.cronograma.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-cronograma-heading" v-bind:title="$t('ciecytApp.cronograma.delete.question')">Are you sure you want to delete this Cronograma?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-cronograma" v-text="$t('entity.action.delete')" v-on:click="removeCronograma()"></button>
            </div>
</template>
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
