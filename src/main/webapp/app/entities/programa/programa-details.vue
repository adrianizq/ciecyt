<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="programa">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.programa.detail.title')"></span> {{programa.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.programa.programa')"></span>
                    </dt>
                    <dd>
                        <span>{{programa.programa}}</span>
                    </dd>
<dt>
                        <span v-text="$t('ciecytApp.programa.descripcion')"></span>
                    </dt>
                     <dd>
                        <span>{{descripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.codigoInterno')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.codigoInterno}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.codigoSnies')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.codigoSnies}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.creditos')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.creditos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.ciclo')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.ciclo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.resolucion')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.resolucion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.titulo')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.programa')"></span>
                    </dt>
                     <dd>
                        <span>{{programa.duracionSemestres}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.programa.programaFacultad')"></span>
                    </dt>
                    <dd>
                        <span>{{programa.programaFacultad}}</span>
                    </dd>
                    
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="programa.id" :to="{name: 'ProgramaEdit', params: {programaId: programa.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./programa-details.component.ts">
</script>
