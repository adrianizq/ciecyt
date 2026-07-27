<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="proyecto">
                <h2 class="jh-entity-heading"><span v-text="$t('ciecytApp.proyecto.detail.title')">Proyecto</span> {{proyecto.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.titulo')">Titulo</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.titulo}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.url')">Url</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.url}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.lugarEjecucion')">Lugar Ejecucion</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.lugarEjecucion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.duracion')">Duracion</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.duracion}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.fechaIni')">Fecha Ini</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.fechaIni}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.fechaFin')">Fecha Fin</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.fechaFin}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.contrapartidaPesos')">Contrapartida Pesos</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.contrapartidaPesos}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.contrapartidaEspecie')">Contrapartida Especie</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.contrapartidaEspecie}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.palabrasClave')">Palabras Clave</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.palabrasClave}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.convocatoria')">Convocatoria</span>
                    </dt>
                    <dd>
                        <span>{{proyecto.convocatoria}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')">Proyecto Linea Investigacion</span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.proyectoLineaInvestigacionId">
                            <router-link :to="{name: 'LineaInvestigacionView', params: {proyectoLineaInvestigacionId: proyecto.proyectoLineaInvestigacionId}}">{{proyecto.proyectoLineaInvestigacionLinea}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.proyectoGrupoSemillero')">Proyecto Grupo Semillero</span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.proyectoGrupoSemilleroId">
                            <router-link :to="{name: 'GrupoSemilleroView', params: {proyectoGrupoSemilleroId: proyecto.proyectoGrupoSemilleroId}}">{{proyecto.proyectoGrupoSemilleroNombre}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.proyectoModalidad')">Proyecto Modalidad</span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.proyectoModalidadId">
                            <router-link :to="{name: 'ModalidadView', params: {proyectoModalidadId: proyecto.proyectoModalidadId}}">{{proyecto.proyectoModalidadModalidad}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.facultad')">Facultad</span>
                    </dt>
                    <dd>
                        <div v-if="proyecto.facultadId">
                            <router-link :to="{name: 'FacultadView', params: {facultadId: proyecto.facultadId}}">{{proyecto.facultadId}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')">Sub Linea Linea Investigacion</span>
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
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="proyecto.id" :to="{name: 'ProyectoEdit', params: {proyectoId: proyecto.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./proyecto-details.component.ts">
</script>
