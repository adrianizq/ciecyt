<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral :proyectoId="$route.params.proyectoId"></menu-lateral>
    </div>
    <div class="col-sm-8">
      <form @submit.prevent="save('continuar')">
        <div class="row">
          <div class="col-12">
            <div class="mb-3 form-group">
              <label class="form-control-label" for="encabezado">
                <h2>Información General</h2>
              </label>
            </div>

            <div class="mb-3 form-group" v-if="proyecto.id">
              <label for="id" v-text="$t('global.field.id')"></label>
              <input type="text" class="form-control" id="id" name="id" v-model="proyecto.id" readonly />
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.titulo.$error }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.titulo')" for="proyecto-titulo"></label>
              <input
                disabled="true"
                type="text"
                class="form-control"
                name="titulo"
                id="proyecto-titulo"
                v-model="proyecto.titulo"
                :class="{
                  'is-invalid': v$.proyecto.titulo.$error,
                  'is-valid': !v$.proyecto.titulo.$invalid,
                }"
                placeholder="Ingrese el Titulo del Proyecto"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.proyecto.titulo.required">Este campo es requerido</div>
              <div class="text-danger" v-if="!v$.proyecto.titulo.minLength">
                Este campo debe tener al menos {{ v$.proyecto.titulo.$params.minLength.min }} caracteres.
              </div>
              <div class="text-danger" v-if="!v$.proyecto.titulo.maxLength">
                Este campo no debe tener más de {{ v$.proyecto.titulo.$params.maxLength.max }} caracteres.
              </div>
            </div>
            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.fechaIni.$error }">
              <label for="datepicker-sm">Fecha de Inicio</label>
              <b-form-datepicker
                size="sm-6"
                local="ESP"
                id="fecha-inicio"
                name="fecha-inicio"
                value="value"
                v-model="proyecto.fechaIni"
                :class="{
                  'is-invalid': v$.proyecto.fechaIni.$error,
                  'is-valid': !v$.proyecto.fechaIni.$invalid,
                }"
                placeholder="Fecha de inicio"
                disabled="true"
              >
              </b-form-datepicker>
              <div class="text-danger" v-if="!v$.proyecto.fechaIni.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.fechaFin.$error }">
              <label for="datepicker-sm">Fecha final</label>
              <b-form-datepicker
                size="sm-6"
                local="ESP"
                id="fecha-fin"
                name="fecha-fin"
                value="value"
                v-model="proyecto.fechaFin"
                :class="{
                  'is-invalid': v$.proyecto.fechaFin.$error,
                  'is-valid': !v$.proyecto.fechaFin.$invalid,
                }"
                placeholder="Fecha de finalización"
                disabled="true"
              >
              </b-form-datepicker>
              <div class="text-danger" v-if="!v$.proyecto.fechaFin.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.proyectoModalidadId.$error }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.proyectoModalidad')" for="proyecto-modalidad"
                ></label
              >
              <b-form-select
                :options="modalidads"
                text-field="modalidad"
                value-field="id"
                id="modalidad"
                v-model="proyecto.proyectoModalidadId"
                @update:model-value="setModalidad"
                :class="{
                  'is-invalid': v$.proyecto.proyectoModalidadId.$error,
                  'is-valid': !v$.proyecto.proyectoModalidadId.$invalid,
                }"
                disabled="true"
              >
              </b-form-select>

              <div class="text-danger" v-if="!v$.proyecto.proyectoModalidadId.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.facultadId }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.facultad')" for="proyecto-facultad"></label>
              <b-form-select
                :options="facultades"
                text-field="facultad"
                value-field="id"
                id="facultad"
                v-model="proyecto.facultadId"
                @update:model-value="setFacultad"
                :class="{
                  'is-invalid': v$.proyecto.facultadId.$error,
                  'is-valid': !v$.proyecto.facultadId.$invalid,
                }"
                disabled="true"
              >
              </b-form-select>

              <div class="text-danger" v-if="!v$.proyecto.facultadId.required">Este campo es requerido</div>
            </div>

            <!-- ////////////Programa -->

            <div class="mb-3 form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.programa.programa')" for="proyecto-programa"></label>
              <b-form-select
                text-field="programa"
                value-field="id"
                id="programa"
                v-model="proyecto.proyectoProgramaId"
                :class="{
                  'is-invalid': v$.proyecto.proyectoProgramaId.$error,
                  'is-valid': !v$.proyecto.proyectoProgramaId.$invalid,
                }"
                disabled="true"
              >
                <option v-for="(selectOption, indexOpt) in Programas" :key="indexOpt" :value="selectOption.id">
                  ({{ selectOption.id }}) {{ selectOption.programa }}
                </option>
              </b-form-select>
              <div class="text-danger" v-if="!v$.proyecto.proyectoProgramaId.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.proyectoLineaInvestigacionId }">
              <label
                class="form-control-label"
                v-text="$t('ciecytApp.proyecto.proyectoLineaInvestigacion')"
                for="proyecto-linea-investigacion"
                ></label
              >
              <b-form-select
                text-field="linea"
                value-field="id"
                id="linea_investigacion"
                v-model="proyecto.proyectoLineaInvestigacionId"
                @update:model-value="setLinea"
                :class="{
                  'is-invalid': v$.proyecto.proyectoLineaInvestigacionId.$error,
                  'is-valid': !v$.proyecto.proyectoLineaInvestigacionId.$invalid,
                }"
              >
                <option v-for="(selectOption, indexOpt) in LineasInvestigacion" :key="indexOpt" :value="selectOption.id">
                  ({{ selectOption.codigoLinea }}) {{ selectOption.linea }}
                </option>
              </b-form-select>

              <div class="text-danger" v-if="!v$.proyecto.proyectoLineaInvestigacionId.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.subLineaLineaInvestigacionId }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.subLineaLineaInvestigacion')" for="sub-linea-investigacion"
                ></label
              >
              <b-form-select
                text-field="linea"
                value-field="id"
                id="sub_linea_linea_investigacion"
                v-model="proyecto.subLineaLineaInvestigacionId"
                @update:model-value="setSubLinea"
                :class="{
                  'is-invalid': v$.proyecto.subLineaLineaInvestigacionId.$error,
                  'is-valid': !v$.proyecto.subLineaLineaInvestigacionId.$invalid,
                }"
              >
                <option v-for="(selectOption, indexOpt) in SubLineas" :key="indexOpt" :value="selectOption.id">
                  ({{ selectOption.codigoLinea }}) {{ selectOption.linea }}
                </option>
              </b-form-select>
              <div class="text-danger" v-if="!v$.proyecto.subLineaLineaInvestigacionId.required">Este campo es requerido</div>
            </div>

            <!-- ADR   -->

            <div class="mb-3 form-group">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.asesor')" for="asesor"></label>
              <b-form-select
                :options="users"
                text-field="nombresApellidos"
                value-field="id"
                id="proyecto-asesorId"
                v-model="proyecto.asesorId"
                @update:model-value="setAsesor"
              >
              </b-form-select>
              <small class="form-text text-muted"
                >Elija un asesor de la lista habilitada por la decanatura. El CIECYT hará la designación formal. No es
                requisito para guardar.</small
              >
            </div>

            <div class="mb-3 form-group">
              <label
                class="form-control-label"
                v-bind:value="$t('ciecytApp.proyecto.proyectoGrupoSemillero')"
                for="proyecto-proyectoGrupoSemillero"
                >Proyecto Grupo Semillero</label
              >
              <select
                class="form-control"
                id="proyecto-proyectoGrupoSemillero"
                name="proyectoGrupoSemillero"
                v-model="proyecto.proyectoGrupoSemilleroId"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="grupoSemilleroOption.id"
                  v-for="grupoSemilleroOption in grupoSemilleros"
                  :key="grupoSemilleroOption.id"
                  >{{ grupoSemilleroOption.nombre }}</option
                >
              </select>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.url.$error }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.url')" for="proyecto-url"></label>
              <input
                type="text"
                class="form-control"
                name="url"
                id="proyecto-url"
                v-model="proyecto.url"
                :class="{
                  'is-invalid': v$.proyecto.url.$error,
                  'is-valid': !v$.proyecto.url.$invalid,
                }"
                placeholder="Ingrese la Url del proyecto si existe"
              />
              <div class="text-danger" v-if="!v$.proyecto.url.url">La URL no es válida, ej: http://www.itp.edu.co</div>
            </div>

            <!--/tipo investigacion//////////////////////////////////////7 ///////////////////7-->

            <div class="mb-3 form-group">
              <label class="form-control-label" for="proyecto-facultad">Tipo de Investigacion</label>
              <b-form-select
                :options="investTipos"
                v-model="proyecto.tipo"
                :class="{
                  'is-invalid': v$.proyecto.tipo.$error,
                  'is-valid': !v$.proyecto.tipo.$invalid,
                }"
              >
              </b-form-select>
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.proyecto.tipo.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.palabrasClave.$error }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.palabrasClave')" for="proyecto-palabras-clave"
                ></label
              >
              <textarea
                rows="3"
                cols="6"
                class="form-control"
                name="palabrasClave"
                id="proyecto-palabras-clave"
                v-model="proyecto.palabrasClave"
                @input="setPalabrasClave($event.target.value)"
                :class="{
                  'is-invalid': v$.proyecto.palabrasClave.$error,
                  'is-valid': !v$.proyecto.palabrasClave.$invalid,
                }"
                placeholder="Ingrese las palabras clave separadas por comas"
              />
              <div class="text-danger" v-if="!v$.proyecto.palabrasClave.required">Este campo es requerido</div>
            </div>

            <div class="mb-3 form-group" :class="{ 'form-group--error': v$.proyecto.referencias.$error }">
              <label class="form-control-label" v-text="$t('ciecytApp.proyecto.referencias')" for="proyecto-referencias"></label>
              <textarea
                rows="3"
                cols="6"
                class="form-control"
                name="referencias"
                id="proyecto-referencias"
                v-model="proyecto.referencias"
                :class="{
                  'is-invalid': v$.proyecto.referencias.$error,
                  'is-valid': !v$.proyecto.referencias.$invalid,
                }"
                placeholder="Ingrese las referencias bibliográficas"
              />
            </div>
            <div class="text-danger" v-if="!v$.proyecto.referencias.required">Este campo es requerido</div>
          </div>
        </div>

        <div>
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')"></span>
          </button>

          <button type="button" id="save-borrador" class="btn btn-outline-secondary" v-on:click="save('borrador')" :disabled="isSaving">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar borrador</span>
          </button>

          <button type="submit" id="save-entity" class="btn btn-primary" :disabled="isSaving">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>Guardar y continuar</span>
          </button>

          <p class="typo__p text-danger" v-if="this.submitStatus === 'ERROR'">¡Existen campos sin llenar!.</p>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { useVuelidate } from '@vuelidate/core';
import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';

import MenuLateral from '@/components/propuesta/menu_lateral.vue';
import ModalidadService from '@/entities/modalidad/modalidad.service';
import { IModalidad } from '@/shared/model/modalidad.model';
import FacultadService from '@/entities/facultad/facultad.service';
import { IFacultad } from '@/shared/model/facultad.model';
import LineaInvestigacionService from '@/entities/linea-investigacion/linea-investigacion.service';
import { ILineaInvestigacion } from '@/shared/model/linea-investigacion.model';
import UsuarioService from '@/entities/usuario/usuario.service';
import { IUsuario } from '@/shared/model/usuario.model';
import { IUser } from '@/shared/model/user.model';

import GrupoSemilleroService from '@/entities/grupo-semillero/grupo-semillero.service';
import { IGrupoSemillero } from '@/shared/model/grupo-semillero.model';
//ADR
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import DocenteHabilitadoService from '@/entities/docente-habilitado/docente-habilitado.service';
import { IDocenteHabilitado } from '@/shared/model/docente-habilitado.model';
import ProgramaService from '@/entities/programa/programa.service';
import { IPrograma, Programa } from '@/shared/model/programa.model';
import RolesModalidadService from '@/entities/roles-modalidad/roles-modalidad.service';
import IntegranteProyectoService from '@/entities/integrante-proyecto/integrante-proyecto.service';

import { numeric, required, minLength, maxLength, url } from '@vuelidate/validators';
import { id } from 'date-fns/esm/locale';
import { IIntegranteProyecto, IntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import { IInvestigacionTipo } from '@/shared/model/investigacion-tipo.model';
import InvestigacionTipoService from '@/entities/investigacion-tipo/investigacion-tipo.service';

//import { id } from 'date-fns/locale';

const validations: any = {
  proyecto: {
    //id: {},
    titulo: { required, maxLength: maxLength(1000), minLength: minLength(5) },
    url: { url },
    //lugarEjecucion: {required, maxLength: maxLength(128)},
    tipo: { required, maxLength: maxLength(128) },
    fechaIni: { required },
    fechaFin: { required },
    palabrasClave: { required, maxLength: maxLength(100000) },

    proyectoModalidadId: { required },
    facultadId: { required },
    proyectoProgramaId: { required },
    proyectoLineaInvestigacionId: { required },
    subLineaLineaInvestigacionId: { required },

    referencias: { required },
    //contrapartidaPesos: {},
    //contrapartidaEspecie: {},
    //convocatoria: {},
  },
};

@Component({
  components: { MenuLateral },
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class PropuestaInformacionGeneral extends Vue {
  // El template llamaba previousState() pero el metodo no existia, asi que el boton
  // Cancel/atrás fallaba con un TypeError y no hacia nada.
  previousState() {
    window.history.back();
  }
  @Inject private modalidadService: () => ModalidadService;
  @Inject private facultadService: () => FacultadService;
  @Inject private lineaInvestigacionService: () => LineaInvestigacionService;
  @Inject private usuarioService: () => UsuarioService;
  @Inject private proyectoService: () => ProyectoService;
  @Inject private docenteHabilitadoService: () => DocenteHabilitadoService;
  @Inject private programaService: () => ProgramaService;
  @Inject private investigacionTipoService: () => InvestigacionTipoService;
  @Inject private rolesModalidadService: () => RolesModalidadService;
  @Inject private integranteProyectoService: () => IntegranteProyectoService;

  @Inject private grupoSemilleroService: () => GrupoSemilleroService;
  public grupoSemilleros: IGrupoSemillero[] = [];

  @Inject private alertService: () => AlertService;

  public modalidads: IModalidad[] = [];
  public facultades: IFacultad[] = [];
  public lineas_investigacion: ILineaInvestigacion[] = [];
  public users: IUser[] = [];
  //public programs: IPrograma[] = [];

  public linea_investigacion: number = null;

  public investigacionTips: IInvestigacionTipo[] = [];
  public investTipos: String[] = [];

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

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
      vm.initRelationships();
    });
  }

  public async save(accion: 'borrador' | 'continuar' = 'continuar'): Promise<void> {
    this.v$.$touch();
    if (this.v$.$invalid) {
      this.submitStatus = 'ERROR';
      this.isSaving = false;
      this.alertService().showAlert('Complete los campos obligatorios antes de continuar.', 'warning');
      return;
    }
    this.submitStatus = 'PENDING';
    this.isSaving = true;

    try {
      const esNuevo = !this.proyecto.id;
      const param = esNuevo
        ? await this.proyectoService().createProyecto(this.proyecto)
        : await this.proyectoService().updateProyecto(this.proyecto);

      if (esNuevo) {
        this.proyId = String(param.id);
        this.proyecto.id = param.id;
      }

      // El asesor no es una columna del proyecto: se guarda como integrante con el cargo
      // "Asesor". Se sincroniza aqui para que la eleccion del estudiante no se pierda al
      // recargar y quede disponible para la designacion formal del CIECYT.
      await this.sincronizarAsesor(param.id);

      this.isSaving = false;
      if (accion === 'borrador') {
        if (esNuevo) {
          // Al crear, la URL queda sin id y el menu lateral no habilita los demas pasos.
          this.$router
            .replace({ name: 'PropuestaInformacionGeneraEditlView', params: { proyectoId: this.proyId } })
            .catch(() => undefined);
        }
        this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
        return;
      }

      this.$router.push({ name: 'PropuestaIntegrantesView', params: { proyectoId: param.id.toString() } });
      const message = esNuevo ? 'Se ha creado un nuevo proyecto' : this.$t('ciecytApp.proyecto.updated', { param: param.id });
      this.alertService().showAlert(message, esNuevo ? 'success' : 'info');
    } catch (err) {
      this.isSaving = false;
      // Un error del servidor (permisos, asesor no habilitado, etc.) no significa que
      // falten campos: se deja que el alert muestre el detalle y no se marca el formulario
      // como incompleto.
      this.alertService().showHttpError(null, err);
    }
  }

  /**
   * Guarda la eleccion de asesor como integrante del proyecto con el cargo "Asesor".
   * El endpoint valida que el docente este habilitado por la decanatura y que quien
   * designa tenga permiso (el estudiante puede hacerlo en Tesis, Acuerdo 025).
   */
  private async sincronizarAsesor(proyectoId: number): Promise<void> {
    const asesorId = this.proyecto.asesorId;
    const modalidadId = this.proyecto.proyectoModalidadId;
    if (!asesorId || !modalidadId) {
      return;
    }

    const rolAsesor = await this.rolesModalidadService().findRolModalidad('Asesor', modalidadId);
    if (!rolAsesor || !rolAsesor.id) {
      return;
    }

    const res = await this.integranteProyectoService().retrieveAsesoresProyecto(proyectoId);
    const asesores: IIntegranteProyecto[] = (res && res.data) || [];
    if (asesores.some(integrante => integrante.integranteProyectoUserId === asesorId)) {
      return;
    }

    if (asesores.length > 0) {
      const designado = asesores[0];
      designado.integranteProyectoUserId = asesorId;
      await this.integranteProyectoService().update(designado);
      return;
    }

    const nuevo = new IntegranteProyecto();
    nuevo.integranteProyectoProyectoId = proyectoId;
    nuevo.integranteProyectoRolesModalidadId = rolAsesor.id;
    nuevo.integranteProyectoUserId = asesorId;
    await this.integranteProyectoService().create(nuevo);
  }

  get LineasInvestigacion() {
    return this.lineas_investigacion.filter(linea => {
      //return !linea.lineaPadreId && linea.lineaInvestigacionProgramaId == this.proyecto.proyectoProgramaId;
      return !linea.lineaPadreId;
    });
  }

  get SubLineas() {
    const programaId = this.proyecto.proyectoProgramaId;
    return this.lineas_investigacion.filter(linea => {
      const perteneceAlPrograma = programaId == null || linea.lineaInvestigacionProgramaId == programaId;
      return linea.lineaPadreId == this.proyecto.proyectoLineaInvestigacionId && linea.lineaPadreId && perteneceAlPrograma;
    });
  }

  get Programas() {
    return this.programs.filter(programa => {
      return programa.programaFacultadId == this.proyecto.facultadId;
    });
  }

  retrieveProyecto() {
    this.proyectoService()
      .find(parseInt(this.$route.params.proyectoId))
      .then(res => {
        this.proyecto = res;
      });
  }

  initRelationships() {
    this.proyId = this.$route.params.proyectoId;

    this.modalidadService()
      .retrieve()
      .then(res => {
        this.modalidads = res.data;
      });

    this.investigacionTipoService()
      .retrieve()
      .then(res => {
        this.investigacionTips = res.data;
        res.data.forEach(element => {
          this.investTipos.push(element.investigacionTipo);
        });
      });

    this.programaService()
      .retrieve()
      .then(res => {
        this.programs = res.data;
        //this.setProgramas(res.data);
      });

    //Obtenienedo el asesor del proyecto
    if (this.proyId) {
      this.proyectoService()
        .retrieveWithAsesor(this.proyId)
        .then(res => {
          this.proyecto = res.data;
          // La lista de asesores depende de la facultad del proyecto, que solo se conoce
          // cuando termina esta consulta.
          this.cargarAsesoresDeLaFacultad();
        });
    }

    //Obteniendo las facultadas
    this.facultadService()
      .retrieve()
      .then(res => {
        this.facultades = res.data;
      });

    //Obteniendo las lineas de investigacion
    this.lineaInvestigacionService()
      .retrieveAll()
      .then(res => {
        this.lineas_investigacion = res;
      });

    //Obteniendo los asesores habilitados de la facultad. La designacion sale de la lista que
    //remite la decanatura, no del directorio completo de la institucion.
    this.cargarAsesoresDeLaFacultad();

    this.grupoSemilleroService()
      .retrieve()
      .then(res => {
        this.grupoSemilleros = res.data;
      });
  }

  setAsesor(value) {
    //this.integranteProyecto.integranteProyectoUserId = value;
    this.proyecto.asesorId = value;
  }

  setFacultad(value) {
    this.iniciandoFacultad = false;
    this.submitStatus = 'ERROR';
    this.proyecto.facultadId = value;
    // El padron de habilitados es por facultad: la eleccion anterior deja de ser valida.
    this.proyecto.asesorId = null;
    this.cargarAsesoresDeLaFacultad();
  }

  /**
   * El template enganchaba estos cuatro manejadores que no existian: cada seleccion
   * lanzaba un TypeError. v-model ya escribe el valor, asi que aqui solo se hacen
   * los efectos derivados (limpiar la sublinea que depende de la linea elegida).
   */
  setModalidad() {
    this.proyecto.proyectoLineaInvestigacionId = null;
    this.proyecto.subLineaLineaInvestigacionId = null;
    this.proyecto.proyectoLineaInvestigacionLinea = null;
    this.proyecto.subLineaLineaInvestigacionLinea = null;
  }

  setLinea() {
    this.proyecto.subLineaLineaInvestigacionId = null;
    this.proyecto.subLineaLineaInvestigacionLinea = null;
  }

  setSubLinea() {
    this.v$.proyecto.subLineaLineaInvestigacionId.$touch();
  }

  setPalabrasClave() {
    this.v$.proyecto.palabrasClave.$touch();
  }

  /**
   * Los candidatos a asesor son los habilitados por la decanatura en la facultad del proyecto.
   * Sin facultad no hay lista: la habilitacion es por facultad.
   */
  cargarAsesoresDeLaFacultad() {
    this.users = [];
    const idFacultad = this.proyecto && this.proyecto.facultadId;
    if (!idFacultad) {
      return;
    }
    this.docenteHabilitadoService()
      .retrieveDeFacultad(idFacultad, 'ASESOR')
      .then(res => {
        (res.data || []).forEach((h: IDocenteHabilitado) => {
          const u: any = h.user || {};
          const nombre = ((u.firstName || '') + ' ' + (u.lastName || '')).trim();
          this.users.push({
            id: u.id,
            nombresApellidos: nombre ? nombre + ' (' + (u.login || '') + ')' : u.login,
          });
        });
        this.agregarAsesorDesignadoFueraDelPadron();
      })
      .catch(err => {
        this.users = [];
        this.agregarAsesorDesignadoFueraDelPadron();
      });
  }

  /**
   * Un asesor ya designado puede no estar en el padron vigente (la habilitacion se renueva
   * por semestre). Sin esta opcion el select quedaria vacio aunque el proyecto si tenga
   * asesor, y al guardar se perderia la designacion.
   */
  private agregarAsesorDesignadoFueraDelPadron(): void {
    const asesorId = this.proyecto && this.proyecto.asesorId;
    const proyectoId = this.proyId;
    if (!asesorId || !proyectoId || this.users.some(option => option.id === asesorId)) {
      return;
    }
    this.integranteProyectoService()
      .retrieveAsesoresProyecto(Number(proyectoId))
      .then(res => {
        const designado = (res.data || []).find(i => i.integranteProyectoUserId === asesorId);
        if (!designado || this.users.some(option => option.id === asesorId)) {
          return;
        }
        const nombre = `${designado.integranteProyectoUserFirstName || ''} ${
          designado.integranteProyectoUserLastName || ''
        }`.trim();
        this.users.push({
          id: asesorId,
          nombresApellidos: (nombre || designado.integranteProyectoUserLogin || 'Asesor designado') + ' (ya designado)',
        } as any);
      })
      .catch(() => undefined);
  }
}
</script>

<style scoped></style>
