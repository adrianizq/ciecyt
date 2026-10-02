<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cronograma">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.cronograma.detail.title')"></span> {{cronograma.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.cronograma.actividad')"></span>
                    </dt>
                    <dd>
                        <span>{{cronograma.actividad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronograma.duracion')"></span>
                    </dt>
                    <dd>
                        <span>{{cronograma.duracion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronograma.fechaInicio')"></span>
                    </dt>
                    <dd>
                        <span>{{cronograma.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronograma.fechaFin')"></span>
                    </dt>
                    <dd>
                        <span>{{cronograma.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronograma.ordenVista')"></span>
                    </dt>
                    <dd>
                        <span>{{cronograma.ordenVista}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronograma.cronogramaProyecto')"></span>
                    </dt>
                    <dd>
                        <div v-if="cronograma.cronogramaProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {cronogramaProyectoId: cronograma.cronogramaProyectoId}}">{{cronograma.cronogramaProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="cronograma.id" :to="{name: 'CronogramaEdit', params: {cronogramaId: cronograma.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma-details.component.ts">
</script>
