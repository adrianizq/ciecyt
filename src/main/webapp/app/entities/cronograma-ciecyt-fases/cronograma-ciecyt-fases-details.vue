<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cronogramaCiecytFases">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.cronogramaCiecytFases.detail.title')"></span> {{cronogramaCiecytFases.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecytFases.inicioFase')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecytFases.inicioFase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecytFases.finFase')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecytFases.finFase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecytFases.textoExplicativo')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecytFases.textoExplicativo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecyt')"></span>
                    </dt>
                    <dd>
                        <div v-if="cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytId">
                            <router-link :to="{name: 'CronogramaCiecytView', params: {cronogramaCiecytFasesCronogramaCiecytId: cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytId}}">{{cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytTituloCronograma}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesFases')"></span>
                    </dt>
                    <dd>
                        <div v-if="cronogramaCiecytFases.cronogramaCiecytFasesFasesId">
                            <router-link :to="{name: 'FasesView', params: {cronogramaCiecytFasesFasesId: cronogramaCiecytFases.cronogramaCiecytFasesFasesId}}">{{cronogramaCiecytFases.cronogramaCiecytFasesFasesFase}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="cronogramaCiecytFases.id" :to="{name: 'CronogramaCiecytFasesEdit', params: {cronogramaCiecytFasesId: cronogramaCiecytFases.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma-ciecyt-fases-details.component.ts">
</script>
