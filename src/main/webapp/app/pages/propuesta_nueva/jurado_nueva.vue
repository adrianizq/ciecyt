import { useVuelidate } from '@vuelidate/core';
<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-nueva :proyectoId='$route.params.proyectoId'></menu-lateral-nueva>
        </div>
        <div class="col-sm-8">
                <div class="jurado-form">

                <div class="row">
                    <div class="col-12">
                        <b-alert show variant="info">
                            El jurado lo designa formalmente el CIECYT a partir de la lista de
                            jurados habilitados que remite la decanatura. El estudiante no escoge
                            al jurado: puede continuar y el CIECYT hará la designación.
                        </b-alert>
                        <ul v-if="juradosExistentesConNombre.length">
                            <li v-for="(j, i) in juradosExistentesConNombre" :key="i">
                                Jurado {{ i + 1 }}: {{ j }}
                            </li>
                        </ul>
                    </div>
                </div>

                <div class="row">
                    <div class="col-12">
                        <button type="button" id="cancel" class="btn btn-secondary" v-on:click="back">
                            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;Volver
                        </button>
                        <button type="button" id="save" class="btn btn-primary" v-on:click="continuar()">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Continuar</span>
                        </button>
                    </div>
                </div>

            </div>
        </div>
    </div>
</template>

<script lang="ts">
    import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
    import AlertService from '@/shared/alert/alert.service';

    import MenuLateralNueva from '@/components/propuesta_nueva/menu_lateral_nueva.vue';
    import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
    import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
    import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
    import ProyectoService from '@/entities/proyecto/proyecto.service';

    import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
    import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

    const validations: any = {};

    @Component({
        components: { MenuLateralNueva },
        validations
    })

    export default class JuradosNueva extends Vue {
        @Inject  private proyectoService: () => ProyectoService;
        @Inject  private integranteProyectoService: () => IntegranteProyectoService;
        @Inject  private rolesModalidadService: () => RolesModalidadService;
        @Inject  private alertService: () => AlertService;

        public rolesModalidad: IRolesModalidad;
        public integrantesProyecto: IIntegranteProyecto[] = [];
        public proyecto: IProyecto = new Proyecto();
        public proyId?: any;
        public isSaving = false;
        public modalidadId: number = 0;
        public rolModalidadId?: number = 0;

        @Hook
        beforeRouteEnter(to, from, next) {
            next(async vm => {
                vm.initRelationships();
            });
        }

        get juradosExistentesConNombre(): string[] {
            return this.integrantesProyecto
                .filter(i => i.integranteProyectoUserId || (i.integranteProyectoUser && i.integranteProyectoUser.id))
                .map(i => {
                    const u: any = i.integranteProyectoUser || {};
                    const nombre = ((u.firstName || '') + ' ' + (u.lastName || '')).trim();
                    return nombre ? nombre : ('Designado: id ' + (i.integranteProyectoUserId || u.id));
                });
        }

        public back() {
            this.$router.push({ name: 'PropuestaAsesorNuevaEditView', params: { proyectoId: String(this.proyId) } });
        }

        public async continuar(): Promise<void> {
            // El jurado lo designa el CIECYT desde la lista habilitada de la decanatura:
            // el estudiante no escoge ni guarda jurados aqui.
            this.$router.push({ name: 'PropuestaInscripcionNuevaEditView', params: { proyectoId: String(this.proyId) } });
        }

        async initRelationships() {
            this.isSaving = true;
            try {
                this.proyId = parseInt(this.$route.params.proyectoId);
                this.proyecto = await this.proyectoService().find(this.proyId);
                this.modalidadId = this.proyecto.proyectoModalidadId;

                // Cargar jurados existentes del proyecto
                await this.cargarJuradosExistentes();
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
                this.integrantesProyecto = res.data || [];

                if (this.integrantesProyecto.length === 0) {
                    const rolRes = await this.rolesModalidadService().findRolModalidad("Jurado", this.modalidadId);
                    if (!rolRes || !rolRes.id) {
                        // La modalidad no requiere jurado (Acuerdo 025, art. 9): se omite este paso
                        this.$router.push({ name: 'PropuestaInscripcionNuevaEditView', params: { proyectoId: String(this.proyId) } });
                        return;
                    }
                    this.rolesModalidad = rolRes;
                    this.cantJurados = rolRes.cantidad && rolRes.cantidad > 0 ? rolRes.cantidad : 1;
                    this.rolModalidadId = rolRes.id;

                    for (let i = 0; i < this.cantJurados; i++) {
                        let integrante = new IntegranteProyecto();
                        integrante.integranteProyectoProyectoId = this.proyId;
                        integrante.integranteProyectoRolesModalidadId = this.rolModalidadId;
                        this.integrantesProyecto.push(integrante);
                    }
                } else {
                    // Sincronizar el rol a partir del primer jurado existente para futuras operaciones
                    const primerJurado = this.integrantesProyecto[0];
                    if (primerJurado.integranteProyectoRolesModalidadId) {
                        this.rolModalidadId = primerJurado.integranteProyectoRolesModalidadId;
                    }
                    // Asegurar que todos los jurados cargados tengan el proyecto asignado
                    this.integrantesProyecto.forEach(i => {
                        i.integranteProyectoProyectoId = this.proyId;
                    });
                }
            } catch (e) {
                console.error('Error cargando jurados existentes:', e);
                this.alertService().showAlert('Error al cargar los jurados existentes: ' + (e.response ? e.response.data.message : e.message), 'danger');
            }
        }

    }
</script>

<style scoped>
</style>
