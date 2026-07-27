<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="municipio">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.municipio.detail.title')">Municipio</span> {{municipio.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.region')">Región</span>
                    </dt>
                    <dd>
                        <span>{{municipio.region}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.codigo-dane-departamento')">Codigo Dane Departamento</span>
                    </dt>
                    <dd>
                        <span>{{municipio.codigoDaneDepartamento}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.departamento')">Departamento</span>
                    </dt>
                    <dd>
                        <span>{{municipio.departamento}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.codigo-dane-municipio')">Codigo Dane Municipio</span>
                    </dt>
                    <dd>
                        <span>{{municipio.codigoDaneMunicipio}}</span>
                    </dd>


                     
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.municipio')">Municipio</span>
                    </dt>
                    <dd>
                        <span>{{municipio.municipio}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="municipio.id" :to="{name: 'MunicipioEdit', params: {municipioId: municipio.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./municipio-details.component.ts">
</script>
