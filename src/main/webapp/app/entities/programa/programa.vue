<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.programa.home.title')" id="programa-heading">Programs</span>
            <router-link :to="{name: 'ProgramaCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-programa">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.programa.home.createLabel')">
                    Create a new Programa
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
        <div class="alert alert-warning" v-if="!isFetching && programs && programs.length === 0">
            <span v-text="$t('ciecytApp.programa.home.notFound')">No programas found</span>
        </div>
        <div class="table-responsive" v-if="programs && programs.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('programa')"><span v-text="$t('ciecytApp.programa.programa')">Programa</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                     <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.programa.descripcion')">Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                      <th v-on:click="changeOrder('codigoInterno')"><span v-text="$t('ciecytApp.programa.codigoInterno')">codigoInterno</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                      <th v-on:click="changeOrder('codigoInterno')"><span v-text="$t('ciecytApp.programa.codigoSnies')">codigoSnies</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                      <th v-on:click="changeOrder('creditos')"><span v-text="$t('ciecytApp.programa.creditos')">creditos</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('ciclo')"><span v-text="$t('ciecytApp.programa.ciclo')">ciclo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('resolucion')"><span v-text="$t('ciecytApp.programa.resolucion')">resolucion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.programa.titulo')">titulo que otorga</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('duracionSemestres')"><span v-text="$t('ciecytApp.programa.duracionSemestres')">duracion en semestres</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>

                    <th v-on:click="changeOrder('programaFacultad')"><span v-text="$t('ciecytApp.programa.programaFacultad')">Programa Acuerdo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>

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
                            <router-link :to="{name: 'ProgramaView', params: {programaId: programa.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProgramaEdit', params: {programaId: programa.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(programa)"
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
            <span slot="modal-title"><span id="ciecytApp.programa.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-programa-heading" v-bind:title="$t('ciecytApp.programa.delete.question')">Are you sure you want to delete this Programa?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-programa" v-text="$t('entity.action.delete')" v-on:click="removePrograma()">Delete</button>
            </div>
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
