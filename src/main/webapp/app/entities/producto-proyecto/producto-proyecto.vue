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
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'id' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('id')" v-on:keydown.enter="changeOrder('id')" v-on:keydown.space.prevent="changeOrder('id')"><span v-text="$t('global.field.id')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'aplica' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('aplica')" v-on:keydown.enter="changeOrder('aplica')" v-on:keydown.space.prevent="changeOrder('aplica')"><span v-text="$t('ciecytApp.productoProyecto.aplica')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'descripcion' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('descripcion')" v-on:keydown.enter="changeOrder('descripcion')" v-on:keydown.space.prevent="changeOrder('descripcion')"><span v-text="$t('ciecytApp.productoProyecto.descripcion')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'productoProyectoProductoProducto' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('productoProyectoProductoProducto')" v-on:keydown.enter="changeOrder('productoProyectoProductoProducto')" v-on:keydown.space.prevent="changeOrder('productoProyectoProductoProducto')"><span v-text="$t('ciecytApp.productoProyecto.productoProyectoProducto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th role="button" tabindex="0" :aria-sort="propOrder === 'productoProyectoProyectoTitulo' ? (reverse ? 'descending' : 'ascending') : 'none'" v-on:click="changeOrder('productoProyectoProyectoTitulo')" v-on:keydown.enter="changeOrder('productoProyectoProyectoTitulo')" v-on:keydown.space.prevent="changeOrder('productoProyectoProyectoTitulo')"><span v-text="$t('ciecytApp.productoProyecto.productoProyectoProyecto')"></span> <font-awesome-icon icon="sort"></font-awesome-icon></th>
                    <th scope="col" class="text-right"><span class="visually-hidden" v-text="$t('global.menu.actions')"></span></th>
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
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProductoProyectoView', params: {productoProyectoId: productoProyecto.id}}"><button class="btn btn-info btn-sm details" :aria-label="$t('entity.action.view')" @click="navigate">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')"></span>
                            </button></router-link>
                            <router-link custom v-slot="{ navigate }" :to="{name: 'ProductoProyectoEdit', params: {productoProyectoId: productoProyecto.id}}"><button class="btn btn-primary btn-sm edit" :aria-label="$t('entity.action.edit')" @click="navigate">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')"></span>
                            </button></router-link>
                            <b-button :aria-label="$t('entity.action.delete')" v-on:click="prepareRemove(productoProyecto)"
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
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" @update:model-value="loadPage"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto-proyecto.component.ts">
</script>
