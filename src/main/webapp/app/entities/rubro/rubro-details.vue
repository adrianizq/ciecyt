<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="rubro">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.rubro.detail.title')">Rubro</span> {{rubro.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.rubro.rubro')">Rubro</span>
                    </dt>
                    <dd>
                        <span>{{rubro.rubro}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="rubro.id" :to="{name: 'RubroEdit', params: {rubroId: rubro.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./rubro-details.component.ts">
</script>
