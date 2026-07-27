<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.categorizacion.home.title')" id="categorizacion-heading">Categorizacions</span>
            <router-link :to="{name: 'CategorizacionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-categorizacion">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.categorizacion.home.createLabel')">
                    Create a new Categorizacion
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
        <div class="alert alert-warning" v-if="!isFetching && categorizacions && categorizacions.length === 0">
            <span v-text="$t('ciecytApp.categorizacion.home.notFound')">No categorizacions found</span>
        </div>
        <div class="table-responsive" v-if="categorizacions && categorizacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('categoria')"><span v-text="$t('ciecytApp.categorizacion.categoria')">Categoria</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.categorizacion.descripcion')">Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('categorizacionProyectoTitulo')"><span v-text="$t('ciecytApp.categorizacion.categorizacionProyecto')">Categorizacion Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="categorizacion in categorizacions"
                    :key="categorizacion.id">
                    <td>
                        <router-link :to="{name: 'CategorizacionView', params: {categorizacionId: categorizacion.id}}">{{categorizacion.id}}</router-link>
                    </td>
                    <td>{{categorizacion.categoria}}</td>
                    <td>{{categorizacion.descripcion}}</td>
                    <td>
                        <div v-if="categorizacion.categorizacionProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {categorizacionProyectoId: categorizacion.categorizacionProyectoId}}">{{categorizacion.categorizacionProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'CategorizacionView', params: {categorizacionId: categorizacion.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'CategorizacionEdit', params: {categorizacionId: categorizacion.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(categorizacion)"
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
            <span slot="modal-title"><span id="ciecytApp.categorizacion.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-categorizacion-heading" v-bind:title="$t('ciecytApp.categorizacion.delete.question')">Are you sure you want to delete this Categorizacion?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-categorizacion" v-text="$t('entity.action.delete')" v-on:click="removeCategorizacion()">Delete</button>
            </div>
        </b-modal>
        <div v-show="categorizacions && categorizacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./categorizacion.component.ts">
</script>
