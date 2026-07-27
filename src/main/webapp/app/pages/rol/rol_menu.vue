<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
                <h2>Editar Permisos {{ rolName }}</h2>
                <div class="row">
                    <div class="col-sm-12">
                        <table class="table table-striped">
                            <tr>
                                <th>Menu aaa</th>
                                <th>Permitir Acceso</th>
                                <th>Permitir Crear</th>
                                <th>Permitir Editar</th>
                                <th>Permitir Eliminar</th>
                            </tr>
                            <template v-for="menu in menus" >
                                <tr>
                                    <td class="font-weight-bold">{{ menu.nombre }}</td>
                                    <td class="text-center">
                                        <input
                                            class="form-check-input"
                                            @change="selectAllSubmenu(menu, 'acceso')"
                                            v-model="rolMenu[menu.id].permitirAcceso"
                                            type="checkbox"
                                            :id="'check_ver_' + menu.id">
                                        <label class="form-check-label" :for="'check_ver_' + menu.id"></label>
                                    </td>
                                    <td class="text-center">
                                        <input
                                            class="form-check-input"
                                            @change="selectAllSubmenu(menu, 'crear')"
                                            v-model="rolMenu[menu.id].permitirCrear" type="checkbox" :id="'check_crear_' + menu.id">
                                        <label class="form-check-label" :for="'check_crear_' + menu.id"></label>
                                    </td>
                                    <td class="text-center">
                                        <input
                                            class="form-check-input"
                                            @change="selectAllSubmenu(menu, 'editar')"
                                            v-model="rolMenu[menu.id].permitirEditar" type="checkbox" :id="'check_editar_' + menu.id">
                                        <label class="form-check-label" :for="'check_editar_' + menu.id"></label>
                                    </td>
                                    <td class="text-center">
                                        <input
                                            class="form-check-input"
                                            @change="selectAllSubmenu(menu, 'eliminar')"
                                            v-model="rolMenu[menu.id].permitirEliminar" type="checkbox" :id="'check_eliminar_' + menu.id">
                                        <label class="form-check-label" :for="'check_eliminar_' + menu.id"></label>
                                    </td>
                                </tr>

                                <tr v-for="submenu in menu.children" :key="submenu.id">
                                    <td>&nbsp &nbsp{{ submenu.nombre }} </td>

                                    <td class="text-center">

                                        <input class="form-check-input" v-model="rolMenu[submenu.id].permitirAcceso" type="checkbox" :id="'check_ver_' + submenu.id">
                                        <label class="form-check-label" :for="'check_ver_' + submenu.id"></label>
                                    </td>
                                    <td class="text-center">
                                        <input class="form-check-input" v-model="rolMenu[submenu.id].permitirCrear" type="checkbox" :id="'check_crear_' + submenu.id">
                                        <label class="form-check-label" :for="'check_crear_' + submenu.id"></label>
                                    </td>
                                    <td class="text-center">
                                        <input class="form-check-input" v-model="rolMenu[submenu.id].permitirEditar" type="checkbox" :id="'check_editar_' + submenu.id">
                                        <label class="form-check-label" :for="'check_editar_' + submenu.id"></label>
                                    </td>
                                    <td class="text-center">
                                        <input class="form-check-input" v-model="rolMenu[submenu.id].permitirEliminar" type="checkbox" :id="'check_eliminar_' + submenu.id">
                                        <label class="form-check-label" :for="'check_eliminar_' + submenu.id"></label>
                                    </td>
                                </tr>
                            </template>

                        </table>
                    </div>

                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>

<script lang="ts" src="./rol_menu.component.ts"></script>
