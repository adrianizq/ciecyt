<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.proyecto.home.createOrEditLabel" v-text="$t('ciecytApp.proyecto.home.createOrEditLabel')">Create or edit a Proyecto</h2>
                <div>
                    <div class="form-group" v-if="proyecto.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="proyecto.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.titulo')" for="proyecto-titulo">Titulo</label>
                        <input type="text" class="form-control" name="titulo" id="proyecto-titulo"
                            :class="{'valid': !$v.proyecto.titulo.$invalid, 'invalid': $v.proyecto.titulo.$invalid }" v-model="$v.proyecto.titulo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.url')" for="proyecto-url">Url</label>
                        <input type="text" class="form-control" name="url" id="proyecto-url"
                            :class="{'valid': !$v.proyecto.url.$invalid, 'invalid': $v.proyecto.url.$invalid }" v-model="$v.proyecto.url.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.lugarEjecucion')" for="proyecto-lugarEjecucion">Lugar Ejecucion</label>
                        <input type="text" class="form-control" name="lugarEjecucion" id="proyecto-lugarEjecucion"
                            :class="{'valid': !$v.proyecto.lugarEjecucion.$invalid, 'invalid': $v.proyecto.lugarEjecucion.$invalid }" v-model="$v.proyecto.lugarEjecucion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.duracion')" for="proyecto-duracion">Duracion</label>
                        <input type="text" class="form-control" name="duracion" id="proyecto-duracion"
                            :class="{'valid': !$v.proyecto.duracion.$invalid, 'invalid': $v.proyecto.duracion.$invalid }" v-model="$v.proyecto.duracion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.fechaIni')" for="proyecto-fechaIni">Fecha Ini</label>
                        <div class="input-group">
                            <input id="proyecto-fechaIni" type="date" class="form-control" name="fechaIni"  :class="{'valid': !$v.proyecto.fechaIni.$invalid, 'invalid': $v.proyecto.fechaIni.$invalid }"
                            v-model="$v.proyecto.fechaIni.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.fechaFin')" for="proyecto-fechaFin">Fecha Fin</label>
                        <div class="input-group">
                            <input id="proyecto-fechaFin" type="date" class="form-control" name="fechaFin"  :class="{'valid': !$v.proyecto.fechaFin.$invalid, 'invalid': $v.proyecto.fechaFin.$invalid }"
                            v-model="$v.proyecto.fechaFin.$model"  />
                        </div>
                    </div>
                   <!-- <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.contrapartidaPesos')" for="proyecto-contrapartidaPesos">Contrapartida Pesos</label>
                        <input type="number" class="form-control" name="contrapartidaPesos" id="proyecto-contrapartidaPesos"
                            :class="{'valid': !$v.proyecto.contrapartidaPesos.$invalid, 'invalid': $v.proyecto.contrapartidaPesos.$invalid }" v-model.number="$v.proyecto.contrapartidaPesos.$model" />
                    </div>
                    -->
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.contrapartidaEspecie')" for="proyecto-contrapartidaEspecie">Contrapartida Especie</label>
                        <input type="number" class="form-control" name="contrapartidaEspecie" id="proyecto-contrapartidaEspecie"
                            :class="{'valid': !$v.proyecto.contrapartidaEspecie.$invalid, 'invalid': $v.proyecto.contrapartidaEspecie.$invalid }" v-model.number="$v.proyecto.contrapartidaEspecie.$model" />
                    </div>-->
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.palabrasClave')" for="proyecto-palabrasClave">Palabras Clave</label>
                        <input type="text" class="form-control" name="palabrasClave" id="proyecto-palabrasClave"
                            :class="{'valid': !$v.proyecto.palabrasClave.$invalid, 'invalid': $v.proyecto.palabrasClave.$invalid }" v-model="$v.proyecto.palabrasClave.$model" />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.proyecto.convocatoria')" for="proyecto-convocatoria">Convocatoria</label>
                        <input type="text" class="form-control" name="convocatoria" id="proyecto-convocatoria"
                            :class="{'valid': !$v.proyecto.convocatoria.$invalid, 'invalid': $v.proyecto.convocatoria.$invalid }" v-model="$v.proyecto.convocatoria.$model" />
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')" for="proyecto-proyectoLineaInvestigacion">Proyecto Linea Investigacion</label>
                        <select class="form-control" id="proyecto-proyectoLineaInvestigacion" name="proyectoLineaInvestigacion" v-model="proyecto.proyectoLineaInvestigacionId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="lineaInvestigacionOption.id" v-for="lineaInvestigacionOption in lineaInvestigacions" :key="lineaInvestigacionOption.id">{{lineaInvestigacionOption.linea}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyecto.proyectoGrupoSemillero')" for="proyecto-proyectoGrupoSemillero">Proyecto Grupo Semillero</label>
                        <select class="form-control" id="proyecto-proyectoGrupoSemillero" name="proyectoGrupoSemillero" v-model="proyecto.proyectoGrupoSemilleroId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="grupoSemilleroOption.id" v-for="grupoSemilleroOption in grupoSemilleros" :key="grupoSemilleroOption.id">{{grupoSemilleroOption.nombre}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyecto.proyectoModalidad')" for="proyecto-proyectoModalidad">Proyecto Modalidad</label>
                        <select class="form-control" id="proyecto-proyectoModalidad" name="proyectoModalidad" v-model="proyecto.proyectoModalidadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="modalidadOption.id" v-for="modalidadOption in modalidads" :key="modalidadOption.id">{{modalidadOption.modalidad}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyecto.facultad')" for="proyecto-facultad">Facultad</label>
                        <select class="form-control" id="proyecto-facultad" name="facultad" v-model="proyecto.facultadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="facultadOption.id" v-for="facultadOption in facultads" :key="facultadOption.id">{{facultadOption.id}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')" for="proyecto-subLineaLineaInvestigacion">Sub Linea Linea Investigacion</label>
                        <select class="form-control" id="proyecto-subLineaLineaInvestigacion" name="subLineaLineaInvestigacion" v-model="proyecto.subLineaLineaInvestigacionId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="lineaInvestigacionOption.id" v-for="lineaInvestigacionOption in lineaInvestigacions" :key="lineaInvestigacionOption.id">{{lineaInvestigacionOption.linea}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.proyecto.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./proyecto-update.component.ts">
</script>
