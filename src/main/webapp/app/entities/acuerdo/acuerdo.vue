<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.acuerdo.home.title')" id="acuerdo-heading">Acuerdos</span>
            <router-link :to="{name: 'AcuerdoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-acuerdo">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.acuerdo.home.createLabel')">
                    Create a new Acuerdo
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
        <div class="alert alert-warning" v-if="!isFetching && acuerdos && acuerdos.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')">No acuerdos found</span>
        </div>
        <div class="table-responsive" v-if="acuerdos && acuerdos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('acuerdo')"><span v-text="$t('ciecytApp.acuerdo.acuerdo')">Acuerdo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('version')"><span v-text="$t('ciecytApp.acuerdo.version')">Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('codigo')"><span v-text="$t('ciecytApp.acuerdo.codigo')">Codigo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fecha')"><span v-text="$t('ciecytApp.acuerdo.fecha')">Fecha</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="acuerdo in acuerdos"
                    :key="acuerdo.id">
                    <td>
                        <router-link :to="{name: 'AcuerdoView', params: {acuerdoId: acuerdo.id}}">{{acuerdo.id}}</router-link>
                    </td>
                    <td>{{acuerdo.acuerdo}}</td>
                    <td>{{acuerdo.version}}</td>
                    <td>{{acuerdo.codigo}}</td>
                    <td>{{acuerdo.fecha}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'AcuerdoView', params: {acuerdoId: acuerdo.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'AcuerdoEdit', params: {acuerdoId: acuerdo.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(acuerdo)"
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
            <span slot="modal-title"><span id="ciecytApp.acuerdo.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-acuerdo-heading" v-bind:title="$t('ciecytApp.acuerdo.delete.question')">Are you sure you want to delete this Acuerdo?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-acuerdo" v-text="$t('entity.action.delete')" v-on:click="removeAcuerdo()">Delete</button>
            </div>
        </b-modal>
        <div v-show="acuerdos && acuerdos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./acuerdo.component.ts">
</script>
