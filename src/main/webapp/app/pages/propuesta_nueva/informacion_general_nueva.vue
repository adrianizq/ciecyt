<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-nueva :proyectoId="$route.params.proyectoId"></menu-lateral-nueva>
    </div>
    <div class="col-sm-8">
      <div class="page-header mb-3">
        <h4 style="color:#003366; font-weight:600;">
          <font-awesome-icon icon="edit" />&nbsp; Información General
        </h4>
        <p class="text-muted mb-0" style="font-size:0.85rem;">Complete los datos básicos de su propuesta de grado</p>
      </div>
      <form @submit.prevent="save('continuar')">
        <div class="row">
          <div class="col-12">
            <div class="form-group" v-if="proyecto.id">
              <label for="id" v-text="$t('global.field.id')">ID</label>
              <input type="text" class="form-control" id="id" name="id" v-model="proyecto.id" readonly />
            </div>

            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.titulo')" for="proyecto-titulo">Titulo</label>
              <input
                type="text"
                class="form-control"
                name="titulo"
                id="proyecto-titulo"
                v-model="proyecto.titulo"
                :class="{
                  'is-invalid': $v.proyecto.titulo.$error,
                  'is-valid': !$v.proyecto.titulo.$invalid,
                }"
                placeholder="Ingrese el Titulo del Proyecto"
              />
              <div class="text-danger" v-if="!$v.proyecto.titulo.required">Este campo es requerido</div>
            </div>
         
            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.lugarEjecucion')" for="proyecto-url"
                >Lugar de Ejecución</label
              >
              <input
                type="text"
                class="form-control"
                name="lugar-ejecucion"
                id="proyecto-lugar-ejecucion"
                :class="{
                  'is-invalid': $v.proyecto.lugarEjecucion.$error,
                  'is-valid': !$v.proyecto.lugarEjecucion.$invalid,
                }"
                v-model="proyecto.lugarEjecucion"
                placeholder="Ingrese el lugar (ciudad, ubicación) donde se ejecutará el proyecto"
              />
              <div class="text-danger" v-if="!$v.proyecto.lugarEjecucion.required">Este campo es requerido</div>
            </div>
          
            <div class="form-group">
              <label for="datepicker-sm">Fecha de Inicio</label>
              <b-form-datepicker
                size="sm-6"
                local="ESP"
                id="fecha-inicio"
                name="fecha-inicio"
                value="value"
                :class="{
                  'is-invalid': $v.proyecto.fechaIni.$error,
                  'is-valid': !$v.proyecto.fechaIni.$invalid,
                }"
                v-model="proyecto.fechaIni"
              >
              </b-form-datepicker>
              <div class="text-danger" v-if="!$v.proyecto.fechaIni.required">Este campo es requerido</div>

              <label for="datepicker-lg">Fecha de Finalización</label>
              <b-form-datepicker
                size="sm-6"
                local="ESP"
                id="fecha-fin"
                name="fecha-fin"
                value="value"
                :class="{
                  'is-invalid': $v.proyecto.fechaFin.$error,
                  'is-valid': !$v.proyecto.fechaFin.$invalid,
                }"
                v-model="proyecto.fechaFin"
              >
              </b-form-datepicker>
              <div class="text-danger" v-if="!$v.proyecto.fechaFin.required">Este campo es requerido</div>
            </div>

          </div>

          <div class="col-md-6 col-12">
            <div class="form-group">
              <label class="form-control-label" for="proyecto-ciclo">Ciclo de Formación</label>
              <b-form-select
                :options="ciclos"
                text-field="ciclo"
                value-field="id"
                id="ciclo"
                v-model="proyecto.proyectoCicloId"
                @change="onCicloChange"
                :class="{
                  'is-invalid': $v.proyecto.proyectoCicloId.$error,
                  'is-valid': !$v.proyecto.proyectoCicloId.$invalid,
                }"
              >
              </b-form-select>
              <div class="text-danger" v-if="!$v.proyecto.proyectoCicloId.required">Este campo es requerido</div>
            </div>
          </div>

          <div class="col-md-6 col-12">
            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.proyectoModalidad')" for="proyecto-modalidad"
                >Modalidad</label
              >
              <b-form-select
                :options="modalidadsFiltradas"
                text-field="modalidad"
                value-field="id"
                id="modalidad"
                v-model="proyecto.proyectoModalidadId"
                 @input="setModalidad"
                :disabled="!proyecto.proyectoCicloId"
                :class="{
                  'is-invalid': $v.proyecto.proyectoModalidadId.$error,
                  'is-valid': !$v.proyecto.proyectoModalidadId.$invalid,
                }"
              >
              </b-form-select>
              <div class="text-danger" v-if="!$v.proyecto.proyectoModalidadId.required">Este campo es requerido</div>
              <small class="text-muted" v-if="!proyecto.proyectoCicloId">Seleccione un ciclo primero</small>
            </div>
          </div>

          <div class="col-md-6 col-12">
            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.facultad')" for="proyecto-facultad">Facultad</label>
              <b-form-select
                :options="facultades"
                text-field="facultad"
                value-field="id"
                id="facultad"
                v-model="proyecto.facultadId"
                :class="{
                  'is-invalid': $v.proyecto.facultadId.$error,
                  'is-valid': !$v.proyecto.facultadId.$invalid,
                }"
              >
              </b-form-select>
              <div class="text-danger" v-if="!$v.proyecto.facultadId.required">Este campo es requerido</div>
            </div>
          </div>

          <!-- Programa -->
          <div class="col-md-6 col-12">
            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.programa.programa')" for="proyecto-programa">Programa</label>
              <b-form-select
                text-field="programa"
                value-field="id"
                id="programa"
                v-model="proyecto.proyectoProgramaId"
                :class="{
                  'is-invalid': $v.proyecto.proyectoProgramaId.$error,
                  'is-valid': !$v.proyecto.proyectoProgramaId.$invalid,
                }"
              >
                <option v-for="(selectOption, indexOpt) in Programas" :key="indexOpt" :value="selectOption.id">
                  ({{ selectOption.id }}) {{ selectOption.programa }}
                </option>
              </b-form-select>
              <div class="text-danger" v-if="!$v.proyecto.proyectoProgramaId.required">Este campo es requerido</div>
            </div>
          </div>

          <!-- Linea de Investigación -->
          <div class="col-md-6 col-12">
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')"
                for="proyecto-linea-investigacion"
                >Linea de Investigación</label
              >
              <b-form-select :disabled="!modalidad.contieneLinea"
              text-field="linea" value-field="id" id="linea_investigacion" v-model="proyecto.proyectoLineaInvestigacionId"
              :options="LineasInvestigacionOptions">
              </b-form-select>
            </div>
          </div>

          <div class="col-md-6 col-12">
            <div class="form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')" for="sub-linea-investigacion"
                >Sublinea de Investigación</label
              >
              <b-form-select :disabled="!modalidad.contieneLinea"
                text-field="linea"
                value-field="id"
                id="sub_linea_linea_investigacion"
                v-model="proyecto.subLineaLineaInvestigacionId"
                :options="SubLineasOptions">
              </b-form-select>
            </div>
          </div>



        </div>
        <div class="form-actions mt-3">
          <button type="button" id="cancel-save" class="btn btn-outline-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>

          <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar y continuar</span>
          </button>

          <p class="typo__p text-danger" v-if="this.submitStatus === 'ERROR'">¡Existen campos sin llenar!.</p>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { Component, Inject, Vue } from 'vue-property-decorator';
import AlertService from '@/shared/alert/alert.service';

import MenuLateralNueva from '@/components/propuesta_nueva/menu_lateral_nueva.vue';
import ModalidadService from '@/entities/modalidad/modalidad.service';
import { IModalidad, Modalidad } from '@/shared/model/modalidad.model';
import FacultadService from '@/entities/facultad/facultad.service';
import { IFacultad } from '@/shared/model/facultad.model';
import LineaInvestigacionService from '@/entities/linea-investigacion/linea-investigacion.service';
import { ILineaInvestigacion } from '@/shared/model/linea-investigacion.model';
//ADR
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import ProgramaService from '@/entities/programa/programa.service';
import { IPrograma, Programa } from '@/shared/model/programa.model';
import CicloService from '@/entities/ciclo/ciclo.service';
import { ICiclo } from '@/shared/model/ciclo.model';

import { numeric, required, minLength, maxLength, between, url } from 'vuelidate/lib/validators';
import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';

const validations: any = {
  proyecto: {
    //id: {},
    titulo: { required, maxLength: maxLength(100000) },
    //palabrasClave: { required, maxLength: maxLength(100000) },
    proyectoCicloId: { required },
    proyectoModalidadId: { required },
    facultadId: { required },
    //proyectoLineaInvestigacionId:  { required, between: between(1, 100000000)},
    //subLineaLineaInvestigacionId:  { required, between: between(1, 100000000)},
    proyectoProgramaId: { required },
    //url: { url},
    lugarEjecucion: { required },
    //duracion: {},
    fechaIni: { required },
    fechaFin: { required },
    //contrapartidaPesos: {},
    //contrapartidaEspecie: {},
    //convocatoria: {}
  },
  integranteProyecto: {
    integranteProyectoUserId: { required },
  },
};

@Component({
  components: { MenuLateralNueva },

  validations,
})
export default class PropuestaInformacionGeneral extends Vue {
  @Inject('modalidadService') private modalidadService: () => ModalidadService;
  @Inject('facultadService') private facultadService: () => FacultadService;
  @Inject('lineaInvestigacionService') private lineaInvestigacionService: () => LineaInvestigacionService;
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  @Inject('programaService') private programaService: () => ProgramaService;
  @Inject('cicloService') private cicloService: () => CicloService;

  @Inject('alertService') private alertService: () => AlertService;

  public modalidads: IModalidad[] = [];
  public modalidad: IModalidad = new Modalidad();
  public facultades: IFacultad[] = [];
  public lineas_investigacion: ILineaInvestigacion[] = [];
  public ciclos: ICiclo[] = [];

  public linea_investigacion: number = null;
  public facultad: number = null;
  public user: number = null;
  public nombresApellidos: string = null;
  public proyecto: IProyecto = new Proyecto();
  public proyId: string = null;
  public integranteProyecto: IIntegranteProyecto = new IntegranteProyecto();
  public programs: IPrograma[] = [];
  public programa: IPrograma = new Programa();

  public isSaving = false;

  public submitStatus: string = 'PENDING';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initRelationships();
    });
  }

  public save(accion: 'borrador' | 'continuar' = 'continuar'): void {
    this.isSaving = true;
    console.log('Guardando proyecto:', JSON.stringify(this.proyecto));

    // this.$v.$touch();
    /* if (this.$v.$invalid) {
            
                if(this.$v.proyecto.titulo.$invalid){
                    this.setTitulo("");
                }
                if(this.$v.proyecto.palabrasClave.$invalid){
                    this.setPalabrasClave("");
                }
                if(this.$v.proyecto.proyectoModalidadId.$invalid){
                    this.setModalidad(0);
                }
                if(this.$v.proyecto.facultadId.$invalid){
                    this.setFacultad(0);
                }
                if(this.$v.proyecto.proyectoLineaInvestigacionId.$invalid){
                    this.setLinea(0);
                }
                 if(this.$v.proyecto.subLineaLineaInvestigacionId.$invalid){
                    this.setSubLinea(0);
                }
                  if(this.$v.integranteProyecto.integranteProyectoUserId.$invalid){
                    console.log(this.$v);
                    this.setAsesor("");
                    
                }
            
                this.submitStatus = 'ERROR';
            }
            else{ 

                */

    if (this.proyecto.id) {
      this.proyectoService()
        .updateProyecto(this.proyecto)
        .then(param => {
          this.isSaving = false;
          if (accion === 'borrador') {
            this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
            return;
          }
          if (this.esSinFlujo) {
            this.$router.push({ name: 'PropuestasInvestigadorEditView' });
          } else {
            this.$router.push({ name: 'PropuestaIntegrantesNuevaEditView', params: { proyectoId: this.proyecto.id.toString() } });
          }
          const message = this.$t('ciecytApp.proyecto.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(err => {
          this.isSaving = false;
          console.error('Error actualizando proyecto:', err);
          this.alertService().showAlert('Error al guardar: ' + (err.response ? err.response.data.message : err.message), 'danger');
        });
    } else {
      this.proyectoService()
        .createProyecto(this.proyecto)
        .then(param => {
          this.isSaving = false;

          this.proyId = String(param.id);
          this.proyecto.id = param.id;

          if (accion === 'borrador') {
            this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
            return;
          }

          if (this.esSinFlujo) {
            this.$router.push({ name: 'PropuestasInvestigadorEditView' });
          } else {
            this.$router.push({ name: 'PropuestaIntegrantesNuevaEditView', params: { proyectoId: this.proyId } });
          }

          const message = 'Se ha creado un nuevo proyecto';
          this.alertService().showAlert(message, 'success');
        })
        .catch(err => {
          this.isSaving = false;
          console.error('Error creando proyecto:', err);
          this.alertService().showAlert('Error al guardar: ' + (err.response ? err.response.data.message : err.message), 'danger');
        });
    }
    this.submitStatus = 'PENDING';
    setTimeout(() => {
      this.submitStatus = 'OK';
    }, 500);
    //}
    //console.log(this.submitStatus);
  }

  get LineasInvestigacion() {
    return this.lineas_investigacion.filter(linea => {
      return !linea.lineaPadreId ;
    });
  }

  get LineasInvestigacionOptions() {
    return this.LineasInvestigacion.map(l => ({
      id: l.id,
      linea: (l.codigoLinea ? '(' + l.codigoLinea + ') ' : '') + l.linea
    }));
  }

  get SubLineas() {
    return this.lineas_investigacion.filter(linea => {
      return linea.lineaPadreId == this.proyecto.proyectoLineaInvestigacionId && linea.lineaPadreId;
    });
  }

  get SubLineasOptions() {
    return this.SubLineas.map(l => ({
      id: l.id,
      linea: (l.codigoLinea ? '(' + l.codigoLinea + ') ' : '') + l.linea
    }));
  }

/*get Modalidad() {
    return this.modalidads.filter(modalidad => {
      return modalidad.id == this.proyecto.proyectoModalidadId;
    });
  }*/

  get Programas() {
    return this.programs.filter(programa => {
      return programa.programaFacultadId == this.proyecto.facultadId;
    });
  }

  get modalidadsFiltradas(): IModalidad[] {
    if (!this.proyecto.proyectoCicloId) {
      return [];
    }
    return this.modalidads;
  }

  get esSinFlujo(): boolean {
    if (this.modalidad && this.modalidad.modalidad) {
      return this.modalidad.modalidad === 'Diplomado' || this.modalidad.modalidad === 'Especializacion';
    }
    const found = this.modalidads.find(m => m.id === this.proyecto.proyectoModalidadId);
    if (found && found.modalidad) {
      return found.modalidad === 'Diplomado' || found.modalidad === 'Especializacion';
    }
    return false;
  }

  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;

    // Cargar listas base necesarias para los selects
    this.ciclos = await this.cicloService().retrieveAll();

    this.facultades = (await this.facultadService().retrieve()).data;

    this.lineas_investigacion = (await this.lineaInvestigacionService().retrieve()).data;

    // Si estamos editando un proyecto existente, cargar sus datos y relaciones
    if (this.proyId) {
      const res = await this.proyectoService().retrieveWithAsesor(this.proyId);
      const proyectoCargado = res.data;
      console.log('Proyecto cargado:', proyectoCargado);

      // Cargar la modalidad completa para saber si contiene linea/sublinea
      if (proyectoCargado.proyectoModalidadId) {
        this.modalidad = await this.modalidadService().find(proyectoCargado.proyectoModalidadId);
        console.log('Modalidad cargada:', this.modalidad);
      }

      // Cargar modalidades y programas asociados al ciclo del proyecto
      if (proyectoCargado.proyectoCicloId) {
        this.modalidads = await this.cicloService().findModalidadesByCiclo(proyectoCargado.proyectoCicloId);
        const selectedCiclo = this.ciclos.find(c => c.id === proyectoCargado.proyectoCicloId);
        if (selectedCiclo && selectedCiclo.ciclo) {
          this.programs = await this.programaService().findByCiclo(selectedCiclo.ciclo);
        }
      }

      // Asignar el proyecto al final, cuando todo lo demas ya esta listo
      this.proyecto = proyectoCargado;
    }
  }
  //metodos para las validaciones
  /*   setTitulo(value) {
            this.iniciandoTitulo= false;
            this.submitStatus='ERROR';
        }

        setPalabrasClave(value) {
            this.iniciandoPalabrasClave= false;
            this.submitStatus='ERROR';
        }

        setModalidad(value) {
            this.iniciandoModalidad= false;
            this.submitStatus='ERROR';
         }

          setFacultad(value) {
             this.iniciandoFacultad= false;
             this.submitStatus='ERROR';
          }

          setLinea(value) {
             this.iniciandoLinea= false;
             this.submitStatus='ERROR';
          }

           setSubLinea(value) {
             this.iniciandoSubLinea= false;
             this.submitStatus='ERROR';
          }
*/
  setModalidad(value){
      if (!value) {
        this.modalidad = new Modalidad();
        this.proyecto.proyectoLineaInvestigacionId = null;
        this.proyecto.subLineaLineaInvestigacionId = null;
        this.proyecto.proyectoLineaInvestigacionLinea = null;
        this.proyecto.subLineaLineaInvestigacionLinea = null;
        return;
      }
      // Si la modalidad no cambio realmente, no limpiar la linea/sublinea ya cargada
      if (value === this.proyecto.proyectoModalidadId) {
        this.modalidadService()
          .find(value)
          .then(res => {
            this.modalidad = res;
          });
        return;
      }
      this.modalidadService()
        .find(value)
        .then(res => {
          this.modalidad = res;
        });
      this.proyecto.proyectoLineaInvestigacionId = null;
      this.proyecto.subLineaLineaInvestigacionId = null;
      this.proyecto.proyectoLineaInvestigacionLinea = null;
      this.proyecto.subLineaLineaInvestigacionLinea = null;
  }

  onCicloChange(cicloId: number) {
    this.proyecto.proyectoModalidadId = null;
    this.modalidad = new Modalidad();
    this.proyecto.proyectoLineaInvestigacionId = null;
    this.proyecto.subLineaLineaInvestigacionId = null;
    this.proyecto.proyectoLineaInvestigacionLinea = null;
    this.proyecto.subLineaLineaInvestigacionLinea = null;
    this.proyecto.proyectoProgramaId = null;
    if (cicloId) {
      this.cicloService()
        .findModalidadesByCiclo(cicloId)
        .then(res => {
          this.modalidads = res;
        });
      const selectedCiclo = this.ciclos.find(c => c.id === cicloId);
      if (selectedCiclo && selectedCiclo.ciclo) {
        this.programaService()
          .findByCiclo(selectedCiclo.ciclo)
          .then(res => {
            this.programs = res;
          });
      }
    } else {
      this.modalidads = [];
      this.programs = [];
    }
  }
}
</script>

<style scoped>
.page-header {
  padding: 1rem 0;
  border-bottom: 1px solid #e5e7eb;
}

.form-actions {
  padding-top: 1rem;
  border-top: 1px solid #e5e7eb;
}

::v-deep .form-control:focus {
  border-color: #C4A94D;
  box-shadow: 0 0 0 0.2rem rgba(196, 169, 77, 0.25);
}

::v-deep .custom-select:focus {
  border-color: #C4A94D;
  box-shadow: 0 0 0 0.2rem rgba(196, 169, 77, 0.25);
}

::v-deep .btn-primary {
  background-color: #003366;
  border-color: #003366;
}

::v-deep .btn-primary:hover {
  background-color: #002244;
  border-color: #002244;
}

::v-deep .btn-outline-secondary {
  color: #6b7280;
  border-color: #d1d5db;
}

::v-deep .btn-outline-secondary:hover {
  background-color: #f3f4f6;
  color: #374151;
  border-color: #d1d5db;
}
</style>
