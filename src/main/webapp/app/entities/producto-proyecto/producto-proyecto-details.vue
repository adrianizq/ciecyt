<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="productoProyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.productoProyecto.detail.title')">ProductoProyecto</span> {{productoProyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.aplica')">Aplica</span>
                    </dt>
                    <dd>
                        <span>{{productoProyecto.aplica}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.descripcion')">Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{productoProyecto.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.productoProyectoProducto')">Producto Proyecto Producto</span>
                    </dt>
                    <dd>
                        <div v-if="productoProyecto.productoProyectoProductoId">
                            <router-link :to="{name: 'ProductoView', params: {productoProyectoProductoId: productoProyecto.productoProyectoProductoId}}">{{productoProyecto.productoProyectoProductoProducto}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.productoProyectoProyecto')">Producto Proyecto Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="productoProyecto.productoProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {productoProyectoProyectoId: productoProyecto.productoProyectoProyectoId}}">{{productoProyecto.productoProyectoProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="productoProyecto.id" :to="{name: 'ProductoProyectoEdit', params: {productoProyectoId: productoProyecto.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto-proyecto-details.component.ts">
</script>
