<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="producto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.producto.detail.title')"></span> {{producto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.producto.producto')"></span>
                    </dt>
                    <dd>
                        <span>{{producto.producto}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="producto.id" :to="{name: 'ProductoEdit', params: {productoId: producto.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./producto-details.component.ts">
</script>
