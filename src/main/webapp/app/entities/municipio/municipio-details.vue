<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="municipio">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.municipio.detail.title')"></span> {{municipio.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.region')"></span>
                    </dt>
                    <dd>
                        <span>{{municipio.region}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.codigo-dane-departamento')"></span>
                    </dt>
                    <dd>
                        <span>{{municipio.codigoDaneDepartamento}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.departamento')"></span>
                    </dt>
                    <dd>
                        <span>{{municipio.departamento}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.codigo-dane-municipio')"></span>
                    </dt>
                    <dd>
                        <span>{{municipio.codigoDaneMunicipio}}</span>
                    </dd>


                     
                    <dt>
                        <span v-text="$t('ciecytApp.municipio.municipio')"></span>
                    </dt>
                    <dd>
                        <span>{{municipio.municipio}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="municipio.id" :to="{name: 'MunicipioEdit', params: {municipioId: municipio.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./municipio-details.component.ts">
</script>
