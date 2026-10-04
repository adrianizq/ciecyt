<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.adjuntoRetroalimentacion.home.title')" id="adjunto-retroalimentacion-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'AdjuntoRetroalimentacionCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-adjunto-retroalimentacion" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.adjuntoRetroalimentacion.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && adjuntoRetroalimentacions && adjuntoRetroalimentacions.length === 0" :titulo="$t('ciecytApp.adjuntoRetroalimentacion.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="adjuntoRetroalimentacions && adjuntoRetroalimentacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'nombreAdjunto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('nombreAdjunto')" v-on:keydown.enter="changeOrder('nombreAdjunto')" v-on:keydown.space.prevent="changeOrder('nombreAdjunto')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreAdjunto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaCreacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaCreacion')" v-on:keydown.enter="changeOrder('fechaCreacion')" v-on:keydown.space.prevent="changeOrder('fechaCreacion')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaCreacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaModificacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaModificacion')" v-on:keydown.enter="changeOrder('fechaModificacion')" v-on:keydown.space.prevent="changeOrder('fechaModificacion')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaModificacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'estadoAdjunto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('estadoAdjunto')" v-on:keydown.enter="changeOrder('estadoAdjunto')" v-on:keydown.space.prevent="changeOrder('estadoAdjunto')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.estadoAdjunto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'adjuntoRetroalimentacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('adjuntoRetroalimentacion')" v-on:keydown.enter="changeOrder('adjuntoRetroalimentacion')" v-on:keydown.space.prevent="changeOrder('adjuntoRetroalimentacion')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'nombreArchivoOriginal' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('nombreArchivoOriginal')" v-on:keydown.enter="changeOrder('nombreArchivoOriginal')" v-on:keydown.space.prevent="changeOrder('nombreArchivoOriginal')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.nombreArchivoOriginal')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaInicio' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaInicio')" v-on:keydown.enter="changeOrder('fechaInicio')" v-on:keydown.space.prevent="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaInicio')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaFin' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaFin')" v-on:keydown.enter="changeOrder('fechaFin')" v-on:keydown.space.prevent="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.fechaFin')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'adjuntoRetroalimentacionRetroalimentacionTitulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('adjuntoRetroalimentacionRetroalimentacionTitulo')" v-on:keydown.enter="changeOrder('adjuntoRetroalimentacionRetroalimentacionTitulo')" v-on:keydown.space.prevent="changeOrder('adjuntoRetroalimentacionRetroalimentacionTitulo')"><span v-text="$t('ciecytApp.adjuntoRetroalimentacion.adjuntoRetroalimentacionRetroalimentacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'AdjuntoRetroalimentacionView', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'AdjuntoRetroalimentacionEdit', params: {adjuntoRetroalimentacionId: adjuntoRetroalimentacion.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(adjuntoRetroalimentacion)"
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
<span ><span id="ciecytApp.adjuntoRetroalimentacion.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-adjuntoRetroalimentacion-heading" v-bind:title="$t('ciecytApp.adjuntoRetroalimentacion.delete.question')">Are you sure you want to delete this Adjunto Retroalimentacion?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-adjuntoRetroalimentacion" v-text="$t('entity.action.delete')" v-on:click="removeAdjuntoRetroalimentacion()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="adjuntoRetroalimentacions && adjuntoRetroalimentacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-retroalimentacion.component.ts">
</script>
