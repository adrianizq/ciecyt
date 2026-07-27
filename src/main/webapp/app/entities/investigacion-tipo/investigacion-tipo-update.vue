<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.investigacionTipo.home.createOrEditLabel" v-text="$t('ciecytApp.investigacionTipo.home.createOrEditLabel')">Create or edit a investigacionTipo</h2>
                <div>
                    <div class="form-group" v-if="investigacionTipo.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="investigacionTipo.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.investigacionTipo.investigacionTipo')" for="investigacion-tipo-investigacionTipo">Investigacion Tipo</label>
                        <input type="text" class="form-control" name="investigacionTipo" id="investigacion-tipo-investigacion-tipo"
                            :class="{'valid': !$v.investigacionTipo.investigacionTipo.$invalid, 'invalid': $v.investigacionTipo.investigacionTipo.$invalid }" v-model="$v.investigacionTipo.investigacionTipo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.investigacionTipo.investigacionTipoDescripcion')" for="investigacion-tipo-investigacionTipoDescripcion">Investigacion Tipo Descripcion</label>
                        <input type="text" class="form-control" name="investigacionTipoDescripcion" id="investigacion-tipo-investigacionTipoDescripcion"
                            :class="{'valid': !$v.investigacionTipo.investigacionTipoDescripcion.$invalid, 'invalid': $v.investigacionTipo.investigacionTipoDescripcion.$invalid }" v-model="$v.investigacionTipo.investigacionTipoDescripcion.$model" />
                    </div>
                     <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.investigacionTipo.tipo')" for="investigacion-tipo-tipo"> Tipo</label>
                        <input type="text" class="form-control" name="tipo" id="investigacion-tipo-tipo"
                            :class="{'valid': !$v.investigacionTipo.tipo.$invalid, 'invalid': $v.investigacionTipo.tipo.$invalid }" v-model="$v.investigacionTipo.tipo.$model" />
                    </div>
                      <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.investigacionTipo.tipoDescripcion')" for="investigacion-tipo-tipoDescripcion"> Tipo Descripcion</label>
                        <input type="text" class="form-control" name="tipoDescripcion" id="investigacion-tipo-tipoDescripcion"
                            :class="{'valid': !$v.investigacionTipo.tipoDescripcion.$invalid, 'invalid': $v.investigacionTipo.tipoDescripcion.$invalid }" v-model="$v.investigacionTipo.tipoDescripcion.$model" />
                    </div>

                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.investigacionTipo.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./investigacion-tipo-update.component.ts">
</script>
