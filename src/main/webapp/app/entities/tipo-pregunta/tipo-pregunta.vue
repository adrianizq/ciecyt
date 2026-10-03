<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.tipoPregunta.home.title')" id="tipo-pregunta-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'TipoPreguntaCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-tipo-pregunta" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.tipoPregunta.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && tipoPreguntas && tipoPreguntas.length === 0" :titulo="$t('ciecytApp.general.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="tipoPreguntas && tipoPreguntas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('tipoPregunta')"><span v-text="$t('ciecytApp.tipoPregunta.tipoPregunta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('tipoDato')"><span v-text="$t('ciecytApp.tipoPregunta.tipoDato')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="tipoPregunta in tipoPreguntas"
                    :key="tipoPregunta.id">
                    <td>
                        <router-link :to="{name: 'TipoPreguntaView', params: {tipoPreguntaId: tipoPregunta.id}}">{{tipoPregunta.id}}</router-link>
                    </td>
                    <td>{{tipoPregunta.tipoPregunta}}</td>
                    <td>{{tipoPregunta.tipoDato}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'TipoPreguntaView', params: {tipoPreguntaId: tipoPregunta.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'TipoPreguntaEdit', params: {tipoPreguntaId: tipoPregunta.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(tipoPregunta)"
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
<span ><span id="ciecytApp.tipoPregunta.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-tipoPregunta-heading" v-bind:title="$t('ciecytApp.tipoPregunta.delete.question')">Are you sure you want to delete this Tipo Pregunta?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-tipoPregunta" v-text="$t('entity.action.delete')" v-on:click="removeTipoPregunta()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="tipoPreguntas && tipoPreguntas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tipo-pregunta.component.ts">
</script>
