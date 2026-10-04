<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.proyectoRespuestas.home.title')" id="proyecto-respuestas-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoRespuestasCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-proyecto-respuestas" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.proyectoRespuestas.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && proyectoRespuestas && proyectoRespuestas.length === 0" :titulo="$t('ciecytApp.proyectoRespuestas.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="proyectoRespuestas && proyectoRespuestas.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'respuesta' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('respuesta')" v-on:keydown.enter="changeOrder('respuesta')" v-on:keydown.space.prevent="changeOrder('respuesta')"><span v-text="$t('ciecytApp.proyectoRespuestas.respuesta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'observaciones' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('observaciones')" v-on:keydown.enter="changeOrder('observaciones')" v-on:keydown.space.prevent="changeOrder('observaciones')"><span v-text="$t('ciecytApp.proyectoRespuestas.observaciones')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'viable' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('viable')" v-on:keydown.enter="changeOrder('viable')" v-on:keydown.space.prevent="changeOrder('viable')"><span v-text="$t('ciecytApp.proyectoRespuestas.viable')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'proyectoRespuestasPreguntaPregunta' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('proyectoRespuestasPreguntaPregunta')" v-on:keydown.enter="changeOrder('proyectoRespuestasPreguntaPregunta')" v-on:keydown.space.prevent="changeOrder('proyectoRespuestasPreguntaPregunta')"><span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasPregunta')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'proyectoRespuestasProyectoTitulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('proyectoRespuestasProyectoTitulo')" v-on:keydown.enter="changeOrder('proyectoRespuestasProyectoTitulo')" v-on:keydown.space.prevent="changeOrder('proyectoRespuestasProyectoTitulo')"><span v-text="$t('ciecytApp.proyectoRespuestas.proyectoRespuestasProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="proyectoRespuestas in proyectoRespuestas"
                    :key="proyectoRespuestas.id">
                    <td>
                        <router-link :to="{name: 'ProyectoRespuestasView', params: {proyectoRespuestasId: proyectoRespuestas.id}}">{{proyectoRespuestas.id}}</router-link>
                    </td>
                    <td v-text="$t('ciecytApp.EnumRespuestas.' + proyectoRespuestas.respuesta)"></td>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoRespuestasView', params: {proyectoRespuestasId: proyectoRespuestas.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProyectoRespuestasEdit', params: {proyectoRespuestasId: proyectoRespuestas.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(proyectoRespuestas)"
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
<span ><span id="ciecytApp.proyectoRespuestas.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-proyectoRespuestas-heading" v-bind:title="$t('ciecytApp.proyectoRespuestas.delete.question')">Are you sure you want to delete this Proyecto Respuestas?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-proyectoRespuestas" v-text="$t('entity.action.delete')" v-on:click="removeProyectoRespuestas()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="proyectoRespuestas && proyectoRespuestas.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-respuestas.component.ts">
</script>
