<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.formato.home.title')" id="formato-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'FormatoCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-formato" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.formato.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && formatoes && formatoes.length === 0" :titulo="$t('ciecytApp.formato.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="formatoes && formatoes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'formato' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('formato')" v-on:keydown.enter="changeOrder('formato')" v-on:keydown.space.prevent="changeOrder('formato')"><span v-text="$t('ciecytApp.formato.formato')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'version' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('version')" v-on:keydown.enter="changeOrder('version')" v-on:keydown.space.prevent="changeOrder('version')"><span v-text="$t('ciecytApp.formato.version')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'codigo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('codigo')" v-on:keydown.enter="changeOrder('codigo')" v-on:keydown.space.prevent="changeOrder('codigo')"><span v-text="$t('ciecytApp.formato.codigo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fecha' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fecha')" v-on:keydown.enter="changeOrder('fecha')" v-on:keydown.space.prevent="changeOrder('fecha')"><span v-text="$t('ciecytApp.formato.fecha')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="formato in formatoes"
                    :key="formato.id">
                    <td>
                        <router-link :to="{name: 'FormatoView', params: {formatoId: formato.id}}">{{formato.id}}</router-link>
                    </td>
                    <td>{{formato.formato}}</td>
                    <td>{{formato.version}}</td>
                    <td>{{formato.codigo}}</td>
                    <td>{{formato.fecha}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'FormatoView', params: {formatoId: formato.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'FormatoEdit', params: {formatoId: formato.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(formato)"
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
<span ><span id="ciecytApp.formato.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-formato-heading" v-bind:title="$t('ciecytApp.formato.delete.question')">Are you sure you want to delete this Formato?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-formato" v-text="$t('entity.action.delete')" v-on:click="removeFormato()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="formatoes && formatoes.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./formato.component.ts">
</script>
