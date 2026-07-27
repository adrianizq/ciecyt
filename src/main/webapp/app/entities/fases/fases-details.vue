<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="fases">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.fases.detail.title')">Fases</span> {{fases.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.fases.fase')">Fase</span>
                    </dt>
                    <dd>
                        <span>{{fases.fase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fases.notificable')">Notificable</span>
                    </dt>
                    <dd>
                        <span>{{fases.notificable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fases.fasesModalidad')">Fases Modalidad</span>
                    </dt>
                    <dd>
                        <div v-if="fases.fasesModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {fasesModalidadId: fases.fasesModalidadId}}">{{fases.fasesModalidadModalidad}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="fases.id" :to="{name: 'FasesEdit', params: {fasesId: fases.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./fases-details.component.ts">
</script>
