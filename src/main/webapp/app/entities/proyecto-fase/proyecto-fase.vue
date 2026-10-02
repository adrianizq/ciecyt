<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.proyectoFase.home.title')" id="proyecto-fase-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoFaseCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-proyecto-fase" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.proyectoFase.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && proyectoFases && proyectoFases.length === 0">
            <span v-text="$t('ciecytApp.proyectoFase.home.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="proyectoFases && proyectoFases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.proyectoFase.titulo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cumplida')"><span v-text="$t('ciecytApp.proyectoFase.cumplida')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaCumplimiento')"><span v-text="$t('ciecytApp.proyectoFase.fechaCumplimiento')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('observaciones')"><span v-text="$t('ciecytApp.proyectoFase.observaciones')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoFaseFasesFase')"><span v-text="$t('ciecytApp.proyectoFase.proyectoFaseFases')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoFaseProyectoTitulo')"><span v-text="$t('ciecytApp.proyectoFase.proyectoFaseProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="proyectoFase in proyectoFases"
                    :key="proyectoFase.id">
                    <td>
                        <router-link :to="{name: 'ProyectoFaseView', params: {proyectoFaseId: proyectoFase.id}}">{{proyectoFase.id}}</router-link>
                    </td>
                    <td>{{proyectoFase.titulo}}</td>
                    <td>{{proyectoFase.cumplida}}</td>
                    <td>{{proyectoFase.fechaCumplimiento}}</td>
                    <td>{{proyectoFase.observaciones}}</td>
                    <td>
                        <div v-if="proyectoFase.proyectoFaseFasesId">
                            <router-link :to="{name: 'FasesView', params: {proyectoFaseFasesId: proyectoFase.proyectoFaseFasesId}}">{{proyectoFase.proyectoFaseFasesFase}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="proyectoFase.proyectoFaseProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoFaseProyectoId: proyectoFase.proyectoFaseProyectoId}}">{{proyectoFase.proyectoFaseProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoFaseView', params: {proyectoFaseId: proyectoFase.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoFaseEdit', params: {proyectoFaseId: proyectoFase.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(proyectoFase)"
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
<span ><span id="ciecytApp.proyectoFase.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-proyectoFase-heading" v-bind:title="$t('ciecytApp.proyectoFase.delete.question')">Are you sure you want to delete this Proyecto Fase?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-proyectoFase" v-text="$t('entity.action.delete')" v-on:click="removeProyectoFase()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="proyectoFases && proyectoFases.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-fase.component.ts">
</script>
