<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cronogramaCiecyt">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.cronogramaCiecyt.detail.title')"></span> {{cronogramaCiecyt.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.tituloCronograma')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.tituloCronograma}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.fechaInicio')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.fechaInicio}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.fechaFin')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.observaciones')"></span>
                    </dt>
                    <dd>
                        <span>{{cronogramaCiecyt.observaciones}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.cronogramaCiecyt.cronogramaCiecytModalidad')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="cronogramaCiecyt.id" :to="{name: 'CronogramaCiecytEdit', params: {cronogramaCiecytId: cronogramaCiecyt.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./cronograma-ciecyt-details.component.ts">
</script>
