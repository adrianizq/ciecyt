<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.retroalimentacion.home.title')" id="retroalimentacion-heading">Retroalimentacions</span>
            <router-link :to="{name: 'RetroalimentacionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-retroalimentacion">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.retroalimentacion.home.createLabel')">
                    Create a new Retroalimentacion
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
        <div class="alert alert-warning" v-if="!isFetching && retroalimentacions && retroalimentacions.length === 0">
            <span v-text="$t('ciecytApp.retroalimentacion.home.notFound')">No retroalimentacions found</span>
        </div>
        <div class="table-responsive" v-if="retroalimentacions && retroalimentacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.retroalimentacion.titulo')">Titulo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('retroalimentacion')"><span v-text="$t('ciecytApp.retroalimentacion.retroalimentacion')">Retroalimentacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaRetroalimentacion')"><span v-text="$t('ciecytApp.retroalimentacion.fechaRetroalimentacion')">Fecha Retroalimentacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('estadoRetroalimentacion')"><span v-text="$t('ciecytApp.retroalimentacion.estadoRetroalimentacion')">Estado Retroalimentacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('estadoProyectoFase')"><span v-text="$t('ciecytApp.retroalimentacion.estadoProyectoFase')">Estado Proyecto Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('retroalimentacionProyectoFaseTitulo')"><span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionProyectoFase')">Retroalimentacion Proyecto Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('retroalimentacionUserLogin')"><span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionUser')">Retroalimentacion User</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="retroalimentacion in retroalimentacions"
                    :key="retroalimentacion.id">
                    <td>
                        <router-link :to="{name: 'RetroalimentacionView', params: {retroalimentacionId: retroalimentacion.id}}">{{retroalimentacion.id}}</router-link>
                    </td>
                    <td>{{retroalimentacion.titulo}}</td>
                    <td>{{retroalimentacion.retroalimentacion}}</td>
                    <td>{{retroalimentacion.fechaRetroalimentacion}}</td>
                    <td>{{retroalimentacion.estadoRetroalimentacion}}</td>
                    <td>{{retroalimentacion.estadoProyectoFase}}</td>
                    <td>
                        <div v-if="retroalimentacion.retroalimentacionProyectoFaseId">
                            <router-link :to="{name: 'ProyectoFaseView', params: {retroalimentacionProyectoFaseId: retroalimentacion.retroalimentacionProyectoFaseId}}">{{retroalimentacion.retroalimentacionProyectoFaseTitulo}}</router-link>
                        </div>
                    </td>
                    <td>
                        {{retroalimentacion.retroalimentacionUserLogin}}
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RetroalimentacionView', params: {retroalimentacionId: retroalimentacion.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RetroalimentacionEdit', params: {retroalimentacionId: retroalimentacion.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(retroalimentacion)"
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
            <span slot="modal-title"><span id="ciecytApp.retroalimentacion.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-retroalimentacion-heading" v-bind:title="$t('ciecytApp.retroalimentacion.delete.question')">Are you sure you want to delete this Retroalimentacion?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-retroalimentacion" v-text="$t('entity.action.delete')" v-on:click="removeRetroalimentacion()">Delete</button>
            </div>
        </b-modal>
        <div v-show="retroalimentacions && retroalimentacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./retroalimentacion.component.ts">
</script>
