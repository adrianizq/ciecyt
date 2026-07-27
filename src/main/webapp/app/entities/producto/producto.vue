<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('ciecytApp.producto.home.title')" id="producto-heading">Productos</span>
            <router-link :to="{name: 'ProductoCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-producto">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.producto.home.createLabel')">
                    Create a new Producto
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
        <div class="alert alert-warning" v-if="!isFetching && productos && productos.length === 0">
            <span v-text="$t('ciecytApp.producto.home.notFound')">No productos found</span>
        </div>
        <div class="table-responsive" v-if="productos && productos.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('producto')"><span v-text="$t('ciecytApp.producto.producto')">Producto</span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="producto in productos"
                    :key="producto.id">
                    <td>
                        <router-link :to="{name: 'ProductoView', params: {productoId: producto.id}}">{{producto.id}}</router-link>
                    </td>
                    <td>{{producto.producto}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProductoView', params: {productoId: producto.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProductoEdit', params: {productoId: producto.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(producto)"
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
            <span slot="modal-title"><span id="ciecytApp.producto.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-producto-heading" v-bind:title="$t('ciecytApp.producto.delete.question')">Are you sure you want to delete this Producto?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-producto" v-text="$t('entity.action.delete')" v-on:click="removeProducto()">Delete</button>
            </div>
        </b-modal>
        <div v-show="productos && productos.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto.component.ts">
</script>
