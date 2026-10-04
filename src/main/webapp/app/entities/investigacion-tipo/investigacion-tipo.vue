<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.investigacionTipo.home.title')" id="investigacion-tipo-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'InvestigacionTipoCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-investigacion-tipo" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.investigacionTipo.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && investigacionTips && investigacionTips.length === 0" :titulo="$t('ciecytApp.general.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="investigacionTips && investigacionTips.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'investigacionTipo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('investigacionTipo')" v-on:keydown.enter="changeOrder('investigacionTipo')" v-on:keydown.space.prevent="changeOrder('investigacionTipo')"><span v-text="$t('ciecytApp.investigacionTipo.investigacionTipo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'investigacionTipoDescripcion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('investigacionTipoDescripcion')" v-on:keydown.enter="changeOrder('investigacionTipoDescripcion')" v-on:keydown.space.prevent="changeOrder('investigacionTipoDescripcion')"><span v-text="$t('ciecytApp.investigacionTipo.investigacionTipoDescripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'investigacionTipoTipo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('investigacionTipoTipo')" v-on:keydown.enter="changeOrder('investigacionTipoTipo')" v-on:keydown.space.prevent="changeOrder('investigacionTipoTipo')"><span v-text="$t('ciecytApp.investigacionTipo.tipo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'investigacionTipoTipoDescripcion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('investigacionTipoTipoDescripcion')" v-on:keydown.enter="changeOrder('investigacionTipoTipoDescripcion')" v-on:keydown.space.prevent="changeOrder('investigacionTipoTipoDescripcion')"><span v-text="$t('ciecytApp.investigacionTipo.tipoDescripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="investigacionTipo in investigacionTips"
                    :key="investigacionTipo.id">
                    <td>
                        <router-link :to="{name: 'InvestigacionTipoView', params: {investigacionTipoId: investigacionTipo.id}}">{{investigacionTipo.id}}</router-link>
                    </td>
                    <td>{{investigacionTipo.investigacionTipo}}</td>
                    <td>{{investigacionTipo.investigacionTipoDescripcion}}</td>
                    <td>{{investigacionTipo.tipo}}</td>
                    <td>{{investigacionTipo.tipoDescripcion}}</td>
                    
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'InvestigacionTipoView', params: {investigacionTipoId: investigacionTipo.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'InvestigacionTipoEdit', params: {investigacionTipoId: investigacionTipo.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(investigacionTipo)"
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
<span ><span id="ciecytApp.investigacionTipo.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-investigacionTipo-heading" v-bind:title="$t('ciecytApp.investigacionTipo.delete.question')">Are you sure you want to delete this Linea Investigacion?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-investigacionTipo" v-text="$t('entity.action.delete')" v-on:click="removeInvestigacionTipo()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="investigacionTips && investigacionTips.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./investigacion-tipo.component.ts">
</script>
