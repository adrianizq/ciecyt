<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.categorizacion.home.title')" id="categorizacion-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'CategorizacionCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-categorizacion" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.categorizacion.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && categorizacions && categorizacions.length === 0">
            <span v-text="$t('ciecytApp.categorizacion.home.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="categorizacions && categorizacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('categoria')"><span v-text="$t('ciecytApp.categorizacion.categoria')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.categorizacion.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('categorizacionProyectoTitulo')"><span v-text="$t('ciecytApp.categorizacion.categorizacionProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CategorizacionView', params: {categorizacionId: categorizacion.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'CategorizacionEdit', params: {categorizacionId: categorizacion.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(categorizacion)"
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
<span ><span id="ciecytApp.categorizacion.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-categorizacion-heading" v-bind:title="$t('ciecytApp.categorizacion.delete.question')">Are you sure you want to delete this Categorizacion?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-categorizacion" v-text="$t('entity.action.delete')" v-on:click="removeCategorizacion()"></button>
            </div>
</template>
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
