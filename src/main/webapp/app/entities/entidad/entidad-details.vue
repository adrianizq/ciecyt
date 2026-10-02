<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="entidad">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.entidad.detail.title')"></span> {{entidad.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.entidad.entidad')"></span>
                    </dt>
                    <dd>
                        <span>{{entidad.entidad}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.entidad.nit')"></span>
                    </dt>
                    <dd>
                        <span>{{entidad.nit}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="entidad.id" :to="{name: 'EntidadEdit', params: {entidadId: entidad.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./entidad-details.component.ts">
</script>
