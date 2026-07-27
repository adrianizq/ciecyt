<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.proyectoRespuestas.home.title')" id="proyecto-respuestas-heading">Proyecto Respuestas</span>
            <router-link :to="{name: 'ProyectoRespuestasCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-proyecto-respuestas">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.proyectoRespuestas.home.createLabel')">
                    Create a new Proyecto Respuestas
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
        <div class="alert alert-warning" v-if="!isFetching && proyectoRespuestas && proyectoRespuestas.length === 0">
            <span v-text="$t('ciecytApp.proyectoRespuestas.home.notFound')">No proyectoRespuestas found</span>
        </div>
        <div class="table-responsive" v-if="proyectoRespuestas && proyectoRespuestas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('respuesta')"><span v-text="$t('ciecytApp.proyectoRespuestas.respuesta')">Respuesta</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('observaciones')"><span v-text="$t('ciecytApp.proyectoRespuestas.observaciones')">Observaciones</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('viable')"><span v-text="$t('ciecytApp.proyectoRespuestas.viable')">Viable</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoRespuestasPreguntaPregunta')"><span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasPregunta')">Proyecto Respuestas Pregunta</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoRespuestasProyectoTitulo')"><span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasProyecto')">Proyecto Respuestas Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="proyectoRespuestas in proyectoRespuestas"
                    :key="proyectoRespuestas.id">
                    <td>
                        <router-link :to="{name: 'ProyectoRespuestasView', params: {proyectoRespuestasId: proyectoRespuestas.id}}">{{proyectoRespuestas.id}}</router-link>
                    </td>
                    <td v-text="$t('ciecytApp.EnumRespuestas.' + proyectoRespuestas.respuesta)">{{proyectoRespuestas.respuesta}}</td>
                    <td>{{proyectoRespuestas.observaciones}}</td>
                    <td>{{proyectoRespuestas.viable}}</td>
                    <td>
                        <div v-if="proyectoRespuestas.proyectoRespuestasPreguntaId">
                            <router-link :to="{name: 'PreguntaView', params: {proyectoRespuestasPreguntaId: proyectoRespuestas.proyectoRespuestasPreguntaId}}">{{proyectoRespuestas.proyectoRespuestasPreguntaPregunta}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="proyectoRespuestas.proyectoRespuestasProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoRespuestasProyectoId: proyectoRespuestas.proyectoRespuestasProyectoId}}">{{proyectoRespuestas.proyectoRespuestasProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProyectoRespuestasView', params: {proyectoRespuestasId: proyectoRespuestas.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProyectoRespuestasEdit', params: {proyectoRespuestasId: proyectoRespuestas.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(proyectoRespuestas)"
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
            <span slot="modal-title"><span id="ciecytApp.proyectoRespuestas.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-proyectoRespuestas-heading" v-bind:title="$t('ciecytApp.proyectoRespuestas.delete.question')">Are you sure you want to delete this Proyecto Respuestas?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-proyectoRespuestas" v-text="$t('entity.action.delete')" v-on:click="removeProyectoRespuestas()">Delete</button>
            </div>
        </b-modal>
        <div v-show="proyectoRespuestas && proyectoRespuestas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-respuestas.component.ts">
</script>
