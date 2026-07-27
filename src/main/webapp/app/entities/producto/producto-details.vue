<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="producto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.producto.detail.title')">Producto</span> {{producto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.producto.producto')">Producto</span>
                    </dt>
                    <dd>
                        <span>{{producto.producto}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="producto.id" :to="{name: 'ProductoEdit', params: {productoId: producto.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto-details.component.ts">
</script>
