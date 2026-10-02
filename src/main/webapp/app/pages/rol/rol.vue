<template>
    <div>
        <h2 id="page-heading">
            <span id="rol-heading">Roles</span>
            <!--<router-link custom v-slot="{ navigate }" :to="{name: 'AcuerdoCreate'}"><button id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-acuerdo" @click="navigate">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('ciecytApp.acuerdo.home.createLabel')"></span>
            </button></router-link>-->
        </h2>
        <br/>

        <div class="table-responsive" v-if="ROLES && ROLES.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th><span>Nombre</span>
                        <font-awesome-icon icon="sort"></font-awesome-icon>
                    </th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="rol in ROLES"
                    :key="rol">
                    <td>{{ rol }}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{ name: 'RolPermisos', params: { rol } }" class="btn btn-primary btn-sm details" title="Permisos">
                                <font-awesome-icon icon="lock"></font-awesome-icon>
                            </router-link>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script lang="ts" src="./rol.component.ts">
</script>
