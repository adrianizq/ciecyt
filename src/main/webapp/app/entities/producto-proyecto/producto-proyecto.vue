<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.productoProyecto.home.title')" id="producto-proyecto-heading"></span>
            <router-link custom v-slot="{ navigate }" :to="{name: 'ProductoProyectoCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-producto-proyecto" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.productoProyecto.home.createLabel')"></span>
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
        <jhi-empty v-if="!isFetching && productoProyectos && productoProyectos.length === 0" :titulo="$t('ciecytApp.productoProyecto.home.notFound')" mensaje=""></jhi-empty>
        <div class="table-responsive" v-if="productoProyectos && productoProyectos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('aplica')"><span v-text="$t('ciecytApp.productoProyecto.aplica')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('descripcion')"><span v-text="$t('ciecytApp.productoProyecto.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('productoProyectoProductoProducto')"><span v-text="$t('ciecytApp.productoProyecto.productoProyectoProducto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('productoProyectoProyectoTitulo')"><span v-text="$t('ciecytApp.productoProyecto.productoProyectoProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="productoProyecto in productoProyectos"
                    :key="productoProyecto.id">
                    <td>
                        <router-link :to="{name: 'ProductoProyectoView', params: {productoProyectoId: productoProyecto.id}}">{{productoProyecto.id}}</router-link>
                    </td>
                    <td>{{productoProyecto.aplica}}</td>
                    <td>{{productoProyecto.descripcion}}</td>
                    <td>
                        <div v-if="productoProyecto.productoProyectoProductoId">
                            <router-link :to="{name: 'ProductoView', params: {productoProyectoProductoId: productoProyecto.productoProyectoProductoId}}">{{productoProyecto.productoProyectoProductoProducto}}</router-link>
                        </div>
                    </td>
                    <td>
                        <div v-if="productoProyecto.productoProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {productoProyectoProyectoId: productoProyecto.productoProyectoProyectoId}}">{{productoProyecto.productoProyectoProyectoTitulo}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProductoProyectoView', params: {productoProyectoId: productoProyecto.id}}"><button class="btn btn-info btn-sm details" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProductoProyectoEdit', params: {productoProyectoId: productoProyecto.id}}"><button class="btn btn-primary btn-sm edit" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button v-on:click="prepareRemove(productoProyecto)"
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
<span ><span id="ciecytApp.productoProyecto.delete.question" v-text="$t('entity.delete.title')"></span></span>
</template>
            <div class="modal-body">
                <p id="jhi-delete-productoProyecto-heading" v-bind:title="$t('ciecytApp.productoProyecto.delete.question')">Are you sure you want to delete this Producto Proyecto?</p>
            </div>
            <template #modal-footer>
<div >
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()"></button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-productoProyecto" v-text="$t('entity.action.delete')" v-on:click="removeProductoProyecto()"></button>
            </div>
</template>
        </b-modal>
        <div v-show="productoProyectos && productoProyectos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto-proyecto.component.ts">
</script>
