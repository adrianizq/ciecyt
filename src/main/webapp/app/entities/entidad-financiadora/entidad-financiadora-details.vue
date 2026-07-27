<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="entidadFinanciadora">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.entidadFinanciadora.detail.title')">EntidadFinanciadora</span> {{entidadFinanciadora.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.entidadFinanciadora.valor')">Valor</span>
                    </dt>
                    <dd>
                        <span>{{entidadFinanciadora.valor}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.entidadFinanciadora.aprobada')">Aprobada</span>
                    </dt>
                    <dd>
                        <span>{{entidadFinanciadora.aprobada}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.entidadFinanciadora.entidadFinanciadoraEntidad')">Entidad Financiadora Entidad</span>
                    </dt>
                    <dd>
                        <div v-if="entidadFinanciadora.entidadFinanciadoraEntidadId">
                            <router-link :to="{name: 'EntidadView', params: {entidadFinanciadoraEntidadId: entidadFinanciadora.entidadFinanciadoraEntidadId}}">{{entidadFinanciadora.entidadFinanciadoraEntidadEntidad}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.entidadFinanciadora.entidadFinanciadoraProyecto')">Entidad Financiadora Proyecto</span>
                    </dt>
                    <dd>
                        <div v-if="entidadFinanciadora.entidadFinanciadoraProyectoId">
                            <router-link :to="{name: 'ProyectoView', params: {entidadFinanciadoraProyectoId: entidadFinanciadora.entidadFinanciadoraProyectoId}}">{{entidadFinanciadora.entidadFinanciadoraProyectoTitulo}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="entidadFinanciadora.id" :to="{name: 'EntidadFinanciadoraEdit', params: {entidadFinanciadoraId: entidadFinanciadora.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./entidad-financiadora-details.component.ts">
</script>
