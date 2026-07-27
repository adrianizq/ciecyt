<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.home.title')" id="adjunto-retroalimentacion-heading">Adjunto Retroalimentacions</span>
            <router-link :to="{name: 'AdjuntoRetroalimentacionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-adjunto-retroalimentacion">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.adjuntoRetroalimentacion.home.createLabel')">
                    Create a new Adjunto Retroalimentacion
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
        <div class="alert alert-warning" v-if="!isFetching && adjuntoRetroalimentacions && adjuntoRetroalimentacions.length === 0">
            <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.home.notFound')">No adjuntoRetroalimentacions found</span>
        </div>
        <div class="table-responsive" v-if="adjuntoRetroalimentacions && adjuntoRetroalimentacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nombreAdjunto')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreAdjunto')">Nombre Adjunto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaCreacion')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaCreacion')">Fecha Creacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaModificacion')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaModificacion')">Fecha Modificacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('estadoAdjunto')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.estadoAdjunto')">Estado Adjunto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('adjuntoRetroalimentacion')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacion')">Adjunto Retroalimentacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nombreArchivoOriginal')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreArchivoOriginal')">Nombre Archivo Original</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaInicio')">Fecha Inicio</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaFin')">Fecha Fin</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('adjuntoRetroalimentacionRetroalimentacionTitulo')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacion')">Adjunto Retroalimentacion Retroalimentacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="adjuntoRetroalimentacion in adjuntoRetroalimentacions"
                    :key="adjuntoRetroalimentacion.id">
                    <td>
                        <router-link :to="{name: 'AdjuntoRetroalimentacionView', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}">{{adjuntoRetroalimentacion.id}}</router-link>
                    </td>
                    <td>{{adjuntoRetroalimentacion.nombreAdjunto}}</td>
                    <td>{{adjuntoRetroalimentacion.fechaCreacion}}</td>
                    <td>{{adjuntoRetroalimentacion.fechaModificacion}}</td>
                    <td>{{adjuntoRetroalimentacion.estadoAdjunto}}</td>
                    <td>{{adjuntoRetroalimentacion.adjuntoRetroalimentacion}}</td>
                    <td>{{adjuntoRetroalimentacion.nombreArchivoOriginal}}</td>
                    <td>{{adjuntoRetroalimentacion.fechaInicio}}</td>
                    <td>{{adjuntoRetroalimentacion.fechaFin}}</td>
                    <td>
                        <div v-if="adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionId">
                            <router-link :to="{name: 'RetroalimentacionView', params: {adjuntoRetroalimentacionRetroalimentacionId: adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionId}}">{{adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacionTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AdjuntoRetroalimentacionView', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AdjuntoRetroalimentacionEdit', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(adjuntoRetroalimentacion)"
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
            <span slot="modal-title"><span id="ciecytApp.adjuntoRetroalimentacion.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-adjuntoRetroalimentacion-heading" v-bind:title="$t('ciecytApp.adjuntoRetroalimentacion.delete.question')">Are you sure you want to delete this Adjunto Retroalimentacion?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-adjuntoRetroalimentacion" v-text="$t('entity.action.delete')" v-on:click="removeAdjuntoRetroalimentacion()">Delete</button>
            </div>
        </b-modal>
        <div v-show="adjuntoRetroalimentacions && adjuntoRetroalimentacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-retroalimentacion.component.ts">
</script>
