<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.modalidad.home.title')" id="modalidad-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ModalidadCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-modalidad" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.modalidad.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && modalidads && modalidads.length === 0" :titulo="$t('ciecytApp.modalidad.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="modalidads && modalidads.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('modalidad')"><span v-text="$t('ciecytApp.modalidad.modalidad')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('contieneLinea')"><span v-text="$t('ciecytApp.modalidad.contieneLinea')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('modalidadAcuerdoAcuerdo')"><span v-text="$t('ciecytApp.modalidad.modalidadAcuerdo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ModalidadView', params: {modalidadId: modalidad.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ModalidadEdit', params: {modalidadId: modalidad.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(modalidad)"
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
<span ><span id="ciecytApp.modalidad.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-modalidad-heading" v-bind:title="$t('ciecytApp.modalidad.delete.question')">Are you sure you want to delete this Modalidad?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-modalidad" v-text="$t('entity.action.delete')" v-on:click="removeModalidad()"></button>
            </div>
</template>
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
