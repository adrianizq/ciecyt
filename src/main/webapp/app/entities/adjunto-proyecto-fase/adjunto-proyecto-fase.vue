<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.adjuntoProyectoFase.home.title')" id="adjunto-proyecto-fase-heading">Adjunto Proyecto Fases</span>
            <router-link :to="{name: 'AdjuntoProyectoFaseCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-adjunto-proyecto-fase">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.adjuntoProyectoFase.home.createLabel')">
                    Create a new Adjunto Proyecto Fase
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
        <div class="alert alert-warning" v-if="!isFetching && adjuntoProyectoFases && adjuntoProyectoFases.length === 0">
            <span v-text="$t('ciecytApp.adjuntoProyectoFase.home.notFound')">No adjuntoProyectoFases found</span>
        </div>
        <div class="table-responsive" v-if="adjuntoProyectoFases && adjuntoProyectoFases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nombreAdjunto')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreAdjunto')">Nombre Adjunto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaCreacion')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaCreacion')">Fecha Creacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaModificacion')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaModificacion')">Fecha Modificacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('estadoAdjunto')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.estadoAdjunto')">Estado Adjunto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('adjuntoProyectoFase')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFase')">Adjunto Proyecto Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nombreArchivoOriginal')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreArchivoOriginal')">Nombre Archivo Original</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('archivo')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')">Archivo</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'archivo'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaInicio')">Fecha Inicio</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaFin')">Fecha Fin</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('adjuntoProyectoFaseFaseFase')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFaseFase')">Adjunto Proyecto Fase  Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="adjuntoProyectoFase in adjuntoProyectoFases"
                    :key="adjuntoProyectoFase.id">
                    <td>
                        <router-link :to="{name: 'AdjuntoProyectoFaseView', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}">{{adjuntoProyectoFase.id}}</router-link>
                    </td>
                    <td>{{adjuntoProyectoFase.nombreAdjunto}}</td>
                    <td>{{adjuntoProyectoFase.fechaCreacion}}</td>
                    <td>{{adjuntoProyectoFase.fechaModificacion}}</td>
                    <td>{{adjuntoProyectoFase.estadoAdjunto}}</td>
                    <td>{{adjuntoProyectoFase.adjuntoProyectoFase}}</td>
                    <td>{{adjuntoProyectoFase.nombreArchivoOriginal}}</td>
                     <td>
                        <a v-if="adjuntoProyectoFase.archivo" v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.archivo)" v-text="$t('entity.action.open')">open</a>
                        <span v-if="adjuntoProyectoFase.archivo">{{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.archivo)}}</span>
                    </td>
                    <td>{{adjuntoProyectoFase.fechaInicio}}</td>
                    <td>{{adjuntoProyectoFase.fechaFin}}</td>
                    <td>
                        <div v-if="adjuntoProyectoFase.adjuntoProyectoFaseFaseId">
                            <router-link :to="{name: 'ProyectoFaseView', params: {adjuntoProyectoFaseFaseId: adjuntoProyectoFase.adjuntoProyectoFaseFaseId}}">{{adjuntoProyectoFase.adjuntoProyectoFaseFaseFase}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdjuntoProyectoFaseView', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdjuntoProyectoFaseEdit', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(adjuntoProyectoFase)"
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
            <span slot="modal-title"><span id="ciecytApp.adjuntoProyectoFase.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-adjuntoProyectoFase-heading" v-bind:title="$t('ciecytApp.adjuntoProyectoFase.delete.question')">Are you sure you want to delete this Adjunto Proyecto Fase?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-adjuntoProyectoFase" v-text="$t('entity.action.delete')" v-on:click="removeAdjuntoProyectoFase()">Delete</button>
            </div>
        </b-modal>
        <div v-show="adjuntoProyectoFases && adjuntoProyectoFases.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-proyecto-fase.component.ts">
</script>
