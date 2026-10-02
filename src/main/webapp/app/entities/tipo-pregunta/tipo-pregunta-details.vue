<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="tipoPregunta">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.tipoPregunta.detail.title')"></span> {{tipoPregunta.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.tipoPregunta.tipoPregunta')"></span>
                    </dt>
                    <dd>
                        <span>{{tipoPregunta.tipoPregunta}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.tipoPregunta.tipoDato')"></span>
                    </dt>
                    <dd>
                        <span>{{tipoPregunta.tipoDato}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="tipoPregunta.id" :to="{name: 'TipoPreguntaEdit', params: {tipoPreguntaId: tipoPregunta.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./tipo-pregunta-details.component.ts">
</script>
