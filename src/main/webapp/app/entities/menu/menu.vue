<template>
    <div>
        <h2 id="page-heading">
            <span  id="menu-heading">Aplicaciones</span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'MenuCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-menu" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.menu.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && menus && menus.length === 0">
            <span v-text="$t('ciecytApp.general.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="menus && menus.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('nombre')"><span v-text="$t('ciecytApp.menu.nombre')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('url')"><span v-text="$t('ciecytApp.menu.url')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('icono')"><span v-text="$t('ciecytApp.menu.icono')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('activo')"><span v-text="$t('ciecytApp.menu.activo')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('es_publico')"><span v-text="$t('ciecytApp.menu.es_publico')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('orden')"><span v-text="$t('ciecytApp.menu.orden')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('menuPadreNombre')"><span v-text="$t('ciecytApp.menu.menuPadre')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'MenuView', params: {menuId: menu.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'MenuEdit', params: {menuId: menu.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(menu)"
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
<span ><span id="ciecytApp.menu.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-menu-heading" v-text="$t('ciecytApp.menu.delete.question', {name: removeName })"></p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-menu" v-text="$t('entity.action.delete')" v-on:click="removeMenu()"></button>
            </div>
</template>
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
