<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="ciecytApp.rubro.home.createOrEditLabel" v-text="$t('ciecytApp.rubro.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="rubro.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="rubro.id" readonly />
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.rubro.rubro')" for="rubro-rubro"></label>
                        <input type="text" class="form-control" name="rubro" id="rubro-rubro"
                            :class="{'valid': !v$.rubro.rubro.$invalid, 'invalid': v$.rubro.rubro.$invalid }" v-model="v$.rubro.rubro.$model" />
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.rubro.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./rubro-update.component.ts">
</script>
