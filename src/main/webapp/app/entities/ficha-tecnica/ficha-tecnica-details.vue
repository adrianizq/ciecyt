<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="fichaTecnica">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.fichaTecnica.detail.title')">FichaTecnica</span> {{fichaTecnica.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.tituloProfesional')">Titulo Profesional</span>
                    </dt>
                    <dd>
                        <span>{{fichaTecnica.tituloProfesional}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.tituloPostgrado')">Titulo Postgrado</span>
                    </dt>
                    <dd>
                        <span>{{fichaTecnica.tituloPostgrado}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.experiencia')">Experiencia</span>
                    </dt>
                    <dd>
                        <span>{{fichaTecnica.experiencia}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.fichaTecnica.fichaTecnicaUser')">Ficha Tecnica User</span>
                    </dt>
                    <dd>
                        {{fichaTecnica.fichaTecnicaUserLogin}}
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="fichaTecnica.id" :to="{name: 'FichaTecnicaEdit', params: {fichaTecnicaId: fichaTecnica.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./ficha-tecnica-details.component.ts">
</script>
