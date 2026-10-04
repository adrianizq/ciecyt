<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.retroalimentacion.home.title')" id="retroalimentacion-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'RetroalimentacionCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-retroalimentacion" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.retroalimentacion.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && retroalimentacions && retroalimentacions.length === 0" :titulo="$t('ciecytApp.retroalimentacion.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="retroalimentacions && retroalimentacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'titulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('titulo')" v-on:keydown.enter="changeOrder('titulo')" v-on:keydown.space.prevent="changeOrder('titulo')"><span v-text="$t('ciecytApp.retroalimentacion.titulo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'retroalimentacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('retroalimentacion')" v-on:keydown.enter="changeOrder('retroalimentacion')" v-on:keydown.space.prevent="changeOrder('retroalimentacion')"><span v-text="$t('ciecytApp.retroalimentacion.retroalimentacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaRetroalimentacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaRetroalimentacion')" v-on:keydown.enter="changeOrder('fechaRetroalimentacion')" v-on:keydown.space.prevent="changeOrder('fechaRetroalimentacion')"><span v-text="$t('ciecytApp.retroalimentacion.fechaRetroalimentacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'estadoRetroalimentacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('estadoRetroalimentacion')" v-on:keydown.enter="changeOrder('estadoRetroalimentacion')" v-on:keydown.space.prevent="changeOrder('estadoRetroalimentacion')"><span v-text="$t('ciecytApp.retroalimentacion.estadoRetroalimentacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'estadoProyectoFase' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('estadoProyectoFase')" v-on:keydown.enter="changeOrder('estadoProyectoFase')" v-on:keydown.space.prevent="changeOrder('estadoProyectoFase')"><span v-text="$t('ciecytApp.retroalimentacion.estadoProyectoFase')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'retroalimentacionProyectoFaseTitulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('retroalimentacionProyectoFaseTitulo')" v-on:keydown.enter="changeOrder('retroalimentacionProyectoFaseTitulo')" v-on:keydown.space.prevent="changeOrder('retroalimentacionProyectoFaseTitulo')"><span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionProyectoFase')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'retroalimentacionUserLogin' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('retroalimentacionUserLogin')" v-on:keydown.enter="changeOrder('retroalimentacionUserLogin')" v-on:keydown.space.prevent="changeOrder('retroalimentacionUserLogin')"><span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionUser')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'RetroalimentacionView', params: {retroalimentacionId: retroalimentacion.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'RetroalimentacionEdit', params: {retroalimentacionId: retroalimentacion.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(retroalimentacion)"
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
<span ><span id="ciecytApp.retroalimentacion.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-retroalimentacion-heading" v-bind:title="$t('ciecytApp.retroalimentacion.delete.question')">Are you sure you want to delete this Retroalimentacion?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-retroalimentacion" v-text="$t('entity.action.delete')" v-on:click="removeRetroalimentacion()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="retroalimentacions && retroalimentacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./retroalimentacion.component.ts">
</script>
