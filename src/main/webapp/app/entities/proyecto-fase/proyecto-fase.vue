<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.proyectoFase.home.title')" id="proyecto-fase-heading">Proyecto Fases</span>
            <router-link :to="{name: 'ProyectoFaseCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-proyecto-fase">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.proyectoFase.home.createLabel')">
                    Create a new Proyecto Fase
                </span>
            </router-link>
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
            <span v-text="$t('ciecytApp.proyectoFase.home.notFound')">No proyectoFases found</span>
        </div>
        <div class="table-responsive" v-if="proyectoFases && proyectoFases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.proyectoFase.titulo')">Titulo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('cumplida')"><span v-text="$t('ciecytApp.proyectoFase.cumplida')">Cumplida</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaCumplimiento')"><span v-text="$t('ciecytApp.proyectoFase.fechaCumplimiento')">Fecha Cumplimiento</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('observaciones')"><span v-text="$t('ciecytApp.proyectoFase.observaciones')">Observaciones</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoFaseFasesFase')"><span v-text="$t('ciecytApp.proyectoFase.proyectoFaseFases')">Proyecto Fase Fases</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoFaseProyectoTitulo')"><span v-text="$t('ciecytApp.proyectoFase.proyectoFaseProyecto')">Proyecto Fase Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link :to="{name: 'ProyectoFaseView', params: {proyectoFaseId: proyectoFase.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProyectoFaseEdit', params: {proyectoFaseId: proyectoFase.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(proyectoFase)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="ciecytApp.proyectoFase.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-proyectoFase-heading" v-bind:title="$t('ciecytApp.proyectoFase.delete.question')">Are you sure you want to delete this Proyecto Fase?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-proyectoFase" v-text="$t('entity.action.delete')" v-on:click="removeProyectoFase()">Delete</button>
            </div>
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
