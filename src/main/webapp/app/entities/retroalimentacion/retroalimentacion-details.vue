<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="retroalimentacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.retroalimentacion.detail.title')"></span> {{retroalimentacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.titulo')"></span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.retroalimentacion')"></span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.retroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.fechaRetroalimentacion')"></span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.fechaRetroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.estadoRetroalimentacion')"></span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.estadoRetroalimentacion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.estadoProyectoFase')"></span>
                    </dt>
                    <dd>
                        <span>{{retroalimentacion.estadoProyectoFase}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionProyectoFase')"></span>
                    </dt>
                    <dd>
                        <div v-if="retroalimentacion.retroalimentacionProyectoFaseId">
                            <router-link :to="{name: 'ProyectoFaseView', params: {retroalimentacionProyectoFaseId: retroalimentacion.retroalimentacionProyectoFaseId}}">{{retroalimentacion.retroalimentacionProyectoFaseTitulo}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.retroalimentacion.retroalimentacionUser')"></span>
                    </dt>
                    <dd>
                        {{retroalimentacion.retroalimentacionUserLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="retroalimentacion.id" :to="{name: 'RetroalimentacionEdit', params: {retroalimentacionId: retroalimentacion.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./retroalimentacion-details.component.ts">
</script>
