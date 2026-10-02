<template>
    <div class="row">
        <div class="col-sm-4">
            <menu-lateral-ciecyt :proyectoId='$route.params.proyectoId'></menu-lateral-ciecyt>
        </div>
        <div class="col-sm-8">
            <form @submit.prevent="save()">
                <div class="row">
                    <div class="col-12" v-for="(integrante, i) in integrantesProyecto" :key="i">
                        <b-form-group class="mb-3"
                            :label="`Jurado # ${i + 1}`"
                            :label-for="`integrante-${i}`"
                        >
                            <b-form-select
                              v-if="!integrante.esExterno"
                              :id="`integrante-${i}`"
                              :options="withPlaceholder(options, 'busque por nombre o cedula')"
                              v-model="integrante.integranteProyectoUserId"
                            ></b-form-select>
                        <b-form-select
                          v-if="integrante.esExterno"
                          :id="`integrante-${i}`"
                          :options="withPlaceholder(opcionesExternos, 'profesional externo verificado por el CIECYT')"
                          v-model="integrante.integranteProyectoExternoId"
                        ></b-form-select>
                        <b-form-checkbox
                            class="mt-2"
                            v-model="integrante.esExterno"
                            @change="alternarExterno(integrante)"
                        >
                            Documentarlo como profesional externo (sin vinculo laboral con la institucion)
                        </b-form-checkbox>
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

    import MenuLateralCiecyt from '@/components/ciecyt/menu_lateral_ciecyt.vue';
    import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
    import { IRolesModalidad } from '@/shared/model/roles-modalidad.model';
    import UsuarioService from '@/entities/usuario/usuario.service';
    import { IUser } from '@/shared/model/user.model';
    import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
    import ProyectoService from '@/entities/proyecto/proyecto.service';
    import DocenteHabilitadoService from '@/entities/docente-habilitado/docente-habilitado.service';
    import { IDocenteHabilitado } from '@/shared/model/docente-habilitado.model';
    import AsesorExternoService from '@/entities/asesor-externo/asesor-externo.service';
    import { IAsesorExterno } from '@/shared/model/asesor-externo.model';

    import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
    import { withPlaceholder as withPlaceholderOptions } from '@/shared/filter/filter';
    import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';
 

    const validations: any = {};

    @Component({
        components: { MenuLateralCiecyt },
        validations
    })

    export default class PropuestaAsesores extends Vue {
        public withPlaceholder(options: any[], placeholder: string): any[] {
            return withPlaceholderOptions(options, placeholder);
        }
        @Inject('usuarioService') private usuarioService: () => UsuarioService;
        @Inject('proyectoService') private proyectoService: () => ProyectoService;
        @Inject('docenteHabilitadoService') private docenteHabilitadoService: () => DocenteHabilitadoService;
        @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;
        @Inject('rolesModalidadService') private rolesModalidadService: () => RolesModalidadService;
        @Inject('asesorExternoService') private asesorExternoService: () => AsesorExternoService;
        @Inject('alertService') private alertService: () => AlertService;

        public users: IUser[] = [];
        public rolesModalidad: IRolesModalidad;
        public integrantesProyecto: IIntegranteProyecto[] = [];
        public user: number = null;
        public proyecto: IProyecto = new Proyecto();
        public facultadId: number = null;
        public proyId?: any;
        public isSaving = false;
        public modalidadId: number = 0;
        public n: number = 0;
        public cantJurados: number = 0;
        public rolModalidadId?: number =0;
          public options : any = [];
          public opcionesExternos: any = [];

//public proyId: string = null;

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

        public back() {
             this.$router.go(-1);
           // this.$router.push({ name: 'PropuestaListadoCiecytView', params: { proyectoId: this.proyId } });
        }

        public save(): void {
            try {
                this.isSaving = true;
                for (let integrante of this.integrantesProyecto) {
                    if (integrante.esExterno) {
                        integrante.integranteProyectoUserId = null;
                        integrante.integranteProyectoUserLogin = null;
                        integrante.integranteProyectoUserFirstName = null;
                        integrante.integranteProyectoUserLastName = null;
                    } else {
                        integrante.integranteProyectoExternoId = null;
                        integrante.integranteProyectoExternoNombre = null;
                    }
                    //Actualizando el integrante
                    if (integrante.id) {
                        this.integranteProyectoService().update(integrante);
                         (<any>this).$router.go(0);
                    } else {
                        //Creando un nuevo integrante
                        this.integranteProyectoService().create(integrante)
                            .then(param => {
                                    (<any>this).$router.go(0);                            });
                    }
                     var proyId: string = String(this.proyId);
                    // this.$router.push({ name: 'PropuestaElementosView', params: { proyectoId: proyId } });

                }

            } catch (e) {
                //TODO: mostrar mensajes de error
            }
        }

          /**
          * Los candidatos son los que la decanatura tiene habilitados como jurado en la
          * facultad del proyecto, no todos los usuarios con ese rol. Es la lista de la que
          * el CIECYT designa, y el backend rechaza a quien no este en ella.
          */
         async cargarHabilitados() {
            if (!this.facultadId) {
                this.options = [];
                return;
            }
            try {
                const res = await this.docenteHabilitadoService().retrieveDeFacultad(this.facultadId, 'JURADO');
                const habilitados: IDocenteHabilitado[] = res.data || [];
                this.options = habilitados.map(h => {
                    const u: any = h.user || {};
                    const nombre = ((u.firstName || '') + ' ' + (u.lastName || '')).trim();
                    const texto = nombre ? nombre + ' (' + (u.login || '') + ')' : u.login;
                    return {
                        value: u.id,
                        text: (texto || 'Sin nombre') + ' — ' + (h.rol || ''),
                        vigente: !h.fechaHasta
                    };
                });
                this.users = habilitados.map(h => ({ id: (h.user || {}).id } as any));
            } catch (e) {
                this.options = [];
                this.users = [];
                if (e && e.response && e.response.status === 403) {
                    this.alertService()
                        .error('No tiene permiso para consultar el padron de habilitados de la facultad')
                        .then(() => {});
                }
            }
         }

         /**
          * Los profesionales externos son los que el CIECYT ya verifico para esta facultad,
          * solo asi pueden designarse (Acuerdo 25, paragrafos 1 de los articulos 8 y 9). La
          * lista sale del registro de asesores externos, no de los usuarios del sistema.
          */
         async cargarExternos() {
            if (!this.facultadId) {
                this.opcionesExternos = [];
                return;
            }
            try {
                const res = await this.asesorExternoService().retrieveDeFacultad(this.facultadId, 'JURADO');
                const externos: IAsesorExterno[] = res.data || [];
                this.opcionesExternos = externos.map(e => {
                    const nombre = ((e.nombres || '') + ' ' + (e.apellidos || '')).trim();
                    return {
                        value: e.id,
                        text: (nombre || 'Sin nombre') + ' (doc. ' + (e.numeroDocumento || '') + ')'
                    };
                });
            } catch (e) {
                this.opcionesExternos = [];
                if (e && e.response && e.response.status === 403) {
                    this.alertService()
                        .error('No tiene permiso para consultar los profesionales externos de la facultad')
                        .then(() => {});
                }
            }
         }

         /**
          * Al abrir la casilla de externo se descarta el usuario institucional seleccionado, y al
          * cerrarla se descarta el externo: nunca se guardan las dos designaciones a la vez y el
          * backend lo rechaza asi llegara igualmente.
          */
         public alternarExterno(integrante: any) {
            if (integrante.esExterno) {
                integrante.integranteProyectoUserId = null;
                integrante.integranteProyectoUserLogin = null;
                integrante.integranteProyectoUserFirstName = null;
                integrante.integranteProyectoUserLastName = null;
            } else {
                integrante.integranteProyectoExternoId = null;
                integrante.integranteProyectoExternoNombre = null;
            }
         }

         /**
          * Quien ya estaba designado conserva su designacion aun si salio del padron despues, pero
          * no apareceria en la lista de candidatos y el selector lo mostraria vacio. Se agrega a
          * las opciones, marcado como no vigente, para que lo ya designado se vea y no se pierda.
          */
         public anadirDesignadosFueraDelPadron() {
             if (!this.integrantesProyecto) {
                 return;
             }
             const yaEnOpciones = new Set(this.options.map(o => o.value));
             const yaExternos = new Set(this.opcionesExternos.map(o => o.value));
             for (const integrante of this.integrantesProyecto) {
                 const extId = integrante.integranteProyectoExternoId;
                 if (extId != null) {
                     this.$set(integrante, 'esExterno', true);
                     if (!yaExternos.has(extId)) {
                         this.opcionesExternos.push({
                             value: extId,
                             text: (integrante.integranteProyectoExternoNombre || 'Profesional externo') + ' (ya designado)'
                         });
                         yaExternos.add(extId);
                     }
                 } else {
                     this.$set(integrante, 'esExterno', false);
                 }
                 const u: any = (integrante as any).integranteProyectoUser || {};
                 const userId = integrante.integranteProyectoUserId;
                 if (userId == null || yaEnOpciones.has(userId)) {
                     continue;
                 }
                 const nombre = ((u.firstName || '') + ' ' + (u.lastName || '')).trim();
                 const texto = nombre ? nombre + ' (' + (u.login || '') + ')' : (u.login || 'Docente');
                 this.options.push({
                     value: userId,
                     text: texto + ' (no vigente — ya designado)',
                     vigente: false
                 });
                 yaEnOpciones.add(userId);
             }
         }

         async initRelationships() {
            try {
                //El proyecto se carga primero: la lista de candidatos sale de la facultad a la
                //que pertenece, y sin ella no hay contra quien comparar.
                this.proyId = parseInt(this.$route.params.proyectoId);

                this.proyecto = await this.proyectoService().find(this.proyId);
                this.facultadId = this.proyecto.facultadId;
                this.modalidadId = this.proyecto.proyectoModalidadId;

                await this.cargarHabilitados();

                await this.cargarExternos();

await this.integranteProyectoService()
                    .retrieveJuradosProyecto(this.proyId,"Jurado" )
                    .then(res => {
                       this.integrantesProyecto = res.data;
                       this.anadirDesignadosFueraDelPadron();
                   });
                    
                  if(this.integrantesProyecto.length==0){  
                  await this.rolesModalidadService()
                    .findRolModalidad("Jurado", this.modalidadId )
                    .then(res => {
                        this.rolesModalidad = res;
                        this.cantJurados = res.cantidad;
                        this.rolModalidadId = res.id;

                        console.log( this.cantJurados);
                        
                         for (var i = 0; i < this.cantJurados; i++) {
                            let integrante = new IntegranteProyecto();

                            integrante.integranteProyectoProyectoId = this.proyId;
                            integrante.integranteProyectoRolesModalidadId = this.rolModalidadId;
                            integrante.esExterno = false;

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