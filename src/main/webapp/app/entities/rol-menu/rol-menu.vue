<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.rolMenu.home.title')" id="rol-menu-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'RolMenuCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-rol-menu" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.rolMenu.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && rolMenus && rolMenus.length === 0" :titulo="$t('ciecytApp.rolMenu.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="rolMenus && rolMenus.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('permitirAcceso')"><span v-text="$t('ciecytApp.rolMenu.permitirAcceso')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('permitirCrear')"><span v-text="$t('ciecytApp.rolMenu.permitirCrear')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('permitirEditar')"><span v-text="$t('ciecytApp.rolMenu.permitirEditar')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('permitirEliminar')"><span v-text="$t('ciecytApp.rolMenu.permitirEliminar')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('authName')"><span v-text="$t('ciecytApp.rolMenu.authName')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('rolMenuMenuNombre')"><span v-text="$t('ciecytApp.rolMenu.rolMenuMenu')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="rolMenu in rolMenus"
                    :key="rolMenu.id">
                    <td>
                        <router-link :to="{name: 'RolMenuView', params: {rolMenuId: rolMenu.id}}">{{rolMenu.id}}</router-link>
                    </td>
                    <td>{{rolMenu.permitirAcceso}}</td>
                    <td>{{rolMenu.permitirCrear}}</td>
                    <td>{{rolMenu.permitirEditar}}</td>
                    <td>{{rolMenu.permitirEliminar}}</td>
                    <td>{{rolMenu.authName}}</td>
                    <td>
                        <div v-if="rolMenu.rolMenuMenuId">
                            <router-link :to="{name: 'MenuView', params: {rolMenuMenuId: rolMenu.rolMenuMenuId}}">{{rolMenu.rolMenuMenuNombre}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'RolMenuView', params: {rolMenuId: rolMenu.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'RolMenuEdit', params: {rolMenuId: rolMenu.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(rolMenu)"
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
<span ><span id="ciecytApp.rolMenu.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-rolMenu-heading" v-bind:title="$t('ciecytApp.rolMenu.delete.question')">Are you sure you want to delete this Rol Menu?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-rolMenu" v-text="$t('entity.action.delete')" v-on:click="removeRolMenu()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="rolMenus && rolMenus.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rol-menu.component.ts">
</script>
