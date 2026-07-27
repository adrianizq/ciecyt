<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="acuerdo">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.acuerdo.detail.title')">Acuerdo</span> {{acuerdo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.acuerdo')">Acuerdo</span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.acuerdo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.version')">Version</span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.version}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.codigo')">Codigo</span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.codigo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.acuerdo.fecha')">Fecha</span>
                    </dt>
                    <dd>
                        <span>{{acuerdo.fecha}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="acuerdo.id" :to="{name: 'AcuerdoEdit', params: {acuerdoId: acuerdo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./acuerdo-details.component.ts">
</script>
