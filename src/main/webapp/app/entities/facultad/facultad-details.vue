<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="facultad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.facultad.detail.title')"></span> {{facultad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.facultad.codigoFacultad')"></span>
                    </dt>
                    <dd>
                        <span>{{facultad.codigoFacultad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.facultad.facultad')"></span>
                    </dt>
                    <dd>
                        <span>{{facultad.facultad}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="facultad.id" :to="{name: 'FacultadEdit', params: {facultadId: facultad.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./facultad-details.component.ts">
</script>
