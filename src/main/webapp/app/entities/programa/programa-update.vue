<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.programa.home.createOrEditLabel" v-text="$t('ciecytApp.programa.home.createOrEditLabel')">Create or edit a Programa</h2>
                <div>
                    <div class="form-group" v-if="programa.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="programa.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.programa')" for="programa-programa">Programa</label>
                        <input type="text" class="form-control" name="programa" id="programa-programa"
                            :class="{'valid': !$v.programa.programa.$invalid, 'invalid': $v.programa.programa.$invalid }" v-model="$v.programa.programa.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.descripcion')" for="programa-descripcion">descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="programa-descripcion"
                            :class="{'valid': !$v.programa.descripcion.$invalid, 'invalid': $v.programa.descripcion.$invalid }" v-model="$v.programa.descripcion.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.codigoInterno')" for="programa-codigoInterno">codigoInterno</label>
                        <input type="text" class="form-control" name="codigoInterno" id="programa-codigoInterno"
                            :class="{'valid': !$v.programa.codigoInterno.$invalid, 'invalid': $v.programa.codigoInterno.$invalid }" v-model="$v.programa.codigoInterno.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.codigoSnies')" for="programa-codigoSnies">codigoSnies</label>
                        <input type="text" class="form-control" name="codigoSnies" id="programa-codigoSnies"
                            :class="{'valid': !$v.programa.codigoSnies.$invalid, 'invalid': $v.programa.codigoSnies.$invalid }" v-model="$v.programa.codigoSnies.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.creditos')" for="programa-codigoSnies">creditos</label>
                        <input type="number" class="form-control" name="creditos" id="programa-creditos"
                            :class="{'valid': !$v.programa.creditos.$invalid, 'invalid': $v.programa.creditos.$invalid }" v-model="$v.programa.creditos.$model" />
                    </div>

                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.programa.ciclo')" for="programa-ciclo">Ciclo</label>
                        <select class="form-control" id="programa-ciclo" name="ciclo" v-model="$v.programa.ciclo.$model">
                            <option value="Tecnologico" programa.ciclo="Tecnologico">Tecnológico</option>
                            <option value="Profesional" programa.ciclo="Profesional">Profesional</option>
                             <!-- <option value="Profesional" programa.ciclo="Tecnico">Técnico</option> -->
                            <!--<option v-bind:value="facultadOption.id" v-for="facultadOption in facultads" :key="facultadOption.id">{{facultadOption.facultad}}</option> -->

                            
                        </select>
                    </div> 
                    
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.resolucion')" for="programa-resolucion">resolucion</label>
                        <input type="text" class="form-control" name="resolucion" id="programa-resolucion"
                            :class="{'valid': !$v.programa.resolucion.$invalid, 'invalid': $v.programa.resolucion.$invalid }" v-model="$v.programa.resolucion.$model" />
                    </div>

                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.titulo')" for="programa-titulo">Título que otorga</label>
                        <input type="text" class="form-control" name="titulo" id="programa-titulo"
                            :class="{'valid': !$v.programa.titulo.$invalid, 'invalid': $v.programa.titulo.$invalid }" v-model="$v.programa.titulo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.programa.duracionSemestres')" for="programa-duracionSemestres">Dureación en Semestres</label>
                        <input type="number" class="form-control" name="duracionSemestres" id="programa-duracionSemestres"
                            :class="{'valid': !$v.programa.duracionSemestres.$invalid, 'invalid': $v.programa.duracionSemestres.$invalid }" v-model="$v.programa.duracionSemestres.$model" />
                    </div>

                     <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.programa.programaFacultad')" for="programa-programaFacultad">Facultad</label>
                        <select class="form-control" id="programa-programaFacultad" name="programaFacultad" v-model="programa.programaFacultadId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="facultadOption.id" v-for="facultadOption in facultads" :key="facultadOption.id">{{facultadOption.facultad}}</option>
                        </select>
                    </div> 
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.programa.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./programa-update.component.ts">
</script>
