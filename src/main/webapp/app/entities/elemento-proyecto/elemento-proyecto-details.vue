<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="elementoProyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.elementoProyecto.detail.title')">ElementoProyecto</span> {{elementoProyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.dato')">Dato</span>
                    </dt>
                    <dd>
                        <span>{{elementoProyecto.dato}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyectoDescripcion')">Elemento Proyecto Proyecto Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{elementoProyecto.elementoProyectoProyectoDescripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoElemento')">Elemento Proyecto Elemento</span>
                    </dt>
                    <dd>
                        <div v-if="elementoProyecto.elementoProyectoElementoId">
                            <router-link :to="{name: 'ElementoView', params: {elementoProyectoElementoId: elementoProyecto.elementoProyectoElementoId}}">{{elementoProyecto.elementoProyectoElementoElemento}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.elementoProyecto.elementoProyectoProyecto')">Elemento Proyecto Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="elementoProyecto.elementoProyectoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {elementoProyectoProyectoId: elementoProyecto.elementoProyectoProyectoId}}">{{elementoProyecto.elementoProyectoProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="elementoProyecto.id" :to="{name: 'ElementoProyectoEdit', params: {elementoProyectoId: elementoProyecto.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./elemento-proyecto-details.component.ts">
</script>
