<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.grupoSemillero.home.title')" id="grupo-semillero-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'GrupoSemilleroCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-grupo-semillero" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.grupoSemillero.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && grupoSemilleros && grupoSemilleros.length === 0" :titulo="$t('ciecytApp.grupoSemillero.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="grupoSemilleros && grupoSemilleros.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'nombre' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('nombre')" v-on:keydown.enter="changeOrder('nombre')" v-on:keydown.space.prevent="changeOrder('nombre')"><span v-text="$t('ciecytApp.grupoSemillero.nombre')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'tipo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('tipo')" v-on:keydown.enter="changeOrder('tipo')" v-on:keydown.space.prevent="changeOrder('tipo')"><span v-text="$t('ciecytApp.grupoSemillero.tipo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="grupoSemillero in grupoSemilleros"
                    :key="grupoSemillero.id">
                    <td>
                        <router-link :to="{name: 'GrupoSemilleroView', params: {grupoSemilleroId: grupoSemillero.id}}">{{grupoSemillero.id}}</router-link>
                    </td>
                    <td>{{grupoSemillero.nombre}}</td>
                    <td>{{grupoSemillero.tipo}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'GrupoSemilleroView', params: {grupoSemilleroId: grupoSemillero.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'GrupoSemilleroEdit', params: {grupoSemilleroId: grupoSemillero.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(grupoSemillero)"
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
<span ><span id="ciecytApp.grupoSemillero.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-grupoSemillero-heading" v-bind:title="$t('ciecytApp.grupoSemillero.delete.question')">Are you sure you want to delete this Grupo Semillero?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-grupoSemillero" v-text="$t('entity.action.delete')" v-on:click="removeGrupoSemillero()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="grupoSemilleros && grupoSemilleros.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./grupo-semillero.component.ts">
</script>
