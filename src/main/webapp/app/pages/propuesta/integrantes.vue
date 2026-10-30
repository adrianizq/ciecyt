<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral :proyectoId='$route.params.proyectoId'></menu-lateral>
        </div>

        <div class="col-sm-8">
            <form @submit.prevent="save('continuar')">
                <div class="row">
                    <div class="mb-3 form-group">
                        <label class="form-control-label" for="encabezado">
                            <h2>Integrantes</h2>
                        </label>
                    </div>

                    <p class="text-muted">
                        La modalidad de tesis admite hasta {{ cantEstudiantes }} estudiante(s)
                        (Acuerdo 025, art. 7 par. 1). Usted queda registrado(a) como autor(a) de la
                        propuesta y puede incluir a un compañero(a) de forma opcional.
                    </p>

                    <div class="col-12" v-for="(integrante, i) in integrantesProyecto" :key="i">
                        <b-form-group class="mb-3"
                            :label="`Integrante # ${i + 1}`"
                            :label-for="`integrante-${i}`"
                        >
                            <div class="input-group">
                                <b-form-select
                                    :options="users"
                                    text-field="nombresApellidos"
                                    value-field="id" :id="`integrante-${i}`" v-model="integrante.integranteProyectoUserId"
                                    :disabled="esCreador(integrante)">

                                </b-form-select>
                                <div class="input-group-append" v-if="!esCreador(integrante) && integrante.integranteProyectoUserId">
                                    <button type="button" class="btn btn-outline-danger" @click="quitarIntegrante(i)" title="Quitar integrante">
                                        <font-awesome-icon icon="times"></font-awesome-icon>
                                    </button>
                                </div>
                            </div>
                            <small v-if="esCreador(integrante)" class="form-text text-muted">Usted (autor de la propuesta)</small>
                        </b-form-group>
                    </div>

                </div>


                <div class="row">
                    <div class="col-12">
                        <button type="button" id="cancel" class="btn btn-secondary" v-on:click="back">
                            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;Volver
                        </button>
                        <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')" :disabled="isSaving">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
                        </button>
                        <button type="button" id="save" class="btn btn-primary" v-on:click="save('continuar')" :disabled="isSaving">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar y continuar</span>
                        </button>
                    </div>
                </div>

            </form>
        </div>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
    import AlertService from '@/shared/alert/alert.service';

    import MenuLateral from '@/components/propuesta/menu_lateral.vue';
    import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
    import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
    import UsuarioService from '@/entities/usuario/usuario.service';
    import { IUser } from '@/shared/model/user.model';
    import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
    import ProyectoService from '@/entities/proyecto/proyecto.service';

    import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
    import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

    @Component({
        components: { MenuLateral },
    })

    export default class PropuestaIntegrantes extends Vue {
        @Inject  private usuarioService: () => UsuarioService;
        @Inject  private proyectoService: () => ProyectoService;
        @Inject  private integranteProyectoService: () => IntegranteProyectoService;
        @Inject  private rolesModalidadService: () => RolesModalidadService;
        @Inject  private alertService: () => AlertService;

        public users: IUser[] = [];
        public rolesModalidad: IRolesModalidad;
        public integrantesProyecto: IIntegranteProyecto[] = [];
        public proyecto: IProyecto = new Proyecto();
        public proyId?: any;
        public isSaving = false;
        public modalidadId: number = 0;
        public cantEstudiantes: number = 1;
        public rolModalidadId?: number = 0;
        public creadorId?: number = null;

        @Hook
        beforeRouteEnter(to, from, next) {
            next(async vm => {
                vm.initRelationships();
            });
        }

        mounted() {
            this.proyId = this.$route.params.proyectoId;
        }

        /**
         * El creador de la propuesta queda fijo en su casillero: no se puede
         * reasignar ni quitar, porque es el autor del trabajo de grado.
         */
        public esCreador(integrante: IIntegranteProyecto): boolean {
            return this.creadorId != null && integrante.integranteProyectoUserId === this.creadorId;
        }

        public back() {
            this.$router.push({ name: 'PropuestaInformacionGeneraEditlView', params: { proyectoId: this.proyId } });
        }

        public async quitarIntegrante(i: number): Promise<void> {
            const integrante = this.integrantesProyecto[i];
            try {
                if (integrante.id) {
                    await this.integranteProyectoService().delete(integrante.id);
                }
                integrante.integranteProyectoUserId = null;
                integrante.id = null;
            } catch (e) {
                this.alertService().showHttpError(this, e && e.response ? e.response : e);
            }
        }

        public async save(accion: 'borrador' | 'continuar' = 'continuar'): Promise<void> {
            this.isSaving = true;
            try {
                for (const integrante of this.integrantesProyecto) {
                    // Los casilleros sin persona asignada (p.ej. el compañero opcional
                    // que no se diligenció) no se guardan.
                    if (!integrante.integranteProyectoUserId) {
                        continue;
                    }
                    if (integrante.id) {
                        await this.integranteProyectoService().update(integrante);
                    } else {
                        const param = await this.integranteProyectoService().create(integrante);
                        integrante.id = param.id;
                    }
                }
                if (accion === 'borrador') {
                    this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
                } else {
                    this.$router.push({ name: 'PropuestaChecklistView', params: { proyectoId: this.proyId } });
                }
            } catch (e) {
                this.alertService().showHttpError(this, e && e.response ? e.response : e);
            } finally {
                this.isSaving = false;
            }
        }

        async initRelationships() {
            try {
                //Obteniendo los usuarios estudiantes
                this.usuarioService()
                    .retrieveEstudiantes()
                    .then(res => {
                        this.users = (res.data || []).map((item: any) => {
                            return { ...item, nombresApellidos: item.firstName + ' ' + item.lastName };
                        });
                    });

                this.proyId = parseInt(this.$route.params.proyectoId);

                this.proyecto = await this.proyectoService().find(this.proyId);

                this.modalidadId = this.proyecto.proyectoModalidadId;

                const account: any = this.$store.getters.account;
                this.creadorId = account ? account.id : null;

                this.rolesModalidad = await this.rolesModalidadService()
                    .findRolModalidad('Estudiante', this.modalidadId);
                this.cantEstudiantes = this.rolesModalidad && this.rolesModalidad.cantidad ? this.rolesModalidad.cantidad : 1;
                this.rolModalidadId = this.rolesModalidad ? this.rolesModalidad.id : null;

                const res = await this.integranteProyectoService()
                    .retrieveEstudiantesProyecto(this.proyId);
                this.integrantesProyecto = res.data || [];

                // El backend registra al creador apenas se crea el proyecto; para
                // proyectos anteriores a ese cambio se completa aquí su casillero.
                if (this.creadorId != null && !this.integrantesProyecto.some(it => it.integranteProyectoUserId === this.creadorId)) {
                    const vacio = this.integrantesProyecto.find(it => !it.integranteProyectoUserId);
                    if (vacio) {
                        vacio.integranteProyectoUserId = this.creadorId;
                    }
                }

                // Se muestran tantos casilleros como estudiantes admite la modalidad
                // para dejar disponible el cupo del compañero, aunque hoy esté vacío.
                while (this.integrantesProyecto.length < this.cantEstudiantes) {
                    const integrante = new IntegranteProyecto();

                    integrante.integranteProyectoProyectoId = this.proyId;
                    integrante.integranteProyectoRolesModalidadId = this.rolModalidadId;

                    this.integrantesProyecto.push(integrante);
                }
            } catch (e) {
            }
        }
    }
</script>

<style scoped>
</style>
