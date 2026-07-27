<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.cicloPropedeutico.home.createOrEditLabel" v-text="$t('ciecytApp.cicloPropedeutico.home.createOrEditLabel')">Create or edit a CicloPropedeutico</h2>
                <div>
                    <div class="form-group" v-if="cicloPropedeutico.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cicloPropedeutico.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cicloPropedeutico.ciclo')" for="ciclo-propedeutico-ciclo">Ciclo</label>
                        <select class="form-control" id="ciclo-propedeutico-ciclo" name="ciclo" v-model="cicloPropedeutico.ciclo">
                            <option :value="null" disabled>Seleccione un ciclo</option>
                            <option v-for="cicloOption in ciclos" :key="cicloOption.id" :value="cicloOption.ciclo">{{cicloOption.ciclo}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cicloPropedeutico.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./ciclo-propedeutico-update.component.ts">
</script>
