<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-nueva :proyectoId='$route.params.proyectoId'></menu-lateral-nueva>
        </div>
        <div class="col-sm-8">
            <form @submit.prevent="save('continuar')">
                <div class="row">
                    <div class="col-12" v-for="(integrante, i) in integrantesProyecto" :key="i">
                    <b-form-group class="mb-3"
                        label="Busca los integrantes"
                        label-for="search-integrantes"
                    >
                        <b-form-select
                          :options="withPlaceholder(options, 'busque por nombre o cedula')"
                          v-model="integrante.integranteProyectoUserId"
                        ></b-form-select>
                    </b-form-group>
                    </div>
                </div>

                <!--
                  Acuerdo 25, art. 7 par. 2 y Tabla 9: la opcion de grado Publicacion
                  de Articulo aplica limite de estudiantes segun la categoria de la
                  revista indexada. A1|A2 admiten hasta 3, B|C admiten hasta 2.
                -->
                <div v-if="esPublicacionArticulo" class="row mt-3 mb-3">
                  <div class="col-12">
                    <b-form-group
                      label="Categoria de la revista indexada (Publindex u homologo)"
                      label-for="categoria-revista"
                    >
                      <b-form-select
                        id="categoria-revista"
                        v-model="proyecto.publicacionCategoriaRevista"
                        :options="categoriaRevistaOpciones"
                      ></b-form-select>
                    </b-form-group>

                    <div
                      class="alert alert-warning"
                      role="alert"
                      v-if="limiteExcedido"
                    >
                      <strong>Excede el maximo permitido.</strong>
                      La categoria
                      <em>{{ proyecto.publicacionCategoriaRevista }}</em>
                      admite hasta {{ integrantesMaxPermitidos }} estudiantes pero
                      hay {{ conteoIntegrantesDesignados }}. Ajuste la categoria o
                      reduzca el numero de integrantes antes de guardar.
                    </div>

                    <div class="small text-muted">
                      Integrantes designados: <strong>{{ conteoIntegrantesDesignados }}</strong>
                      / {{ integrantesMaxPermitidos }} permitidos por la categoria seleccionada.
                    </div>
                  </div>
                </div>

                <br><br>
                <div class="row">
                    <div class="col-12">
                        <button type="button" id="cancel" class="btn btn-secondary" v-on:click="back">
                            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;Volver
                        </button>
                        <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')" :disabled="isSaving || limiteExcedido">
                            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
                        </button>
                        <button type="button" id="save" class="btn btn-primary" v-on:click="save('continuar')" :disabled="isSaving || limiteExcedido">
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

    import MenuLateralNueva from '@/components/propuesta_nueva/menu_lateral_nueva.vue';
    import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
    import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
    import UsuarioService from '@/entities/usuario/usuario.service';
    import { IUser } from '@/shared/model/user.model';
    import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
    import ProyectoService from '@/entities/proyecto/proyecto.service';

    import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
    import {
      EnumCategoriaRevista,
      integrantesPermitidosPorCategoriaRevista,
    } from '@/shared/model/enumerations/enum-categoria-revista.model';
    import { withPlaceholder as withPlaceholderOptions } from '@/shared/filter/filter';
    import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';


import { useVuelidate } from '@vuelidate/core';

    const MODALIDAD_PUBLICACION_ARTICULO = 9006;

    @Component({
        components: { MenuLateralNueva },
    })

    export default class PropuestaIntegrantes extends Vue {
        public withPlaceholder(options: any[], placeholder: string): any[] {
            return withPlaceholderOptions(options, placeholder);
        }
        @Inject  private usuarioService: () => UsuarioService;
        @Inject  private proyectoService: () => ProyectoService;
        @Inject  private integranteProyectoService: () => IntegranteProyectoService;
        @Inject  private rolesModalidadService: () => RolesModalidadService;
        @Inject  private alertService: () => AlertService;

        public users: IUser[] = [];
        public rolesModalidad: IRolesModalidad;
        public integrantesProyecto: IIntegranteProyecto[] = [];
        public user: number = null;
        public proyecto: IProyecto = new Proyecto();
        public proyId?: any;
        public isSaving = false;
        public modalidadId: number = 0;
        public n: number = 0;
        public cantEstudiantes: number = 0;
        public rolModalidadId?: number =0;
        public options : any = [];

        /**
         * Opciones del selector de categoria de revista para la opcion de grado
         * Publicacion de Articulo.
         */
        public categoriaRevistaOpciones: any[] = [
          { value: null, text: 'Seleccione una categoria' },
          { value: EnumCategoriaRevista.A1, text: 'A1 (hasta 3 estudiantes)' },
          { value: EnumCategoriaRevista.A2, text: 'A2 (hasta 3 estudiantes)' },
          { value: EnumCategoriaRevista.B, text: 'B (hasta 2 estudiantes)' },
          { value: EnumCategoriaRevista.C, text: 'C (hasta 2 estudiantes)' },
        ];

        get esPublicacionArticulo(): boolean {
          return this.modalidadId === MODALIDAD_PUBLICACION_ARTICULO;
        }

        /**
         * Cuenta solo los integrantes con usuario efectivamente asignado: el resto
         * son casilleros vacios que no cuentan contra el limite del Acuerdo.
         */
        get conteoIntegrantesDesignados(): number {
          if (!this.integrantesProyecto) {
            return 0;
          }
          return this.integrantesProyecto.filter(i => i.integranteProyectoUserId != null).length;
        }

        get integrantesMaxPermitidos(): number {
          return integrantesPermitidosPorCategoriaRevista(this.proyecto.publicacionCategoriaRevista as EnumCategoriaRevista);
        }

        get limiteExcedido(): boolean {
          return this.esPublicacionArticulo && this.integrantesMaxPermitidos > 0
            && this.conteoIntegrantesDesignados > this.integrantesMaxPermitidos;
        }

        @Hook
        beforeRouteEnter(to, from, next) {
            next(async vm => {
                vm.initRelationships();
            });
        }

        mounted() {
            this.proyId = this.$route.params.proyectoId;
        }
        beforeMount() {
        }

        /*Methods for multi select*/
        public back() {
            this.$router.push({ name: 'PropuestaInformacionGeneralNuevaEditView', params: { proyectoId: this.proyId } });
        }

        public async save(accion: 'borrador' | 'continuar' = 'continuar'): Promise<void> {
            if (this.limiteExcedido) {
              this.alertService().showAlert(
                `La categoria ${this.proyecto.publicacionCategoriaRevista || 'sin categoria'} ` +
                `admite maximo ${this.integrantesMaxPermitidos} estudiantes, pero hay ` +
                `${this.conteoIntegrantesDesignados} asignados. Reduzca el numero de ` +
                'integrantes antes de continuar (Acuerdo 25, art. 7 par. 2 y Tabla 9).',
                'danger',
              );
              return;
            }
            this.isSaving = true;
            try {
                for (const integrante of this.integrantesProyecto) {
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
                    this.$router.push({ name: 'PropuestaAsesorNuevaEditView', params: { proyectoId: this.proyId } });
                }
            } catch (e) {
                this.alertService().showHttpError(this, e && e.response ? e.response : e);
            } finally {
                this.isSaving = false;
            }
        }

         async initRelationships() {
            try {
                this.usuarioService()
                    .retrieveEstudiantes()
                    .then(res => {
                        res.data.forEach((item) => {
                            if(item.firstName && item.lastName && item.userInfo ){
                                if(item.userInfo.nuip)
                                item.nombresApellidos = item.firstName + ' ' + item.lastName  + ' ' +  item.userInfo.nuip;
                            }else if(item.firstName && item.lastName){
                                item.nombresApellidos = item.firstName + ' ' + item.lastName;
                            }

                            this.users.push(item);
                            this.options.push({value: item.id, text: item.nombresApellidos})
                        });
                    });
                this.proyId = parseInt(this.$route.params.proyectoId);
                this.proyecto = await this.proyectoService().find(this.proyId);
                this.modalidadId = this.proyecto.proyectoModalidadId;

                await this.integranteProyectoService()
                    .retrieveEstudiantesProyecto(this.proyId)
                    .then(res => {
                       this.integrantesProyecto = res.data;
                   });

                  if(this.integrantesProyecto.length==0){
                    await this.rolesModalidadService()
                        .findRolModalidad('Estudiante', this.modalidadId )
                        .then(res => {
                            this.rolesModalidad = res;
                            this.cantEstudiantes = res.cantidad;
                            this.rolModalidadId = res.id;

                            for (var i = 0; i < this.cantEstudiantes; i++) {
                                let integrante = new IntegranteProyecto();

                                integrante.integranteProyectoProyectoId = this.proyId;
                                integrante.integranteProyectoRolesModalidadId = this.rolModalidadId;

                                this.integrantesProyecto.push(integrante);
                            }
                    });
                  }
            } catch (e) {
            }
        }
    }
</script>

<style scoped>
</style>
