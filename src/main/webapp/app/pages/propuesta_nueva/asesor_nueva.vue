<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-nueva :proyectoId='$route.params.proyectoId'></menu-lateral-nueva>
        </div>
        <div class="col-sm-8">
            <form @submit.prevent="save()">
                <div class="row">
                    <div class="col-12">
                        <b-form-group
                            label="Asesor del Proyecto"
                            label-for="asesor"
                        >
                            <model-select 
                            :options="options"
                            placeholder="busque por nombre o cedula"
                            v-model="integranteProyecto.integranteProyectoUserId"
                            >
                        </model-select>
                        </b-form-group>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12">
                        <button type="button" id="cancel" class="btn btn-secondary" v-on:click="back">
                            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;Volver
                        </button>
                        <button type="button" id="save" class="btn btn-primary" v-on:click="save()">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Guardar</span>
                        </button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue } from 'vue-property-decorator';
    import AlertService from '@/shared/alert/alert.service';

    import MenuLateralNueva from '@/components/propuesta_nueva/menu_lateral_nueva.vue';
    import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
    import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
    import UsuarioService from '@/entities/usuario/usuario.service';
    import { IUser } from '@/shared/model/user.model';
    import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
    import ProyectoService from '@/entities/proyecto/proyecto.service';

    import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
    import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';
    import 'vue-search-select/dist/VueSearchSelect.css'
    import { ModelSelect} from 'vue-search-select'

    const validations: any = {};

    @Component({
        components: { MenuLateralNueva, ModelSelect },
        validations
    })

    export default class PropuestaAsesor extends Vue {
        @Inject('usuarioService') private usuarioService: () => UsuarioService;
        @Inject('proyectoService') private proyectoService: () => ProyectoService;
        @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;
        @Inject('rolesModalidadService') private rolesModalidadService: () => RolesModalidadService;
        @Inject('alertService') private alertService: () => AlertService;

        public users: IUser[] = [];
        public rolesModalidad: IRolesModalidad;
        public integranteProyecto: IIntegranteProyecto = new IntegranteProyecto();
        public proyecto: IProyecto = new Proyecto();
        public proyId?: any;
        public isSaving = false;
        public modalidadId: number = 0;
        public rolModalidadId?: number = 0;
        public options: any = [];

        beforeRouteEnter(to, from, next) {
            next(async vm => {
                vm.initRelationships();
            });
        }

        mounted() {
            this.proyId = this.$route.params.proyectoId;
        }

        public back() {
            this.$router.push({ name: 'PropuestaIntegrantesNuevaEditView', params: { proyectoId: this.proyId } });
        }

        public save(): void {
            try {
                this.isSaving = true;
                if (this.integranteProyecto.id) {
                    this.integranteProyectoService().update(this.integranteProyecto)
                        .then(param => {
                            this.$router.push({ name: 'PropuestaViabilidadNuevaEditView', params: { proyectoId: this.proyId } });
                        });
                } else {
                    this.integranteProyectoService().create(this.integranteProyecto)
                        .then(param => {
                            this.$router.push({ name: 'PropuestaViabilidadNuevaEditView', params: { proyectoId: this.proyId } });
                        });
                }
            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

        async initRelationships() {
            try {
                this.usuarioService()
                    .retrieveAsesores()
                    .then(res => {
                        res.data.forEach((item) => {
                            if (item.firstName && item.lastName && item.userInfo) {
                                if (item.userInfo.nuip)
                                    item.nombresApellidos = item.firstName + ' ' + item.lastName + ' ' + item.userInfo.nuip;
                            } else if (item.firstName && item.lastName) {
                                item.nombresApellidos = item.firstName + ' ' + item.lastName;
                            }

                            this.users.push(item);
                            this.options.push({ value: item.id, text: item.nombresApellidos })
                        });
                    });

                this.proyId = parseInt(this.$route.params.proyectoId);
                this.proyecto = await this.proyectoService().find(this.proyId);
                this.modalidadId = this.proyecto.proyectoModalidadId;

                // Buscar si ya tiene un asesor asignado
                await this.integranteProyectoService()
                    .retrieveAsesoresProyecto(this.proyId)
                    .then(res => {
                        if (res.data && res.data.length > 0) {
                            this.integranteProyecto = res.data[0];
                        }
                    });

                // Si no tiene asesor, crear uno nuevo con el rol correspondiente
                if (!this.integranteProyecto.id) {
                    await this.rolesModalidadService()
                        .findRolModalidad("Asesor", this.modalidadId)
                        .then(res => {
                            this.rolesModalidad = res;
                            this.rolModalidadId = res.id;

                            this.integranteProyecto = new IntegranteProyecto();
                            this.integranteProyecto.integranteProyectoProyectoId = this.proyId;
                            this.integranteProyecto.integranteProyectoRolesModalidadId = this.rolModalidadId;
                        });
                }

            } catch (e) {

            }
        }

    }
</script>

<style scoped>