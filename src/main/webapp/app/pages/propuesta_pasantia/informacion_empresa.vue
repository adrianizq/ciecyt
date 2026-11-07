<template>
  <div class="row">
    <div class="col-sm-4">
      <menu-lateral-pasantia :proyectoId="$route.params.proyectoId"></menu-lateral-pasantia>
    </div>
    <div class="col-sm-8">
      <form @submit.prevent="save('continuar')">
        <div class="row">
          <div class="col-12">
            <div class="mb-3 form-group">
              <label class="form-control-label" for="encabezado">
                <h2>Información de la Empresa</h2>
              </label>
            </div>
            <jhi-loading v-if="isFetching"></jhi-loading>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-convenio">
                Número del Convenio ITP - Empresa <span class="text-muted">(opcional)</span>
              </label>
              <input
                type="text"
                class="form-control"
                name="convenio-empresa"
                id="informacion-pasantia-convenio-empresa"
                v-model="informacionPasantia.convenio"
                :class="{
                  'is-invalid': v$.informacionPasantia.convenio.$error,
                  'is-valid': !v$.informacionPasantia.convenio.$invalid,
                }"
                placeholder="Número del convenio con la empresa que realizará la pasantía"
              />
              <small class="text-muted">El CIECYT verifica la existencia y vigencia del convenio.</small>
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.convenio.maxLength">
                Este campo no debe tener más de {{ v$.informacionPasantia.convenio.$params.maxLength.max }} caracteres.
              </div>
            </div>

            <div class="mb-3 form-group">
              <label
                class="form-control-label"
                v-text="$t('ciecytApp.informacionPasantia.duracionHoras')"
                for="informacion-pasantia-duracion-horas"
                ></label
              >
              <input
                type="number"
                class="form-control"
                name="duracion-horas"
                id="informacion-pasantia-duracion-horas"
                v-model.number="informacionPasantia.duracionHoras"
                :class="{
                  'is-invalid': v$.informacionPasantia.duracionHoras.$error,
                  'is-valid': !v$.informacionPasantia.duracionHoras.$invalid,
                }"
                placeholder="Horas de pasantía: técnico 480, tecnólogo 576, profesional 672"
              />
              <small v-if="horasEsperadasPasantia" class="text-muted">
                Para este ciclo se esperan {{ horasEsperadasPasantia }} horas efectivas.
              </small>
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.duracionHoras.required">Este campo es requerido</div>
              <div class="text-danger" v-if="!v$.informacionPasantia.duracionHoras.between">
                Se espera un valor numérico entre {{ v$.informacionPasantia.duracionHoras.$params.between.min }} y
                {{ v$.informacionPasantia.duracionHoras.$params.between.max }}.
              </div>
              <small v-if="duracionSemanasPasantia" class="text-muted d-block mt-1">
                Con 42 horas semanales equivale a {{ duracionSemanasPasantia.toFixed(1) }} semanas y
                {{ diasHabilesPasantia }} días hábiles.
              </small>
              <small v-if="fechaFinEstimadaPasantia" class="text-success d-block">
                Fecha estimada de finalización: {{ fechaCorta(fechaFinEstimadaPasantia) }} (días hábiles, sin festivos).
              </small>
              <small v-else class="text-muted d-block">
                La fecha estimada de finalización se calcula cuando el proyecto tenga fecha de inicio.
              </small>
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-nombre-empresa">Nombre de la empresa</label>
              <input
                type="text"
                class="form-control"
                name="nombre-empresa"
                id="informacion-pasantia-nombre-empresa"
                v-model="informacionPasantia.nombreEmpresa"
                :class="{
                  'is-invalid': v$.informacionPasantia.nombreEmpresa.$error,
                  'is-valid': !v$.informacionPasantia.nombreEmpresa.$invalid,
                }"
                placeholder="Nombre de la empresa donde realizará la pasantía"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.nombreEmpresa.required">Este campo es requerido</div>
              <div class="text-danger" v-if="!v$.informacionPasantia.nombreEmpresa.minLength">
                Este campo debe tener al menos {{ v$.informacionPasantia.nombreEmpresa.$params.minLength.min }} caracteres.
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.nombreEmpresa.maxLength">
                Este campo no debe tener más de {{ v$.informacionPasantia.nombreEmpresa.$params.maxLength.max }} caracteres.
              </div>
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-representante-legal">
                Representante Legal de la empresa
              </label>
              <input
                type="text"
                class="form-control"
                name="representante-legal-empresa"
                id="informacion-pasantia-representante-legal"
                v-model="informacionPasantia.representanteLegalEmpresa"
                :class="{
                  'is-invalid': v$.informacionPasantia.representanteLegalEmpresa.$error,
                  'is-valid': !v$.informacionPasantia.representanteLegalEmpresa.$invalid,
                }"
                placeholder="Nombre del representante legal de la empresa"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.representanteLegalEmpresa.required">
                Este campo es requerido
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.representanteLegalEmpresa.maxLength">
                Este campo no debe tener más de
                {{ v$.informacionPasantia.representanteLegalEmpresa.$params.maxLength.max }} caracteres.
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.representanteLegalEmpresa.alphaAndSpaceValidator">
                Este campo solo permite letras y espacios.
              </div>
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-identificacion-representante-legal">
                Identificación del Representante Legal de la empresa
              </label>
              <input
                type="text"
                class="form-control"
                name="identificacion-representante-legal"
                id="informacion-pasantia-identificacion-representante-legal"
                v-model="informacionPasantia.identificacionRepresentanteLegal"
                :class="{
                  'is-invalid': v$.informacionPasantia.identificacionRepresentanteLegal.$error,
                  'is-valid': !v$.informacionPasantia.identificacionRepresentanteLegal.$invalid,
                }"
                placeholder="Número de identificación del representante legal"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.identificacionRepresentanteLegal.required">
                Este campo es requerido
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.identificacionRepresentanteLegal.maxLength">
                Este campo no debe tener más de
                {{ v$.informacionPasantia.identificacionRepresentanteLegal.$params.maxLength.max }} caracteres.
              </div>
              <div
                class="text-danger"
                v-if="!v$.informacionPasantia.identificacionRepresentanteLegal.alphaDigitsSpaceQuoteAndDotValidator"
              >
                Este campo solo permite letras, números, espacios y puntos.
              </div>
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-asesor-empresa">Asesor de la empresa</label>
              <small class="text-muted d-block">
                El asesor de la empresa certifica el cumplimiento de los objetivos. El seguimiento académico lo realizan
                el asesor designado por la decanatura y el coordinador del CIECYT.
              </small>
              <input
                type="text"
                class="form-control mt-2"
                name="asesor-empresa"
                id="informacion-pasantia-asesor-empresa"
                v-model="informacionPasantia.asesorEmpresa"
                :class="{
                  'is-invalid': v$.informacionPasantia.asesorEmpresa.$error,
                  'is-valid': !v$.informacionPasantia.asesorEmpresa.$invalid,
                }"
                placeholder="Nombre del asesor de la empresa"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.asesorEmpresa.required">Este campo es requerido</div>
              <div class="text-danger" v-if="!v$.informacionPasantia.asesorEmpresa.maxLength">
                Este campo no debe tener más de {{ v$.informacionPasantia.asesorEmpresa.$params.maxLength.max }} caracteres.
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.asesorEmpresa.alphaAndSpaceValidator">
                Este campo solo permite letras y espacios.
              </div>
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-cargo-asesor-empresa">
                Cargo del asesor de la empresa
              </label>
              <input
                type="text"
                class="form-control"
                name="cargo-asesor-empresa"
                id="informacion-pasantia-cargo-asesor-empresa"
                v-model="informacionPasantia.cargoAsesorEmpresa"
                :class="{
                  'is-invalid': v$.informacionPasantia.cargoAsesorEmpresa.$error,
                  'is-valid': !v$.informacionPasantia.cargoAsesorEmpresa.$invalid,
                }"
                placeholder="Cargo del asesor de la empresa"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.cargoAsesorEmpresa.required">Este campo es requerido</div>
              <div class="text-danger" v-if="!v$.informacionPasantia.cargoAsesorEmpresa.maxLength">
                Este campo no debe tener más de
                {{ v$.informacionPasantia.cargoAsesorEmpresa.$params.maxLength.max }} caracteres.
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.cargoAsesorEmpresa.alphaAndSpaceValidator">
                Este campo solo permite letras y espacios.
              </div>
            </div>

            <div class="mb-3 form-group">
              <label class="form-control-label" for="informacion-pasantia-email-asesor-empresa">
                Email del asesor de la empresa
              </label>
              <input
                type="email"
                class="form-control"
                name="email-asesor-empresa"
                id="informacion-pasantia-email-asesor-empresa"
                v-model="informacionPasantia.emailAsesorEmpresa"
                :class="{
                  'is-invalid': v$.informacionPasantia.emailAsesorEmpresa.$error,
                  'is-valid': !v$.informacionPasantia.emailAsesorEmpresa.$invalid,
                }"
                placeholder="Correo electrónico del asesor de la empresa"
              />
              <div class="valid-feedback"></div>
              <div class="text-danger" v-if="!v$.informacionPasantia.emailAsesorEmpresa.required">Este campo es requerido</div>
              <div class="text-danger" v-if="!v$.informacionPasantia.emailAsesorEmpresa.email">
                La dirección de correo no es válida
              </div>
              <div class="text-danger" v-if="!v$.informacionPasantia.emailAsesorEmpresa.maxLength">
                Este campo no debe tener más de
                {{ v$.informacionPasantia.emailAsesorEmpresa.$params.maxLength.max }} caracteres.
              </div>
            </div>
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
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts">
import { useVuelidate } from '@vuelidate/core';
import { Component, Inject, Vue, Hook } from 'vue-facing-decorator';
import AlertService from '@/shared/alert/alert.service';

import MenuLateralPasantia from '@/components/propuesta_pasantia/menu_lateral_pasantia.vue';
import InformacionPasantiaService from '@/entities/informacion-pasantia/informacion-pasantia.service';
import { IInformacionPasantia, InformacionPasantia } from '@/shared/model/informacion-pasantia.model';
import ProyectoService from '@/entities/proyecto/proyecto.service';
import { IProyecto, Proyecto } from '@/shared/model/proyecto.model';

import { required, minLength, maxLength, between, email, helpers } from '@vuelidate/validators';
import { sumarDiasHabiles } from '@/shared/composables/dias-habiles';

const alphaAndSpaceValidator = helpers.regex(/^[A-Za-z\u00C0-\u017F- ]+$/i);
const alphaDigitsSpaceQuoteAndDotValidator = helpers.regex(/^[A-Za-z0-9 ./'-]+$/i);

const validations: any = {
  informacionPasantia: {
    convenio: { maxLength: maxLength(50) },
    duracionHoras: { required, between: between(480, 880) },
    nombreEmpresa: { required, maxLength: maxLength(100), minLength: minLength(3) },
    representanteLegalEmpresa: { required, maxLength: maxLength(100), alphaAndSpaceValidator },
    identificacionRepresentanteLegal: { required, maxLength: maxLength(20), alphaDigitsSpaceQuoteAndDotValidator },
    asesorEmpresa: { required, maxLength: maxLength(100), alphaAndSpaceValidator },
    cargoAsesorEmpresa: { required, maxLength: maxLength(100), alphaAndSpaceValidator },
    emailAsesorEmpresa: { required, email, maxLength: maxLength(254) },
  },
};

@Component({
  components: { MenuLateralPasantia },
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class PasantiaInformacionEmpresa extends Vue {
  previousState() {
    window.history.back();
  }

  @Inject private informacionPasantiaService: () => InformacionPasantiaService;
  @Inject private proyectoService: () => ProyectoService;
  @Inject private alertService: () => AlertService;

  public informacionPasantia: IInformacionPasantia = new InformacionPasantia();
  public proyecto: IProyecto = new Proyecto();

  public proyId: any;
  public isSaving = false;
  public submitStatus: string = 'PENDING';
  public isFetching = false;

  public get horasEsperadasPasantia(): number | null {
    const ciclo = Number(this.proyecto.proyectoCicloId);
    if (ciclo === 1) {
      return 480;
    }
    if (ciclo === 2) {
      return 576;
    }
    if (ciclo === 3) {
      return 672;
    }
    return null;
  }

  public get duracionSemanasPasantia(): number | null {
    const horas = Number(this.informacionPasantia.duracionHoras);
    if (!horas || horas <= 0) {
      return null;
    }
    return horas / 42;
  }

  public get diasHabilesPasantia(): number | null {
    const semanas = this.duracionSemanasPasantia;
    return semanas != null ? Math.ceil(semanas * 5) : null;
  }

  public get fechaFinEstimadaPasantia(): Date | null {
    if (!this.proyecto.fechaIni || this.diasHabilesPasantia == null) {
      return null;
    }
    return sumarDiasHabiles(this.proyecto.fechaIni, this.diasHabilesPasantia);
  }

  public fechaCorta(value: any): string {
    if (!value) {
      return '-';
    }
    const fecha = value instanceof Date ? value : new Date(value);
    if (isNaN(fecha.getTime())) {
      return String(value);
    }
    const dos = (numero: number) => String(numero).padStart(2, '0');
    return `${dos(fecha.getUTCDate())}/${dos(fecha.getUTCMonth() + 1)}/${fecha.getUTCFullYear()}`;
  }

  private actualizarFechaFinEstimada(): Promise<any> {
    const fecha = this.fechaFinEstimadaPasantia;
    if (!fecha) {
      return Promise.resolve();
    }
    this.proyecto.fechaFin = fecha;
    return this.proyectoService().updateProyecto(this.proyecto).catch(() => null);
  }

  mounted() {
    this.proyId = this.$route.params.proyectoId;
  }

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.initRelationships(to.params.proyectoId);
      }
    });
  }

  public save(accion: 'borrador' | 'continuar' = 'continuar'): void {
    this.isSaving = true;

    this.v$.$touch();
    if (this.v$.$invalid && accion === 'continuar') {
      this.isSaving = false;
      return;
    }

    if (accion === 'continuar' && this.horasEsperadasPasantia && this.informacionPasantia.duracionHoras !== this.horasEsperadasPasantia) {
      this.isSaving = false;
      this.alertService().showAlert(
        `Para este ciclo la pasantía debe ser de ${this.horasEsperadasPasantia} horas efectivas.`,
        'danger'
      );
      return;
    }

    if (accion === 'continuar' && Number(this.proyecto.proyectoModalidadId) === 9003) {
      const inicio = this.proyecto.fechaIni ? new Date(this.proyecto.fechaIni) : null;
      const fin = this.proyecto.fechaFin ? new Date(this.proyecto.fechaFin) : null;
      if (inicio && fin) {
        const meses = (fin.getTime() - inicio.getTime()) / (1000 * 60 * 60 * 24 * 30);
        if (meses < 3) {
          this.isSaving = false;
          this.alertService().showAlert('La pasantía internacional debe durar mínimo tres (3) meses.', 'danger');
          return;
        }
      }
    }

    if (this.informacionPasantia.id) {
      this.informacionPasantiaService()
        .update(this.informacionPasantia)
        .then(param => {
          if (accion === 'borrador') {
            this.isSaving = false;
            this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
            return;
          }
          return this.actualizarFechaFinEstimada().then(() => {
            this.isSaving = false;
            this.$router.push({ name: 'PropuestaAdjuntarPropuestaPasantiaView', params: { proyectoId: this.proyId } });
            const message = this.$t('ciecytApp.proyecto.updated', { param: param.id });
            this.alertService().showAlert(message, 'info');
          });
        })
        .catch(err => {
          this.isSaving = false;
          this.alertService().showHttpError(null, err);
        });
    } else {
      this.informacionPasantia.informacionPasantiaProyectoId = parseInt(this.proyId);
      this.informacionPasantiaService()
        .create(this.informacionPasantia)
        .then(param => {
          this.informacionPasantia.id = param.id;
          if (accion === 'borrador') {
            this.isSaving = false;
            this.alertService().showAlert('Borrador guardado. Aún puedes continuar más tarde.', 'info');
            return;
          }
          return this.actualizarFechaFinEstimada().then(() => {
            this.isSaving = false;
            this.$router.push({ name: 'PropuestaAdjuntarPropuestaPasantiaView', params: { proyectoId: this.proyId } });
            const message = 'Se ha creado un nuevo elemento de pasantía';
            this.alertService().showAlert(message, 'success');
          });
        })
        .catch(err => {
          this.isSaving = false;
          this.alertService().showHttpError(null, err);
        });
    }
    this.submitStatus = 'PENDING';
    setTimeout(() => {
      this.submitStatus = 'OK';
    }, 500);
  }

  async initRelationships() {
    this.proyId = this.$route.params.proyectoId;

    this.proyecto = await this.proyectoService().find(parseInt(this.proyId, 10));
    this.isFetching = true;
    await this.informacionPasantiaService()
      .findInformacionPasantiaProyecto(this.proyId)
      .then(
        res => {
          if (res) this.informacionPasantia = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }
}
</script>

<style scoped></style>
