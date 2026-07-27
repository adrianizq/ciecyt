<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="categorizacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.categorizacion.detail.title')">Categorizacion</span> {{categorizacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.categorizacion.categoria')">Categoria</span>
                    </dt>
                    <dd>
                        <span>{{categorizacion.categoria}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.categorizacion.descripcion')">Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{categorizacion.descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.categorizacion.categorizacionProyecto')">Categorizacion Proyecto</span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="categorizacion.id" :to="{name: 'CategorizacionEdit', params: {categorizacionId: categorizacion.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./categorizacion-details.component.ts">
</script>
