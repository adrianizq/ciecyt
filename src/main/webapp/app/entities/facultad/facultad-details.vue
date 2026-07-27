<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="facultad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.facultad.detail.title')">Facultad</span> {{facultad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.facultad.codigoFacultad')">Codigo Facultad</span>
                    </dt>
                    <dd>
                        <span>{{facultad.codigoFacultad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.facultad.facultad')">Facultad</span>
                    </dt>
                    <dd>
                        <span>{{facultad.facultad}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="facultad.id" :to="{name: 'FacultadEdit', params: {facultadId: facultad.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./facultad-details.component.ts">
</script>
