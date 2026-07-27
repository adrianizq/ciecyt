<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.elemento.home.createOrEditLabel" v-text="$t('ciecytApp.elemento.home.createOrEditLabel')">Create or edit a Elemento</h2>
                <div>
                    <div class="form-group" v-if="elemento.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="elemento.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.elemento.elemento')" for="elemento-elemento">Elemento</label>
                        <input type="text" class="form-control" name="elemento" id="elemento-elemento"
                            :class="{'valid': !$v.elemento.elemento.$invalid, 'invalid': $v.elemento.elemento.$invalid }" v-model="$v.elemento.elemento.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.elemento.descripcion')" for="elemento-descripcion">Descripcion</label>
                        <input type="text" class="form-control" name="descripcion" id="elemento-descripcion"
                            :class="{'valid': !$v.elemento.descripcion.$invalid, 'invalid': $v.elemento.descripcion.$invalid }" v-model="$v.elemento.descripcion.$model" />
                    </div>
                    <!--
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.elemento.elementoFormato')" for="elemento-elementoFormato">Formato</label>
                        <select class="form-control" id="elemento-elementoFormato" name="elementoFormato" v-model="elemento.elementoFormatoId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="formatoOption.id" v-for="formatoOption in formatoes" :key="formatoOption.id">{{formatoOption.formato}}</option>
                        </select>
                    </div>
                    -->
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.elemento.elementoFase')" for="elemento-elementoFase">Fase</label>
                        <select class="form-control" id="elemento-elementoFase" name="elementoFase" v-model="elemento.elementoFasesId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="faseOption.id" v-for="faseOption in fases" :key="faseOption.id">{{faseOption.fase}}</option>
                        </select>
                    </div>

                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.elemento.elementoModalidad')" for="elemento-elementoModalidad">Modalidad</label>
                         <select class="form-control" multiple name="modalidad"  v-model="modalidadesAsignadas" >
                             <option v-for="modalidad of modalidads" 
                             :value="modalidad"
                             >
                            
                              {{modalidad.modalidad}}
                            </option>
                        </select>
                    </div>

                 
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.elemento.orden')" for="elemento-orden">Orden</label>
                        <input type="number" class="form-control" name="orden" id="elemento-orden"
                            :class="{'valid': !$v.elemento.orden.$invalid, 'invalid': $v.elemento.orden.$invalid }" v-model="$v.elemento.orden.$model" />
                    </div>


                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.elemento.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./elemento-update.component.ts">
</script>
