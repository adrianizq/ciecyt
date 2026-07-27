<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.integranteProyecto.home.title')" id="integrante-proyecto-heading">Integrante Proyectos</span>
            <router-link :to="{name: 'IntegranteProyectoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-integrante-proyecto">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.integranteProyecto.home.createLabel')">
                    Create a new Integrante Proyecto
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
        <div class="alert alert-warning" v-if="!isFetching && integranteProyectos && integranteProyectos.length === 0">
            <span v-text="$t('ciecytApp.integranteProyecto.home.notFound')">No integranteProyectos found</span>
        </div>
        <div class="table-responsive" v-if="integranteProyectos && integranteProyectos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('integrante')"><span v-text="$t('ciecytApp.integranteProyecto.integrante')">Integrante</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.integranteProyecto.descripcion')">Descripcion</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('integranteProyectoUserLogin')"><span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoUser')">Integrante Proyecto User</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('integranteProyectoProyectoTitulo')"><span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoProyecto')">Integrante Proyecto Proyecto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('integranteProyectoRolesModalidadRol')"><span v-text="$t('ciecytApp.integranteProyecto.integranteProyectoRolesModalidad')">Integrante Proyecto Roles Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="integranteProyecto in integranteProyectos"
                    :key="integranteProyecto.id">
                    <td>
                        <router-link :to="{name: 'IntegranteProyectoView', params: {integranteProyectoId: integranteProyecto.id}}">{{integranteProyecto.id}}</router-link>
                    </td>
                    <td>{{integranteProyecto.integrante}}</td>
                    <td>{{integranteProyecto.descripcion}}</td>
                    <td>
                        {{integranteProyecto.integranteProyectoUserLogin}}
                    </td>
                    <td>
                        <div v-if="integranteProyecto.integranteProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {integranteProyectoProyectoId: integranteProyecto.integranteProyectoProyectoId}}">{{integranteProyecto.integranteProyectoProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="integranteProyecto.integranteProyectoRolesModalidadId">
                            <router-link :to="{name: 'RolesModalidadView', params: {integranteProyectoRolesModalidadId: integranteProyecto.integranteProyectoRolesModalidadId}}">{{integranteProyecto.integranteProyectoRolesModalidadRol}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'IntegranteProyectoView', params: {integranteProyectoId: integranteProyecto.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'IntegranteProyectoEdit', params: {integranteProyectoId: integranteProyecto.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(integranteProyecto)"
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
            <span slot="modal-title"><span id="ciecytApp.integranteProyecto.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-integranteProyecto-heading" v-bind:title="$t('ciecytApp.integranteProyecto.delete.question')">Are you sure you want to delete this Integrante Proyecto?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-integranteProyecto" v-text="$t('entity.action.delete')" v-on:click="removeIntegranteProyecto()">Delete</button>
            </div>
        </b-modal>
        <div v-show="integranteProyectos && integranteProyectos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./integrante-proyecto.component.ts">
</script>
