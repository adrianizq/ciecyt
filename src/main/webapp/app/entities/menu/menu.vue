<template>
    <div>
        <h2 id="page-heading">
            <span  id="menu-heading">Aplicaciones</span>
            <router-link :to="{name: 'MenuCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-menu">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.menu.home.createLabel')">
                    Create a new Menu
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
        <div class="alert alert-warning" v-if="!isFetching && menus && menus.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')">No menus found</span>
        </div>
        <div class="table-responsive" v-if="menus && menus.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nombre')"><span v-text="$t('ciecytApp.menu.nombre')">Nombre</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('url')"><span v-text="$t('ciecytApp.menu.url')">Url</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('icono')"><span v-text="$t('ciecytApp.menu.icono')">Icono</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('activo')"><span v-text="$t('ciecytApp.menu.activo')">Activo</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('es_publico')"><span v-text="$t('ciecytApp.menu.es_publico')">Alcance</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('orden')"><span v-text="$t('ciecytApp.menu.orden')">Orden</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('menuPadreNombre')"><span v-text="$t('ciecytApp.menu.menuPadre')">Menu Padre</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="menu in menus"
                    :key="menu.id">
                    <td>
                        <router-link :to="{name: 'MenuView', params: {menuId: menu.id}}">{{menu.id}}</router-link>
                    </td>
                    <td>{{menu.nombre}}</td>
                    <td>{{menu.url}}</td>
                    <td>
                        <font-awesome-icon :icon="menu.icono || 'asterisk'" />
                    </td>
                    <td>
                        <button class="btn btn-danger btn-sm deactivated"
                                v-on:click="setActive(menu, true)" v-if="!menu.activo">
                                Inactivo
                        </button>
                        <button class="btn btn-success btn-sm" v-on:click="setActive(menu, false)" v-if="menu.activo">
                                Activo
                        </button>

                    </td>
                    <td>
                        <button class="btn btn-primary btn-sm deactivated"
                                v-on:click="setAlcance(menu, true)" v-if="!menu.esPublico">
                            Privado
                        </button>
                        <button class="btn btn-warning btn-sm" v-on:click="setAlcance(menu, false)" v-if="menu.esPublico">
                            Público
                        </button>
                    </td>
                    <td>{{ menu.orden }}</td>
                    <td>
                        <div v-if="menu.menuPadreId">
                            <router-link :to="{name: 'MenuView', params: {menuId: menu.menuPadreId}}">{{menu.menuPadreNombre}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'MenuView', params: {menuId: menu.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'MenuEdit', params: {menuId: menu.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(menu)"
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
            <span slot="modal-title"><span id="ciecytApp.menu.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-menu-heading" v-text="$t('ciecytApp.menu.delete.question', {name: removeName })">Are you sure you want to delete this Menu?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-menu" v-text="$t('entity.action.delete')" v-on:click="removeMenu()">Delete</button>
            </div>
        </b-modal>
        <div v-show="menus && menus.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./menu.component.ts">
</script>
