<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="acuerdo">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.acuerdo.detail.title')"></span> {{acuerdo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.acuerdo')"></span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.acuerdo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.version')"></span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.version}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.codigo')"></span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.codigo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.fecha')"></span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.fecha}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="acuerdo.id" :to="{name: 'AcuerdoEdit', params: {acuerdoId: acuerdo.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./acuerdo-details.component.ts">
</script>
