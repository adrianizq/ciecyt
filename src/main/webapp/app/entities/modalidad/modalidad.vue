<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.modalidad.home.title')" id="modalidad-heading">Modalidads</span>
            <router-link :to="{name: 'ModalidadCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-modalidad">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.modalidad.home.createLabel')">
                    Create a new Modalidad
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
        <div class="alert alert-warning" v-if="!isFetching && modalidads && modalidads.length === 0">
            <span v-text="$t('ciecytApp.modalidad.home.notFound')">No modalidads found</span>
        </div>
        <div class="table-responsive" v-if="modalidads && modalidads.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('modalidad')"><span v-text="$t('ciecytApp.modalidad.modalidad')">Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('contieneLinea')"><span v-text="$t('ciecytApp.modalidad.contieneLinea')">Contiene Linea</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('modalidadAcuerdoAcuerdo')"><span v-text="$t('ciecytApp.modalidad.modalidadAcuerdo')">Modalidad Acuerdo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="modalidad in modalidads"
                    :key="modalidad.id">
                    <td>
                        <router-link :to="{name: 'ModalidadView', params: {modalidadId: modalidad.id}}">{{modalidad.id}}</router-link>
                    </td>
                    <td>{{modalidad.modalidad}}</td>
                    <td>{{modalidad.contieneLinea}}</td>
                    <td>
                        <div v-if="modalidad.modalidadAcuerdoId">
                            <router-link :to="{name: 'AcuerdoView', params: {acuerdoId: modalidad.modalidadAcuerdoId}}">{{modalidad.modalidadAcuerdoAcuerdo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ModalidadView', params: {modalidadId: modalidad.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ModalidadEdit', params: {modalidadId: modalidad.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(modalidad)"
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
            <span slot="modal-title"><span id="ciecytApp.modalidad.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-modalidad-heading" v-bind:title="$t('ciecytApp.modalidad.delete.question')">Are you sure you want to delete this Modalidad?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-modalidad" v-text="$t('entity.action.delete')" v-on:click="removeModalidad()">Delete</button>
            </div>
        </b-modal>
        <div v-show="modalidads && modalidads.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./modalidad.component.ts">
</script>
