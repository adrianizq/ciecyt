<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.formato.home.title')" id="formato-heading">Formatos</span>
            <router-link :to="{name: 'FormatoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-formato">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.formato.home.createLabel')">
                    Create a new Formato
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
        <div class="alert alert-warning" v-if="!isFetching && formatoes && formatoes.length === 0">
            <span v-text="$t('ciecytApp.formato.home.notFound')">No formatoes found</span>
        </div>
        <div class="table-responsive" v-if="formatoes && formatoes.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('formato')"><span v-text="$t('ciecytApp.formato.formato')">Formato</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('version')"><span v-text="$t('ciecytApp.formato.version')">Version</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('codigo')"><span v-text="$t('ciecytApp.formato.codigo')">Codigo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('fecha')"><span v-text="$t('ciecytApp.formato.fecha')">Fecha</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="formato in formatoes"
                    :key="formato.id">
                    <td>
                        <router-link :to="{name: 'FormatoView', params: {formatoId: formato.id}}">{{formato.id}}</router-link>
                    </td>
                    <td>{{formato.formato}}</td>
                    <td>{{formato.version}}</td>
                    <td>{{formato.codigo}}</td>
                    <td>{{formato.fecha}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FormatoView', params: {formatoId: formato.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FormatoEdit', params: {formatoId: formato.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(formato)"
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
            <span slot="modal-title"><span id="ciecytApp.formato.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-formato-heading" v-bind:title="$t('ciecytApp.formato.delete.question')">Are you sure you want to delete this Formato?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-formato" v-text="$t('entity.action.delete')" v-on:click="removeFormato()">Delete</button>
            </div>
        </b-modal>
        <div v-show="formatoes && formatoes.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./formato.component.ts">
</script>
