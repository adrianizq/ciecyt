<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="proyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.proyecto.detail.title')"></span> {{proyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.titulo')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.url')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.lugarEjecucion')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.lugarEjecucion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.duracion')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.duracion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.fechaIni')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.fechaIni}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.fechaFin')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.contrapartidaPesos')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.contrapartidaPesos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.contrapartidaEspecie')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.contrapartidaEspecie}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.palabrasClave')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.palabrasClave}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.convocatoria')"></span>
                    </dt>
                    <dd>
                        <span>{{proyecto.convocatoria}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')"></span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.proyectoLineaInvestigacionId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {proyectoLineaInvestigacionId: proyecto.proyectoLineaInvestigacionId}}">{{proyecto.proyectoLineaInvestigacionLinea}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.proyectoGrupoSemillero')"></span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.proyectoGrupoSemilleroId">
                            <router-link :to="{name: 'GrupoSemilleroView', params: {proyectoGrupoSemilleroId: proyecto.proyectoGrupoSemilleroId}}">{{proyecto.proyectoGrupoSemilleroNombre}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.proyectoModalidad')"></span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.proyectoModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {proyectoModalidadId: proyecto.proyectoModalidadId}}">{{proyecto.proyectoModalidadModalidad}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.facultad')"></span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.facultadId">
                            <router-link :to="{name: 'FacultadView', params: {facultadId: proyecto.facultadId}}">{{proyecto.facultadId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')"></span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.subLineaLineaInvestigacionId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {subLineaLineaInvestigacionId: proyecto.subLineaLineaInvestigacionId}}">{{proyecto.subLineaLineaInvestigacionLinea}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"></span>
                </button>
                <router-link custom v-slot="{ navigate }" v-if="proyecto.id" :to="{name: 'ProyectoEdit', params: {proyectoId: proyecto.id}}"><button class="btn btn-primary" @click="navigate">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"></span>
                </button></router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-details.component.ts">
</script>
