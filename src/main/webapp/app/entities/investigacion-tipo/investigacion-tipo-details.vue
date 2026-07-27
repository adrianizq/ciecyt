<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="investigacionTipo">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.investigacionTipo.detail.title')">InvestigacionTipo</span> {{investigacionTipo.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.investigacionTipo')">InvestigacionTipo</span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.investigacionTipo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.investigacionTipoDescripcion')">Investigacion Tipo Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.investigacionTipoDescripcion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.tipo')"> Tipo </span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.tipo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.investigacionTipo.tipoDescripcion')"> Tipo Descripcion</span>
                    </dt>
                    <dd>
                        <span>{{investigacionTipo.tipoDescripcion}}</span>
                    </dd>
                    
                    
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="investigacionTipo.id" :to="{name: 'InvestigacionTipoEdit', params: {investigacionTipoId: investigacionTipo.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./investigacion-tipo-details.component.ts">
</script>
