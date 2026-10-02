<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="fases">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.fases.detail.title')"></span> {{fases.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.fases.fase')"></span>
                    </dt>
                    <dd>
                        <span>{{fases.fase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fases.notificable')"></span>
                    </dt>
                    <dd>
                        <span>{{fases.notificable}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fases.fasesModalidad')"></span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="fases.id" :to="{name: 'FasesEdit', params: {fasesId: fases.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./fases-details.component.ts">
</script>
