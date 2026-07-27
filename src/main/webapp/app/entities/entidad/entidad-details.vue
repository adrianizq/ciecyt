<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="entidad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.entidad.detail.title')">Entidad</span> {{entidad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.entidad.entidad')">Entidad</span>
                    </dt>
                    <dd>
                        <span>{{entidad.entidad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.entidad.nit')">Nit</span>
                    </dt>
                    <dd>
                        <span>{{entidad.nit}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="entidad.id" :to="{name: 'EntidadEdit', params: {entidadId: entidad.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./entidad-details.component.ts">
</script>
