<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="impactosEsperados">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.impactosEsperados.detail.title')"></span> {{impactosEsperados.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.impacto')"></span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.impacto}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.plazo')"></span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.plazo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.indicador')"></span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.indicador}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.supuestos')"></span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.supuestos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.ordenVista')"></span>
                    </dt>
                    <dd>
                        <span>{{impactosEsperados.ordenVista}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.impactosEsperados.impactosEsperadoProyecto')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="impactosEsperados.id" :to="{name: 'ImpactosEsperadosEdit', params: {impactosEsperadosId: impactosEsperados.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./impactos-esperados-details.component.ts">
</script>
