<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.cronogramaCiecytFases.home.createOrEditLabel" v-text="$t('ciecytApp.cronogramaCiecytFases.home.createOrEditLabel')">Create or edit a CronogramaCiecytFases</h2>
                <div>
                    <div class="form-group" v-if="cronogramaCiecytFases.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="cronogramaCiecytFases.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecytFases.inicioFase')" for="cronograma-ciecyt-fases-inicioFase">Inicio Fase</label>
                        <div class="input-group">
                            <input id="cronograma-ciecyt-fases-inicioFase" type="date" class="form-control" name="inicioFase"  :class="{'valid': !$v.cronogramaCiecytFases.inicioFase.$invalid, 'invalid': $v.cronogramaCiecytFases.inicioFase.$invalid }"
                            v-model="$v.cronogramaCiecytFases.inicioFase.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecytFases.finFase')" for="cronograma-ciecyt-fases-finFase">Fin Fase</label>
                        <div class="input-group">
                            <input id="cronograma-ciecyt-fases-finFase" type="date" class="form-control" name="finFase"  :class="{'valid': !$v.cronogramaCiecytFases.finFase.$invalid, 'invalid': $v.cronogramaCiecytFases.finFase.$invalid }"
                            v-model="$v.cronogramaCiecytFases.finFase.$model"  />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.cronogramaCiecytFases.textoExplicativo')" for="cronograma-ciecyt-fases-textoExplicativo">Texto Explicativo</label>
                        <input type="text" class="form-control" name="textoExplicativo" id="cronograma-ciecyt-fases-textoExplicativo"
                            :class="{'valid': !$v.cronogramaCiecytFases.textoExplicativo.$invalid, 'invalid': $v.cronogramaCiecytFases.textoExplicativo.$invalid }" v-model="$v.cronogramaCiecytFases.textoExplicativo.$model" />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecyt')" for="cronograma-ciecyt-fases-cronogramaCiecytFasesCronogramaCiecyt">Cronograma Ciecyt Fases Cronograma Ciecyt</label>
                        <select class="form-control" id="cronograma-ciecyt-fases-cronogramaCiecytFasesCronogramaCiecyt" name="cronogramaCiecytFasesCronogramaCiecyt" v-model="cronogramaCiecytFases.cronogramaCiecytFasesCronogramaCiecytId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="cronogramaCiecytOption.id" v-for="cronogramaCiecytOption in cronogramaCiecyts" :key="cronogramaCiecytOption.id">{{cronogramaCiecytOption.tituloCronograma}}</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.cronogramaCiecytFases.cronogramaCiecytFasesFases')" for="cronograma-ciecyt-fases-cronogramaCiecytFasesFases">Cronograma Ciecyt Fases Fases</label>
                        <select class="form-control" id="cronograma-ciecyt-fases-cronogramaCiecytFasesFases" name="cronogramaCiecytFasesFases" v-model="cronogramaCiecytFases.cronogramaCiecytFasesFasesId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="fasesOption.id" v-for="fasesOption in fases" :key="fasesOption.id">{{fasesOption.fase}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.cronogramaCiecytFases.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./cronograma-ciecyt-fases-update.component.ts">
</script>
