<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.usuario.home.title')" id="usuario-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'UsuarioCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-usuario" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.usuario.home.createLabel')"></span>
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
        <div class="alert alert-warning" v-if="!isFetching && usuarios && usuarios.length === 0">
            <span v-text="$t('ciecytApp.usuario.home.notFound')"></span>
        </div>
        <div class="table-responsive" v-if="usuarios && usuarios.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('usuario')"><span v-text="$t('ciecytApp.usuario.usuario')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.usuario.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="usuario in usuarios"
                    :key="usuario.id">
                    <td>
                        <router-link :to="{name: 'UsuarioView', params: {usuarioId: usuario.id}}">{{usuario.id}}</router-link>
                    </td>
                    <td>{{usuario.usuario}}</td>
                    <td>{{usuario.descripcion}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'UsuarioView', params: {usuarioId: usuario.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'UsuarioEdit', params: {usuarioId: usuario.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(usuario)"
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
<span ><span id="ciecytApp.usuario.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-usuario-heading" v-bind:title="$t('ciecytApp.usuario.delete.question')">Are you sure you want to delete this Usuario?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-usuario" v-text="$t('entity.action.delete')" v-on:click="removeUsuario()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="usuarios && usuarios.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./usuario.component.ts">
</script>
