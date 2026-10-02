<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="cicloPropedeutico">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.cicloPropedeutico.detail.title')"></span> {{cicloPropedeutico.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.cicloPropedeutico.ciclo')"></span>
                    </dt>
                    <dd>
                        <span>{{cicloPropedeutico.ciclo}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="cicloPropedeutico.id" :to="{name: 'CicloPropedeuticoEdit', params: {cicloPropedeuticoId: cicloPropedeutico.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ciclo-propedeutico-details.component.ts">
</script>
