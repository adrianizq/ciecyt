<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="fichaTecnica">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.fichaTecnica.detail.title')"></span> {{fichaTecnica.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.tituloProfesional')"></span>
                    </dt>
                    <dd>
                        <span>{{fichaTecnica.tituloProfesional}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.tituloPostgrado')"></span>
                    </dt>
                    <dd>
                        <span>{{fichaTecnica.tituloPostgrado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.experiencia')"></span>
                    </dt>
                    <dd>
                        <span>{{fichaTecnica.experiencia}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.fichaTecnicaUser')"></span>
                    </dt>
                    <dd>
                        {{fichaTecnica.fichaTecnicaUserLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="fichaTecnica.id" :to="{name: 'FichaTecnicaEdit', params: {fichaTecnicaId: fichaTecnica.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ficha-tecnica-details.component.ts">
</script>
