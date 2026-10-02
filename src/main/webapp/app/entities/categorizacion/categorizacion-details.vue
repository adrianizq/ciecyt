<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="categorizacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.categorizacion.detail.title')"></span> {{categorizacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.categorizacion.categoria')"></span>
                    </dt>
                    <dd>
                        <span>{{categorizacion.categoria}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.categorizacion.descripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{categorizacion.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.categorizacion.categorizacionProyecto')"></span>
                    </dt>
                    <dd>
                        <div v-if="categorizacion.categorizacionProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {categorizacionProyectoId: categorizacion.categorizacionProyectoId}}">{{categorizacion.categorizacionProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="categorizacion.id" :to="{name: 'CategorizacionEdit', params: {categorizacionId: categorizacion.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./categorizacion-details.component.ts">
</script>
