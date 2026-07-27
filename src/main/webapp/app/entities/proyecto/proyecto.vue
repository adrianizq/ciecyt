<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.proyecto.home.title')" id="proyecto-heading">Proyectos</span>
            <router-link :to="{name: 'ProyectoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-proyecto">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.proyecto.home.createLabel')">
                    Create a new Proyecto
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
        <div class="alert alert-warning" v-if="!isFetching && proyectos && proyectos.length === 0">
            <span v-text="$t('ciecytApp.proyecto.home.notFound')">No proyectos found</span>
        </div>
        <div class="table-responsive" v-if="proyectos && proyectos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('titulo')"><span v-text="$t('ciecytApp.proyecto.titulo')">Titulo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('url')"><span v-text="$t('ciecytApp.proyecto.url')">Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('lugarEjecucion')"><span v-text="$t('ciecytApp.proyecto.lugarEjecucion')">Lugar Ejecucion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('duracion')"><span v-text="$t('ciecytApp.proyecto.duracion')">Duracion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaIni')"><span v-text="$t('ciecytApp.proyecto.fechaIni')">Fecha Ini</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.proyecto.fechaFin')">Fecha Fin</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('contrapartidaPesos')"><span v-text="$t('ciecytApp.proyecto.contrapartidaPesos')">Contrapartida Pesos</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('contrapartidaEspecie')"><span v-text="$t('ciecytApp.proyecto.contrapartidaEspecie')">Contrapartida Especie</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('palabrasClave')"><span v-text="$t('ciecytApp.proyecto.palabrasClave')">Palabras Clave</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('convocatoria')"><span v-text="$t('ciecytApp.proyecto.convocatoria')">Convocatoria</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoLineaInvestigacionLinea')"><span v-text="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')">Proyecto Linea Investigacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoGrupoSemilleroNombre')"><span v-text="$t('ciecytApp.proyecto.proyectoGrupoSemillero')">Proyecto Grupo Semillero</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('proyectoModalidadModalidad')"><span v-text="$t('ciecytApp.proyecto.proyectoModalidad')">Proyecto Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('facultadId')"><span v-text="$t('ciecytApp.proyecto.facultad')">Facultad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('subLineaLineaInvestigacionLinea')"><span v-text="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')">Sub Linea Linea Investigacion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="proyecto in proyectos"
                    :key="proyecto.id">
                    <td>
                        <router-link :to="{name: 'ProyectoView', params: {proyectoId: proyecto.id}}">{{proyecto.id}}</router-link>
                    </td>
                    <td>{{proyecto.titulo}}</td>
                    <td>{{proyecto.url}}</td>
                    <td>{{proyecto.lugarEjecucion}}</td>
                    <td>{{proyecto.duracion}}</td>
                    <td>{{proyecto.fechaIni}}</td>
                    <td>{{proyecto.fechaFin}}</td>
                    <td>{{proyecto.contrapartidaPesos}}</td>
                    <td>{{proyecto.contrapartidaEspecie}}</td>
                    <td>{{proyecto.palabrasClave}}</td>
                    <td>{{proyecto.convocatoria}}</td>
                    <td>
                        <div v-if="proyecto.proyectoLineaInvestigacionId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {proyectoLineaInvestigacionId: proyecto.proyectoLineaInvestigacionId}}">{{proyecto.proyectoLineaInvestigacionLinea}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="proyecto.proyectoGrupoSemilleroId">
                            <router-link :to="{name: 'GrupoSemilleroView', params: {proyectoGrupoSemilleroId: proyecto.proyectoGrupoSemilleroId}}">{{proyecto.proyectoGrupoSemilleroNombre}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="proyecto.proyectoModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {proyectoModalidadId: proyecto.proyectoModalidadId}}">{{proyecto.proyectoModalidadModalidad}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="proyecto.facultadId">
                            <router-link :to="{name: 'FacultadView', params: {facultadId: proyecto.facultadId}}">{{proyecto.facultadId}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="proyecto.subLineaLineaInvestigacionId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {subLineaLineaInvestigacionId: proyecto.subLineaLineaInvestigacionId}}">{{proyecto.subLineaLineaInvestigacionLinea}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProyectoEdit', params: {proyectoId: proyecto.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(proyecto)"
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
            <span slot="modal-title"><span id="ciecytApp.proyecto.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-proyecto-heading" v-bind:title="$t('ciecytApp.proyecto.delete.question')">Are you sure you want to delete this Proyecto?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-proyecto" v-text="$t('entity.action.delete')" v-on:click="removeProyecto()">Delete</button>
            </div>
        </b-modal>
        <div v-show="proyectos && proyectos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto.component.ts">
</script>
