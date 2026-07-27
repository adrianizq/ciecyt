<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.elementoProyecto.home.title')" id="elemento-proyecto-heading">Elemento Proyectos</span>
            <router-link :to="{name: 'ElementoProyectoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-elemento-proyecto">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.elementoProyecto.home.createLabel')">
                    Create a new Elemento Proyecto
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
        <div class="alert alert-warning" v-if="!isFetching && elementoProyectos && elementoProyectos.length === 0">
            <span v-text="$t('ciecytApp.elementoProyecto.home.notFound')">No elementoProyectos found</span>
        </div>
        <div class="table-responsive" v-if="elementoProyectos && elementoProyectos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('dato')"><span v-text="$t('ciecytApp.elementoProyecto.dato')">Dato</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('elementoProyectoProyectoDescripcion')"><span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyectoDescripcion')">Elemento Proyecto Proyecto Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('elementoProyectoElementoElemento')"><span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoElemento')">Elemento Proyecto Elemento</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('elementoProyectoProyectoTitulo')"><span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyecto')">Elemento Proyecto Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="elementoProyecto in elementoProyectos"
                    :key="elementoProyecto.id">
                    <td>
                        <router-link :to="{name: 'ElementoProyectoView', params: {elementoProyectoId: elementoProyecto.id}}">{{elementoProyecto.id}}</router-link>
                    </td>
                    <td>{{elementoProyecto.dato}}</td>
                    <td>{{elementoProyecto.elementoProyectoProyectoDescripcion}}</td>
                    <td>
                        <div v-if="elementoProyecto.elementoProyectoElementoId">
                            <router-link :to="{name: 'ElementoView', params: {elementoProyectoElementoId: elementoProyecto.elementoProyectoElementoId}}">{{elementoProyecto.elementoProyectoElementoElemento}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="elementoProyecto.elementoProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {elementoProyectoProyectoId: elementoProyecto.elementoProyectoProyectoId}}">{{elementoProyecto.elementoProyectoProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ElementoProyectoView', params: {elementoProyectoId: elementoProyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ElementoProyectoEdit', params: {elementoProyectoId: elementoProyecto.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(elementoProyecto)"
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
            <span slot="modal-title"><span id="ciecytApp.elementoProyecto.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-elementoProyecto-heading" v-bind:title="$t('ciecytApp.elementoProyecto.delete.question')">Are you sure you want to delete this Elemento Proyecto?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-elementoProyecto" v-text="$t('entity.action.delete')" v-on:click="removeElementoProyecto()">Delete</button>
            </div>
        </b-modal>
        <div v-show="elementoProyectos && elementoProyectos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./elemento-proyecto.component.ts">
</script>
