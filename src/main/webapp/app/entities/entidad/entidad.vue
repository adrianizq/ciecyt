<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.entidad.home.title')" id="entidad-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'EntidadCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-entidad" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.entidad.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && entidads && entidads.length === 0" :titulo="$t('ciecytApp.entidad.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="entidads && entidads.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'entidad' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('entidad')" v-on:keydown.enter="changeOrder('entidad')" v-on:keydown.space.prevent="changeOrder('entidad')"><span v-text="$t('ciecytApp.entidad.entidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'nit' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('nit')" v-on:keydown.enter="changeOrder('nit')" v-on:keydown.space.prevent="changeOrder('nit')"><span v-text="$t('ciecytApp.entidad.nit')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="entidad in entidads"
                    :key="entidad.id">
                    <td>
                        <router-link :to="{name: 'EntidadView', params: {entidadId: entidad.id}}">{{entidad.id}}</router-link>
                    </td>
                    <td>{{entidad.entidad}}</td>
                    <td>{{entidad.nit}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'EntidadView', params: {entidadId: entidad.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'EntidadEdit', params: {entidadId: entidad.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(entidad)"
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
<span ><span id="ciecytApp.entidad.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-entidad-heading" v-bind:title="$t('ciecytApp.entidad.delete.question')">Are you sure you want to delete this Entidad?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-entidad" v-text="$t('entity.action.delete')" v-on:click="removeEntidad()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="entidads && entidads.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./entidad.component.ts">
</script>
