<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="investigacionTipo">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.investigacionTipo.detail.title')"></span> {{investigacionTipo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.investigacionTipo')"></span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.investigacionTipo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.investigacionTipoDescripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.investigacionTipoDescripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.tipo')"></span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.tipo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.tipoDescripcion')"></span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.tipoDescripcion}}</span>
                    </dd>
                    
                    
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="investigacionTipo.id" :to="{name: 'InvestigacionTipoEdit', params: {investigacionTipoId: investigacionTipo.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./investigacion-tipo-details.component.ts">
</script>
