<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="grupoSemillero">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.grupoSemillero.detail.title')"></span> {{grupoSemillero.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.grupoSemillero.nombre')"></span>
                    </dt>
                    <dd>
                        <span>{{grupoSemillero.nombre}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.grupoSemillero.tipo')"></span>
                    </dt>
                    <dd>
                        <span>{{grupoSemillero.tipo}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="grupoSemillero.id" :to="{name: 'GrupoSemilleroEdit', params: {grupoSemilleroId: grupoSemillero.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./grupo-semillero-details.component.ts">
</script>
