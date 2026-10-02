<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="lineaInvestigacion">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.lineaInvestigacion.detail.title')"></span> {{lineaInvestigacion.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.lineaInvestigacion.linea')"></span>
                    </dt>
                    <dd>
                        <span>{{lineaInvestigacion.linea}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.lineaInvestigacion.codigoLinea')"></span>
                    </dt>
                    <dd>
                        <span>{{lineaInvestigacion.codigoLinea}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.lineaInvestigacion.lineaPadre')"></span>
                    </dt>
                    <dd>
                        <div v-if="lineaInvestigacion.lineaPadreId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {lineaPadreId: lineaInvestigacion.lineaPadreId}}">{{lineaInvestigacion.lineaPadreLinea}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.lineaInvestigacion.lineaInvestigacionPrograma')"></span>
                    </dt>
                    <dd>
                        <div v-if="lineaInvestigacion.lineaInvestigacionProgramaId">
                            <router-link :to="{name: 'ProgramaView', params: {lineaInvestigacionProgramaId: lineaInvestigacion.lineaInvestigacionProgramaId}}">{{lineaInvestigacion.lineaInvestigacionProgramaPrograma}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="lineaInvestigacion.id" :to="{name: 'LineaInvestigacionEdit', params: {lineaInvestigacionId: lineaInvestigacion.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./linea-investigacion-details.component.ts">
</script>
