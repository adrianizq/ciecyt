<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="proyectoFase">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.proyectoFase.detail.title')">ProyectoFase</span> {{proyectoFase.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoFase.titulo')">Titulo</span>
                    </dt>
                    <dd>
                        <span>{{proyectoFase.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoFase.cumplida')">Cumplida</span>
                    </dt>
                    <dd>
                        <span>{{proyectoFase.cumplida}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoFase.fechaCumplimiento')">Fecha Cumplimiento</span>
                    </dt>
                    <dd>
                        <span>{{proyectoFase.fechaCumplimiento}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoFase.observaciones')">Observaciones</span>
                    </dt>
                    <dd>
                        <span>{{proyectoFase.observaciones}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoFase.proyectoFaseFases')">Proyecto Fase Fases</span>
                    </dt>
                    <dd>
                        <div v-if="proyectoFase.proyectoFaseFasesId">
                            <router-link :to="{name: 'FasesView', params: {proyectoFaseFasesId: proyectoFase.proyectoFaseFasesId}}">{{proyectoFase.proyectoFaseFasesFase}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyectoFase.proyectoFaseProyecto')">Proyecto Fase Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="proyectoFase.proyectoFaseProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {proyectoFaseProyectoId: proyectoFase.proyectoFaseProyectoId}}">{{proyectoFase.proyectoFaseProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="proyectoFase.id" :to="{name: 'ProyectoFaseEdit', params: {proyectoFaseId: proyectoFase.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-fase-details.component.ts">
</script>
