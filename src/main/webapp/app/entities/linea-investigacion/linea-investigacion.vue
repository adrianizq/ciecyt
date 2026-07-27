<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.lineaInvestigacion.home.title')" id="linea-investigacion-heading">Linea Investigacions</span>
            <router-link :to="{name: 'LineaInvestigacionCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-linea-investigacion">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.lineaInvestigacion.home.createLabel')">
                    Create a new Linea Investigacion
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
        <div class="alert alert-warning" v-if="!isFetching && lineaInvestigacions && lineaInvestigacions.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')">No lineaInvestigacions found</span>
        </div>
        <div class="table-responsive" v-if="lineaInvestigacions && lineaInvestigacions.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('linea')"><span v-text="$t('ciecytApp.lineaInvestigacion.linea')">Linea</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('codigoLinea')"><span v-text="$t('ciecytApp.lineaInvestigacion.codigoLinea')">Codigo Linea</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('lineaPadreLinea')"><span v-text="$t('ciecytApp.lineaInvestigacion.lineaPadre')">Linea Padre</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('lineaInvestigacionProgramaPrograma')"><span v-text="$t('ciecytApp.lineaInvestigacion.lineaInvestigacionPrograma')">Linea Investigacion Programa</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="lineaInvestigacion in lineaInvestigacions"
                    :key="lineaInvestigacion.id">
                    <td>
                        <router-link :to="{name: 'LineaInvestigacionView', params: {lineaInvestigacionId: lineaInvestigacion.id}}">{{lineaInvestigacion.id}}</router-link>
                    </td>
                    <td>{{lineaInvestigacion.linea}}</td>
                    <td>{{lineaInvestigacion.codigoLinea}}</td>
                    <td>
                        <div v-if="lineaInvestigacion.lineaPadreId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {lineaPadreId: lineaInvestigacion.lineaPadreId}}">{{lineaInvestigacion.lineaPadreLinea}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="lineaInvestigacion.lineaInvestigacionProgramaId">
                            <router-link :to="{name: 'ProgramaView', params: { programaId: lineaInvestigacion.lineaInvestigacionProgramaId}}">{{lineaInvestigacion.lineaInvestigacionProgramaPrograma}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {lineaInvestigacionId: lineaInvestigacion.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'LineaInvestigacionEdit', params: {lineaInvestigacionId: lineaInvestigacion.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(lineaInvestigacion)"
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
            <span slot="modal-title"><span id="ciecytApp.lineaInvestigacion.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-lineaInvestigacion-heading" v-bind:title="$t('ciecytApp.lineaInvestigacion.delete.question')">Are you sure you want to delete this Linea Investigacion?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-lineaInvestigacion" v-text="$t('entity.action.delete')" v-on:click="removeLineaInvestigacion()">Delete</button>
            </div>
        </b-modal>
        <div v-show="lineaInvestigacions && lineaInvestigacions.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./linea-investigacion.component.ts">
</script>
