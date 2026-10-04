<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.proyecto.home.title')" id="proyecto-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-proyecto" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.proyecto.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && proyectos && proyectos.length === 0" :titulo="$t('ciecytApp.proyecto.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="proyectos && proyectos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'titulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('titulo')" v-on:keydown.enter="changeOrder('titulo')" v-on:keydown.space.prevent="changeOrder('titulo')"><span v-text="$t('ciecytApp.proyecto.titulo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'url' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('url')" v-on:keydown.enter="changeOrder('url')" v-on:keydown.space.prevent="changeOrder('url')"><span v-text="$t('ciecytApp.proyecto.url')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'lugarEjecucion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('lugarEjecucion')" v-on:keydown.enter="changeOrder('lugarEjecucion')" v-on:keydown.space.prevent="changeOrder('lugarEjecucion')"><span v-text="$t('ciecytApp.proyecto.lugarEjecucion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'duracion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('duracion')" v-on:keydown.enter="changeOrder('duracion')" v-on:keydown.space.prevent="changeOrder('duracion')"><span v-text="$t('ciecytApp.proyecto.duracion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaIni' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaIni')" v-on:keydown.enter="changeOrder('fechaIni')" v-on:keydown.space.prevent="changeOrder('fechaIni')"><span v-text="$t('ciecytApp.proyecto.fechaIni')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaFin' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaFin')" v-on:keydown.enter="changeOrder('fechaFin')" v-on:keydown.space.prevent="changeOrder('fechaFin')"><span v-text="$t('ciecytApp.proyecto.fechaFin')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'contrapartidaPesos' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('contrapartidaPesos')" v-on:keydown.enter="changeOrder('contrapartidaPesos')" v-on:keydown.space.prevent="changeOrder('contrapartidaPesos')"><span v-text="$t('ciecytApp.proyecto.contrapartidaPesos')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'contrapartidaEspecie' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('contrapartidaEspecie')" v-on:keydown.enter="changeOrder('contrapartidaEspecie')" v-on:keydown.space.prevent="changeOrder('contrapartidaEspecie')"><span v-text="$t('ciecytApp.proyecto.contrapartidaEspecie')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'palabrasClave' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('palabrasClave')" v-on:keydown.enter="changeOrder('palabrasClave')" v-on:keydown.space.prevent="changeOrder('palabrasClave')"><span v-text="$t('ciecytApp.proyecto.palabrasClave')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'convocatoria' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('convocatoria')" v-on:keydown.enter="changeOrder('convocatoria')" v-on:keydown.space.prevent="changeOrder('convocatoria')"><span v-text="$t('ciecytApp.proyecto.convocatoria')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'proyectoLineaInvestigacionLinea' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('proyectoLineaInvestigacionLinea')" v-on:keydown.enter="changeOrder('proyectoLineaInvestigacionLinea')" v-on:keydown.space.prevent="changeOrder('proyectoLineaInvestigacionLinea')"><span v-text="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'proyectoGrupoSemilleroNombre' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('proyectoGrupoSemilleroNombre')" v-on:keydown.enter="changeOrder('proyectoGrupoSemilleroNombre')" v-on:keydown.space.prevent="changeOrder('proyectoGrupoSemilleroNombre')"><span v-text="$t('ciecytApp.proyecto.proyectoGrupoSemillero')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'proyectoModalidadModalidad' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('proyectoModalidadModalidad')" v-on:keydown.enter="changeOrder('proyectoModalidadModalidad')" v-on:keydown.space.prevent="changeOrder('proyectoModalidadModalidad')"><span v-text="$t('ciecytApp.proyecto.proyectoModalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'facultadId' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('facultadId')" v-on:keydown.enter="changeOrder('facultadId')" v-on:keydown.space.prevent="changeOrder('facultadId')"><span v-text="$t('ciecytApp.proyecto.facultad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'subLineaLineaInvestigacionLinea' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('subLineaLineaInvestigacionLinea')" v-on:keydown.enter="changeOrder('subLineaLineaInvestigacionLinea')" v-on:keydown.space.prevent="changeOrder('subLineaLineaInvestigacionLinea')"><span v-text="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoView', params: {proyectoId: proyecto.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoEdit', params: {proyectoId: proyecto.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(proyecto)"
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
<span ><span id="ciecytApp.proyecto.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-proyecto-heading" v-bind:title="$t('ciecytApp.proyecto.delete.question')">Are you sure you want to delete this Proyecto?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-proyecto" v-text="$t('entity.action.delete')" v-on:click="removeProyecto()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="proyectos && proyectos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto.component.ts">
</script>
