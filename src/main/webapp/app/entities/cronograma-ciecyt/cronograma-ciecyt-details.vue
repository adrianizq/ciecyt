<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cronogramaCiecyt">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.cronogramaCiecyt.detail.title')">CronogramaCiecyt</span> {{cronogramaCiecyt.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.tituloCronograma')">Titulo Cronograma</span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.tituloCronograma}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.fechaInicio')">Fecha Inicio</span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.fechaFin')">Fecha Fin</span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.observaciones')">Observaciones</span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.observaciones}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.cronogramaCiecytModalidad')">Cronograma Ciecyt Modalidad</span>
                    </dt>
                    <dd>
                        <div v-if="cronogramaCiecyt.cronogramaCiecytModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {cronogramaCiecytModalidadId: cronogramaCiecyt.cronogramaCiecytModalidadId}}">{{cronogramaCiecyt.cronogramaCiecytModalidadModalidad}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="cronogramaCiecyt.id" :to="{name: 'CronogramaCiecytEdit', params: {cronogramaCiecytId: cronogramaCiecyt.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma-ciecyt-details.component.ts">
</script>
