<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="resultadosEsperados">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.resultadosEsperados.detail.title')">ResultadosEsperados</span> {{resultadosEsperados.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.resultadosEsperados.resultado')">Resultado</span>
                    </dt>
                    <dd>
                        <span>{{resultadosEsperados.resultado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.resultadosEsperados.indicador')">Indicador</span>
                    </dt>
                    <dd>
                        <span>{{resultadosEsperados.indicador}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.resultadosEsperados.beneficiario')">Beneficiario</span>
                    </dt>
                    <dd>
                        <span>{{resultadosEsperados.beneficiario}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.resultadosEsperados.ordenVista')">Orden Vista</span>
                    </dt>
                    <dd>
                        <span>{{resultadosEsperados.ordenVista}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.resultadosEsperados.resultadosEsperadosProyecto')">Resultados Esperados Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="resultadosEsperados.resultadosEsperadosProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {resultadosEsperadosProyectoId: resultadosEsperados.resultadosEsperadosProyectoId}}">{{resultadosEsperados.resultadosEsperadosProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="resultadosEsperados.id" :to="{name: 'ResultadosEsperadosEdit', params: {resultadosEsperadosId: resultadosEsperados.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./resultados-esperados-details.component.ts">
</script>
