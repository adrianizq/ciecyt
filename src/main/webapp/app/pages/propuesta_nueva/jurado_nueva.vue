<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-nueva :proyectoId='$route.params.proyectoId'></menu-lateral-nueva>
        </div>
        <div class="col-sm-8">
            <form @submit.prevent="save()">
                <div class="row">
                    <div class="col-12" v-for="(integrante, i) in integrantesProyecto" :key="i">
                        <b-form-group
                            :label="`Jurado # ${i + 1}`"
                            :label-for="`integrante-${i}`"
                        >
                            <!--<b-form-select
                                :options="users"
                                text-field="nombresApellidos"
                                value-field="id" :id="`integrante-${i}`" v-model="integrante.integranteProyectoUserId">

                            </b-form-select>-->
                             <model-select 
                            :options="options"
                            placeholder="busque por nombre o cedula"
                            v-model="integrante.integranteProyectoUserId"
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
                        <button type="button" id="save" class="btn btn-primary" v-on:click="save()" :disabled="isSaving">
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

    export default class JuradosNueva extends Vue {
        @Inject('usuarioService') private usuarioService: () => UsuarioService;
        @Inject('proyectoService') private proyectoService: () => ProyectoService;
        @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;
        @Inject('rolesModalidadService') private rolesModalidadService: () => RolesModalidadService;
        @Inject('alertService') private alertService: () => AlertService;

        public users: IUser[] = [];
        public rolesModalidad: IRolesModalidad;
        public integrantesProyecto: IIntegranteProyecto[] = [];
        public user: number = null;
        public proyecto: IProyecto = new Proyecto();
        public proyId?: any;
        public isSaving = false;
        public modalidadId: number = 0;
        public n: number = 0;
        public cantJurados: number = 0;
        public rolModalidadId?: number =0;
        public options : any = [];

//public proyId: string = null;

        beforeRouteEnter(to, from, next) {
            next(async vm => {

                vm.initRelationships();

            });
        }

        public back() {
             this.$router.go(-1);
           // this.$router.push({ name: 'PropuestaListadoCiecytView', params: { proyectoId: this.proyId } });
        }

        public async save(): Promise<void> {
            if (!this.integrantesProyecto || this.integrantesProyecto.length === 0) {
                this.alertService().showAlert('No hay jurados para guardar', 'warning');
                return;
            }

            const sinSeleccionar = this.integrantesProyecto.some(i => !i.integranteProyectoUserId);
            if (sinSeleccionar) {
                this.alertService().showAlert('Debe seleccionar un jurado para cada campo', 'danger');
                return;
            }

            this.isSaving = true;
            try {
                for (let integrante of this.integrantesProyecto) {
                    if (integrante.id) {
                        await this.integranteProyectoService().update(integrante);
                    } else {
                        await this.integranteProyectoService().create(integrante);
                    }
                }
                this.alertService().showAlert('Jurados guardados correctamente', 'success');
                // Recargar la lista para reflejar los IDs asignados
                await this.cargarJuradosExistentes();
            } catch (e) {
                this.isSaving = false;
                console.error('Error guardando jurados:', e);
                this.alertService().showAlert('Error al guardar los jurados: ' + (e.response ? e.response.data.message : e.message), 'danger');
            }
        }

        async initRelationships() {
            this.isSaving = true;
            try {
                this.proyId = parseInt(this.$route.params.proyectoId);
                this.proyecto = await this.proyectoService().find(this.proyId);
                this.modalidadId = this.proyecto.proyectoModalidadId;

                // Cargar jurados existentes del proyecto
                await this.cargarJuradosExistentes();

                // Cargar lista de jurados disponibles
                const res = await this.usuarioService().retrieveJurados();
                res.data.forEach((item) => {
                    if (item.firstName && item.lastName && item.userInfo) {
                        if (item.userInfo.nuip)
                            item.nombresApellidos = item.firstName + ' ' + item.lastName + ' ' + item.userInfo.nuip;
                    } else if (item.firstName && item.lastName) {
                        item.nombresApellidos = item.firstName + ' ' + item.lastName;
                    }

                    this.users.push(item);
                    this.options.push({ value: item.id, text: item.nombresApellidos });
                });
            } catch (e) {
                console.error('Error cargando jurados:', e);
                this.alertService().showAlert('Error al cargar los datos de los jurados', 'danger');
            } finally {
                this.isSaving = false;
            }
        }

        async cargarJuradosExistentes() {
            try {
                const res = await this.integranteProyectoService().retrieveJuradosProyecto(this.proyId, "Jurado");
                this.integrantesProyecto = res.data;

                if (this.integrantesProyecto.length === 0) {
                    const rolRes = await this.rolesModalidadService().findRolModalidad("Jurado", this.modalidadId);
                    this.rolesModalidad = rolRes;
                    this.cantJurados = 1;
                    this.rolModalidadId = rolRes.id;

                    let integrante = new IntegranteProyecto();
                    integrante.integranteProyectoProyectoId = this.proyId;
                    integrante.integranteProyectoRolesModalidadId = this.rolModalidadId;
                    this.integrantesProyecto.push(integrante);
                }
            } catch (e) {
                console.error('Error cargando jurados existentes:', e);
            }
        }

    }
</script>

<style scoped>