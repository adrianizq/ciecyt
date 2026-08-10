<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-nueva :proyectoId='$route.params.proyectoId'></menu-lateral-nueva>
        </div>
        <div class="col-sm-8">
            <form @submit.prevent="save('continuar')">
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
                        <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')" :disabled="isSaving || !integranteProyecto.integranteProyectoUserId">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
                        </button>
                        <button type="button" id="save" class="btn btn-primary" v-on:click="save('continuar')" :disabled="isSaving || !integranteProyecto.integranteProyectoUserId">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar y continuar</span>
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

        public async save(accion: 'borrador' | 'continuar' = 'continuar'): Promise<void> {
            if (!this.integranteProyecto.integranteProyectoUserId) {
                this.alertService().showAlert('Debe seleccionar un asesor', 'danger');
                return;
            }

            this.isSaving = true;
            try {
                if (this.integranteProyecto.id) {
                    const param = await this.integranteProyectoService().update(this.integranteProyecto);
                    this.integranteProyecto = param;
                    if (accion === 'continuar') {
                        this.alertService().showAlert('Asesor actualizado correctamente', 'success');
                    }
                } else {
                    const param = await this.integranteProyectoService().create(this.integranteProyecto);
                    this.integranteProyecto = param;
                    if (accion === 'continuar') {
                        this.alertService().showAlert('Asesor guardado correctamente', 'success');
                    }
                }
                if (accion === 'borrador') {
                    this.isSaving = false;
                    this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
                    return;
                }
                this.$router.push({ name: 'PropuestaJuradoNuevaEditView', params: { proyectoId: String(this.proyId) } });
            } catch (err) {
                this.isSaving = false;
                console.error('Error guardando asesor:', err);
                this.alertService().showAlert('Error al guardar el asesor: ' + (err.response ? err.response.data.message : err.message), 'danger');
            }
        }

        async initRelationships() {
            this.isSaving = true;
            try {
                this.proyId = parseInt(this.$route.params.proyectoId);
                this.proyecto = await this.proyectoService().find(this.proyId);
                this.modalidadId = this.proyecto.proyectoModalidadId;

                const asesoresRes = await this.integranteProyectoService().retrieveAsesoresProyecto(this.proyId);
                if (asesoresRes.data && asesoresRes.data.length > 0) {
                    this.integranteProyecto = asesoresRes.data[0];
                }

                // Si no tiene asesor, preparar uno nuevo con el rol correspondiente
                if (!this.integranteProyecto.id) {
                    const rolRes = await this.rolesModalidadService().findRolModalidad("Asesor", this.modalidadId);
                    this.rolesModalidad = rolRes;
                    this.rolModalidadId = rolRes.id;

                    this.integranteProyecto = new IntegranteProyecto();
                    this.integranteProyecto.integranteProyectoProyectoId = this.proyId;
                    this.integranteProyecto.integranteProyectoRolesModalidadId = this.rolModalidadId;
                }

                // Cargar usuarios asesores al final para que el select tenga sus opciones
                const usuariosRes = await this.usuarioService().retrieveAsesores();
                usuariosRes.data.forEach((item) => {
                    if (item.firstName && item.lastName && item.userInfo) {
                        if (item.userInfo.nuip)
                            item.nombresApellidos = item.firstName + ' ' + item.lastName + ' ' + item.userInfo.nuip;
                    } else if (item.firstName && item.lastName) {
                        item.nombresApellidos = item.firstName + ' ' + item.lastName;
                    }

                    this.users.push(item);
                    this.options.push({ value: item.id, text: item.nombresApellidos })
                });
            } catch (e) {
                console.error('Error cargando datos del asesor:', e);
                this.alertService().showAlert('Error al cargar los datos del asesor', 'danger');
            } finally {
                this.isSaving = false;
            }
        }

    }
</script>

<style scoped>