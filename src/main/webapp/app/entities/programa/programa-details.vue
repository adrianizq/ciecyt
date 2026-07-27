<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="programa">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.programa.detail.title')">Programa</span> {{programa.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.programa.programa')">Programa</span>
                    </dt>
                    <dd>
                        <span>{{programa.programa}}</span>
                    </dd>
<dt>
                        <span v-text="$t('ciecytApp.programa.descripcion')">descripcion</span>
                    </dt>
                     <dd>
                        <span>{{descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.codigoInterno')">codigoInterno</span>
                    </dt>
                     <dd>
                        <span>{{programa.codigoInterno}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.codigoSnies')">codigoSnies</span>
                    </dt>
                     <dd>
                        <span>{{programa.codigoSnies}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.creditos')">creditos</span>
                    </dt>
                     <dd>
                        <span>{{programa.creditos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.ciclo')">ciclo</span>
                    </dt>
                     <dd>
                        <span>{{programa.ciclo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.resolucion')">resolucion</span>
                    </dt>
                     <dd>
                        <span>{{programa.resolucion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.titulo')">titulo</span>
                    </dt>
                     <dd>
                        <span>{{programa.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.programa')">duracionSemestres</span>
                    </dt>
                     <dd>
                        <span>{{programa.duracionSemestres}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.programaFacultad')">programaFacultad</span>
                    </dt>
                    <dd>
                        <span>{{programa.programaFacultad}}</span>
                    </dd>
                    
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="programa.id" :to="{name: 'ProgramaEdit', params: {programaId: programa.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./programa-details.component.ts">
</script>
