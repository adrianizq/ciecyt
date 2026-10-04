<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.adjuntoProyectoFase.home.title')" id="adjunto-proyecto-fase-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'AdjuntoProyectoFaseCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-adjunto-proyecto-fase" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.adjuntoProyectoFase.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && adjuntoProyectoFases && adjuntoProyectoFases.length === 0" :titulo="$t('ciecytApp.adjuntoProyectoFase.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="adjuntoProyectoFases && adjuntoProyectoFases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'nombreAdjunto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('nombreAdjunto')" v-on:keydown.enter="changeOrder('nombreAdjunto')" v-on:keydown.space.prevent="changeOrder('nombreAdjunto')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreAdjunto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaCreacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaCreacion')" v-on:keydown.enter="changeOrder('fechaCreacion')" v-on:keydown.space.prevent="changeOrder('fechaCreacion')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaCreacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaModificacion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaModificacion')" v-on:keydown.enter="changeOrder('fechaModificacion')" v-on:keydown.space.prevent="changeOrder('fechaModificacion')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaModificacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'estadoAdjunto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('estadoAdjunto')" v-on:keydown.enter="changeOrder('estadoAdjunto')" v-on:keydown.space.prevent="changeOrder('estadoAdjunto')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.estadoAdjunto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'adjuntoProyectoFase' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('adjuntoProyectoFase')" v-on:keydown.enter="changeOrder('adjuntoProyectoFase')" v-on:keydown.space.prevent="changeOrder('adjuntoProyectoFase')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFase')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'nombreArchivoOriginal' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('nombreArchivoOriginal')" v-on:keydown.enter="changeOrder('nombreArchivoOriginal')" v-on:keydown.space.prevent="changeOrder('nombreArchivoOriginal')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.nombreArchivoOriginal')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'archivo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('archivo')" v-on:keydown.enter="changeOrder('archivo')" v-on:keydown.space.prevent="changeOrder('archivo')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.archivo')"></span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'archivo'"></jhi-sort-indicator></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaInicio' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaInicio')" v-on:keydown.enter="changeOrder('fechaInicio')" v-on:keydown.space.prevent="changeOrder('fechaInicio')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaInicio')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaFin' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaFin')" v-on:keydown.enter="changeOrder('fechaFin')" v-on:keydown.space.prevent="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.fechaFin')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'adjuntoProyectoFaseFaseFase' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('adjuntoProyectoFaseFaseFase')" v-on:keydown.enter="changeOrder('adjuntoProyectoFaseFaseFase')" v-on:keydown.space.prevent="changeOrder('adjuntoProyectoFaseFaseFase')"><span v-text="$t('ciecytApp.adjuntoProyectoFase.adjuntoProyectoFaseFase')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="adjuntoProyectoFase in adjuntoProyectoFases"
                    :key="adjuntoProyectoFase.id">
                    <td>
                        <router-link :to="{name: 'AdjuntoProyectoFaseView', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}">{{adjuntoProyectoFase.id}}</router-link>
                    </td>
                    <td>{{adjuntoProyectoFase.nombreAdjunto}}</td>
                    <td>{{adjuntoProyectoFase.fechaCreacion}}</td>
                    <td>{{adjuntoProyectoFase.fechaModificacion}}</td>
                    <td>{{adjuntoProyectoFase.estadoAdjunto}}</td>
                    <td>{{adjuntoProyectoFase.adjuntoProyectoFase}}</td>
                    <td>{{adjuntoProyectoFase.nombreArchivoOriginal}}</td>
                     <td>
                        <a v-if="adjuntoProyectoFase.archivo" v-on:click="openFile(adjuntoProyectoFase.archivoContentType, adjuntoProyectoFase.archivo)" v-text="$t('entity.action.open')"></a>
                        <span v-if="adjuntoProyectoFase.archivo">{{adjuntoProyectoFase.archivoContentType}}, {{byteSize(adjuntoProyectoFase.archivo)}}</span>
                    </td>
                    <td>{{adjuntoProyectoFase.fechaInicio}}</td>
                    <td>{{adjuntoProyectoFase.fechaFin}}</td>
                    <td>
                        <div v-if="adjuntoProyectoFase.adjuntoProyectoFaseFaseId">
                            <router-link :to="{name: 'ProyectoFaseView', params: {adjuntoProyectoFaseFaseId: adjuntoProyectoFase.adjuntoProyectoFaseFaseId}}">{{adjuntoProyectoFase.adjuntoProyectoFaseFaseFase}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'AdjuntoProyectoFaseView', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'AdjuntoProyectoFaseEdit', params: {adjuntoProyectoFaseId: adjuntoProyectoFase.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(adjuntoProyectoFase)"
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
<span ><span id="ciecytApp.adjuntoProyectoFase.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-adjuntoProyectoFase-heading" v-bind:title="$t('ciecytApp.adjuntoProyectoFase.delete.question')">Are you sure you want to delete this Adjunto Proyecto Fase?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-adjuntoProyectoFase" v-text="$t('entity.action.delete')" v-on:click="removeAdjuntoProyectoFase()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="adjuntoProyectoFases && adjuntoProyectoFases.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./adjunto-proyecto-fase.component.ts">
</script>
