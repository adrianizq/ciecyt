<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="retroalimentacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.retroalimentacion.detail.title')">Retroalimentacion</span> {{retroalimentacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.titulo')">Titulo</span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.retroalimentacion')">Retroalimentacion</span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.retroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.fechaRetroalimentacion')">Fecha Retroalimentacion</span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.fechaRetroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.estadoRetroalimentacion')">Estado Retroalimentacion</span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.estadoRetroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.estadoProyectoFase')">Estado Proyecto Fase</span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.estadoProyectoFase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionProyectoFase')">Retroalimentacion Proyecto Fase</span>
                    </dt>
                    <dd>
                        <div v-if="retroalimentacion.retroalimentacionProyectoFaseId">
                            <router-link :to="{name: 'ProyectoFaseView', params: {retroalimentacionProyectoFaseId: retroalimentacion.retroalimentacionProyectoFaseId}}">{{retroalimentacion.retroalimentacionProyectoFaseTitulo}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionUser')">Retroalimentacion User</span>
                    </dt>
                    <dd>
                        {{retroalimentacion.retroalimentacionUserLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="retroalimentacion.id" :to="{name: 'RetroalimentacionEdit', params: {retroalimentacionId: retroalimentacion.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./retroalimentacion-details.component.ts">
</script>
