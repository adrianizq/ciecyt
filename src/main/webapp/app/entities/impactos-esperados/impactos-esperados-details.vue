<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="impactosEsperados">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.impactosEsperados.detail.title')">ImpactosEsperados</span> {{impactosEsperados.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.impacto')">Impacto</span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.impacto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.plazo')">Plazo</span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.plazo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.indicador')">Indicador</span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.indicador}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.supuestos')">Supuestos</span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.supuestos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.ordenVista')">Orden Vista</span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.ordenVista}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.impactosEsperadoProyecto')">Impactos Esperado Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="impactosEsperados.impactosEsperadoProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {impactosEsperadoProyectoId: impactosEsperados.impactosEsperadoProyectoId}}">{{impactosEsperados.impactosEsperadoProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="impactosEsperados.id" :to="{name: 'ImpactosEsperadosEdit', params: {impactosEsperadosId: impactosEsperados.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./impactos-esperados-details.component.ts">
</script>
