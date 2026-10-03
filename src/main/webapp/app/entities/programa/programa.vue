<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.programa.home.title')" id="programa-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ProgramaCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-programa" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.programa.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && programs && programs.length === 0" :titulo="$t('ciecytApp.programa.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="programs && programs.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('programa')"><span v-text="$t('ciecytApp.programa.programa')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                     <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.programa.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                      <th v-on:click="changeOrder('codigoInterno')"><span v-text="$t('ciecytApp.programa.codigoInterno')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                      <th v-on:click="changeOrder('codigoInterno')"><span v-text="$t('ciecytApp.programa.codigoSnies')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                      <th v-on:click="changeOrder('creditos')"><span v-text="$t('ciecytApp.programa.creditos')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ciclo')"><span v-text="$t('ciecytApp.programa.ciclo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resolucion')"><span v-text="$t('ciecytApp.programa.resolucion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.programa.titulo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('duracionSemestres')"><span v-text="$t('ciecytApp.programa.duracionSemestres')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>

                    <th v-on:click="changeOrder('programaFacultad')"><span v-text="$t('ciecytApp.programa.programaFacultad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>

                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="programa in programs"
                    :key="programa.id">
                    <td>
                        <router-link :to="{name: 'ProgramaView', params: {programaId: programa.id}}">{{programa.id}}</router-link>
                    </td>
                    <td>{{programa.programa}}</td>
                     <td>{{programa.descripcion}}</td>
                      <td>{{programa.codigoInterno}}</td>
                       <td>{{programa.codigoSnies}}</td>
                        <td>{{programa.creditos}}</td>
                         <td>{{programa.ciclo}}</td>
                          <td>{{programa.resolucion}}</td>
                           <td>{{programa.titulo}}</td>
                            <td>{{programa.duracionSemestres}}</td>
                    <td>
                        <div v-if="programa.programaFacultadId">
                            <router-link :to="{name: 'FacultadView', params: {facultadId: programa.programaFacultadId}}">{{programa.programaFacultadId}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProgramaView', params: {programaId: programa.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProgramaEdit', params: {programaId: programa.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(programa)"
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
<span ><span id="ciecytApp.programa.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-programa-heading" v-bind:title="$t('ciecytApp.programa.delete.question')">Are you sure you want to delete this Programa?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-programa" v-text="$t('entity.action.delete')" v-on:click="removePrograma()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="programs && programs.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./programa.component.ts">
</script>
