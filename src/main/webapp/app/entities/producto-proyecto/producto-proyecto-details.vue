<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="productoProyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.productoProyecto.detail.title')"></span> {{productoProyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.aplica')"></span>
                    </dt>
                    <dd>
                        <span>{{productoProyecto.aplica}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.descripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{productoProyecto.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.productoProyectoProducto')"></span>
                    </dt>
                    <dd>
                        <div v-if="productoProyecto.productoProyectoProductoId">
                            <router-link :to="{name: 'ProductoView', params: {productoProyectoProductoId: productoProyecto.productoProyectoProductoId}}">{{productoProyecto.productoProyectoProductoProducto}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.productoProyecto.productoProyectoProyecto')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="productoProyecto.id" :to="{name: 'ProductoProyectoEdit', params: {productoProyectoId: productoProyecto.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto-proyecto-details.component.ts">
</script>
