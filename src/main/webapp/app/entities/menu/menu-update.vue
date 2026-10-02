<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
                <h2 id="ciecytApp.menu.home.createOrEditLabel" v-text="$t('ciecytApp.menu.home.createOrEditLabel')"></h2>
                <div>
                    <div class="mb-3 form-group" v-if="menu.id">
                        <label for="id" v-text="$t('global.field.id')"></label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="menu.id" readonly/>
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.menu.nombre')" for="menu-nombre"></label>
                        <input type="text" class="form-control" name="nombre" id="menu-nombre"
                               :class="{'valid': !v$.menu.nombre.$invalid, 'invalid': v$.menu.nombre.$invalid }" v-model="v$.menu.nombre.$model"/>
                    </div>
                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-text="$t('ciecytApp.menu.url')" for="menu-url"></label>
                        <input type="text" class="form-control" name="url" id="menu-url"
                               :class="{'valid': !v$.menu.url.$invalid, 'invalid': v$.menu.url.$invalid }" v-model="v$.menu.url.$model"/>
                    </div>

                    <div class="row">
                        <div class="col-sm-3">
                            <div class="mb-3 form-group">
                                <label class="form-control-label" v-text="$t('ciecytApp.menu.icono')" for="menu-icono"></label>

                                <div class="input-group mb-2">
                                    <div class="input-group-prepend">
                                        <div class="input-group-text">
                                            <font-awesome-icon :icon="menu.icono || 'asterisk'"></font-awesome-icon>
                                        </div>
                                    </div>
                                    <input type="text" class="form-control" name="icono" id="menu-icono"
                                           :class="{'valid': !v$.menu.icono.$invalid, 'invalid': v$.menu.icono.$invalid }" v-model="v$.menu.icono.$model"/>
                                </div>


                            </div>
                        </div>

                        <div class="col-sm-2">
                            <label class="form-control-label" v-text="$t('ciecytApp.menu.orden')" for="menu-orden"></label>
                            <input type="text" class="form-control" name="orden" id="menu-orden"
                                   :class="{'valid': !v$.menu.orden.$invalid, 'invalid': v$.menu.orden.$invalid }" v-model="v$.menu.orden.$model"/>
                        </div>

                        <div class="col-sm-2">
                            <div class="mb-3 form-group">
                                <div class="btn-switch">
                                    <label>Estado</label>
                                    <!--<toggle :options="['Público', 'Privado']" :default="!menu.esPublico" @change="changeEsPublico"></toggle>-->
                                    <b-button :variant="menu.activo ? 'success' : 'danger'" @click="menu.activo = !menu.activo">
                                        {{ menu.activo ? 'Activo' : 'Inactivo' }}
                                    </b-button>
                                </div>

                            </div>
                        </div>

                        <div class="col-sm-2">
                            <div class="mb-3 form-group">
                                <div class="btn-switch">
                                    <label>Alcance</label>
                                    <b-button :variant="menu.esPublico ? 'warning' : 'primary'" @click="menu.esPublico = !menu.esPublico">
                                        {{ menu.esPublico ? 'Público' : 'Privado' }}
                                    </b-button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="mb-3 form-group">
                        <label class="form-control-label" v-bind:value="$t('ciecytApp.menu.menuPadre')" for="menu-menuPadre">Menu Padre</label>
                        <select class="form-control" id="menu-menuPadre" name="menuPadre" v-model="menu.menuPadreId">
                            <option v-bind:value="null"></option>
                            <option v-bind:value="menuOption.id" v-for="menuOption in menus" :key="menuOption.id">{{menuOption.nombre}}</option>
                        </select>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="v$.menu.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')"></span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./menu-update.component.ts">
</script>
