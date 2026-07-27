<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.fases.home.title')" id="fases-heading">Fases</span>
            <router-link :to="{name: 'FasesCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-fases">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.fases.home.createLabel')">
                    Create a new Fases
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
        <div class="alert alert-warning" v-if="!isFetching && fases && fases.length === 0">
            <span v-text="$t('ciecytApp.fases.home.notFound')">No fases found</span>
        </div>
        <div class="table-responsive" v-if="fases && fases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fase')"><span v-text="$t('ciecytApp.fases.fase')">Fase</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('notificable')"><span v-text="$t('ciecytApp.fases.notificable')">Notificable</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fasesModalidadModalidad')"><span v-text="$t('ciecytApp.fases.fasesModalidad')">Fases Modalidad</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="fases in fases"
                    :key="fases.id">
                    <td>
                        <router-link :to="{name: 'FasesView', params: {fasesId: fases.id}}">{{fases.id}}</router-link>
                    </td>
                    <td>{{fases.fase}}</td>
                    <td>{{fases.notificable}}</td>
                    <td>
                        <div v-if="fases.fasesModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {fasesModalidadId: fases.fasesModalidadId}}">{{fases.fasesModalidadModalidad}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FasesView', params: {fasesId: fases.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FasesEdit', params: {fasesId: fases.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(fases)"
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
            <span slot="modal-title"><span id="ciecytApp.fases.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-fases-heading" v-bind:title="$t('ciecytApp.fases.delete.question')">Are you sure you want to delete this Fases?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-fases" v-text="$t('entity.action.delete')" v-on:click="removeFases()">Delete</button>
            </div>
        </b-modal>
        <div v-show="fases && fases.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./fases.component.ts">
</script>
