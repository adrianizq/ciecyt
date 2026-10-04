<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.solicitud.home.title')" id="solicitud-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'SolicitudCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-solicitud" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.solicitud.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && solicituds && solicituds.length === 0" :titulo="$t('ciecytApp.solicitud.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="solicituds && solicituds.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'estado' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('estado')" v-on:keydown.enter="changeOrder('estado')" v-on:keydown.space.prevent="changeOrder('estado')"><span v-text="$t('ciecytApp.solicitud.estado')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'asunto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('asunto')" v-on:keydown.enter="changeOrder('asunto')" v-on:keydown.space.prevent="changeOrder('asunto')"><span v-text="$t('ciecytApp.solicitud.asunto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'textoSolicitud' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('textoSolicitud')" v-on:keydown.enter="changeOrder('textoSolicitud')" v-on:keydown.space.prevent="changeOrder('textoSolicitud')"><span v-text="$t('ciecytApp.solicitud.textoSolicitud')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'fechaSolicitud' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('fechaSolicitud')" v-on:keydown.enter="changeOrder('fechaSolicitud')" v-on:keydown.space.prevent="changeOrder('fechaSolicitud')"><span v-text="$t('ciecytApp.solicitud.fechaSolicitud')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'solicitudIntegranteProyectoIntegrante' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('solicitudIntegranteProyectoIntegrante')" v-on:keydown.enter="changeOrder('solicitudIntegranteProyectoIntegrante')" v-on:keydown.space.prevent="changeOrder('solicitudIntegranteProyectoIntegrante')"><span v-text="$t('ciecytApp.solicitud.solicitudIntegranteProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="solicitud in solicituds"
                    :key="solicitud.id">
                    <td>
                        <router-link :to="{name: 'SolicitudView', params: {solicitudId: solicitud.id}}">{{solicitud.id}}</router-link>
                    </td>
                    <td>{{solicitud.estado}}</td>
                    <td>{{solicitud.asunto}}</td>
                    <td>{{solicitud.textoSolicitud}}</td>
                    <td>{{solicitud.fechaSolicitud}}</td>
                    <td>
                        <div v-if="solicitud.solicitudIntegranteProyectoId">
                            <router-link :to="{name: 'IntegranteProyectoView', params: {solicitudIntegranteProyectoId: solicitud.solicitudIntegranteProyectoId}}">{{solicitud.solicitudIntegranteProyectoIntegrante}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'SolicitudView', params: {solicitudId: solicitud.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'SolicitudEdit', params: {solicitudId: solicitud.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(solicitud)"
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
<span ><span id="ciecytApp.solicitud.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-solicitud-heading" v-bind:title="$t('ciecytApp.solicitud.delete.question')">Are you sure you want to delete this Solicitud?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-solicitud" v-text="$t('entity.action.delete')" v-on:click="removeSolicitud()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="solicituds && solicituds.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./solicitud.component.ts">
</script>
