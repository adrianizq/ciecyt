import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength, minValue, maxValue } from 'vuelidate/lib/validators';

import ProyectoService from '../proyecto/proyecto.service';
import { IProyecto } from '@/shared/model/proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { IInformacionPasantia, InformacionPasantia } from '@/shared/model/informacion-pasantia.model';
import InformacionPasantiaService from './informacion-pasantia.service';

const validations: any = {
  informacionPasantia: {
    duracionHoras: {},
    direccion: {},
    email: {},
    lunes: {},
    martes: {},
    miercoles: {},
    jueves: {},
    viernes: {},
    sabado: {},
    domingo: {},
    horasMes: {},
    bonoAlimenticio: {},
    apoyoEconomico: {},
    auxilioTransporte: {},
    capacitacion: {},
    otroApoyo: {},
    nombreEmpresa: {},
    nitEmpresa: {},
    direccionEmpresa: {},
    sectorEconomicoEmpresa: {},
    representanteLegalEmpresa: {},
    asesorEmpresa: {},
    cargoAsesorEmpresa: {},
    emailAsesorEmpresa: {},
    municipioEmpresa: {},
    telefonoContactoEmpresa: {},
    emailEmpresa: {},
    departamentoEmpresa: {},
    identificacionRepresentanteLegal: {},
    profesionAsesorEmpresa: {},
    celularAsesorEmpresa: {},
  },
};

@Component({
  validations,
})
export default class InformacionPasantiaUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('informacionPasantiaService') private informacionPasantiaService: () => InformacionPasantiaService;
  public informacionPasantia: IInformacionPasantia = new InformacionPasantia();

  @Inject('proyectoService') private proyectoService: () => ProyectoService;

  public proyectos: IProyecto[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.informacionPasantiaId) {
        vm.retrieveInformacionPasantia(to.params.informacionPasantiaId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.informacionPasantia.id) {
      this.informacionPasantiaService()
        .update(this.informacionPasantia)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytVueApp.informacionPasantia.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.informacionPasantiaService()
        .create(this.informacionPasantia)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytVueApp.informacionPasantia.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveInformacionPasantia(informacionPasantiaId): void {
    this.informacionPasantiaService()
      .find(informacionPasantiaId)
      .then(res => {
        this.informacionPasantia = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.proyectoService()
      .retrieve()
      .then(res => {
        this.proyectos = res.data;
      });
  }
}
