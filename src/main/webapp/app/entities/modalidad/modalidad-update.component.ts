import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import CicloPropedeuticoService from '../ciclo-propedeutico/ciclo-propedeutico.service';
import { ICicloPropedeutico } from '@/shared/model/ciclo-propedeutico.model';

import AcuerdoService from '../acuerdo/acuerdo.service';
import { IAcuerdo } from '@/shared/model/acuerdo.model';

import AlertService from '@/shared/alert/alert.service';
import { IModalidad, Modalidad } from '@/shared/model/modalidad.model';
import ModalidadService from './modalidad.service';

const validations: any = {
  modalidad: {
    modalidad: {},
    contieneLinea: {},
  },
};

@Component({
  options: {
    validations,
  },
  setup() {
    return { v$: useVuelidate() };
  },
})
export default class ModalidadUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private modalidadService: () => ModalidadService;
  public modalidad: IModalidad = new Modalidad();

  @Inject private cicloPropedeuticoService: () => CicloPropedeuticoService;

  public cicloPropedeuticos: ICicloPropedeutico[] = [];

  @Inject private acuerdoService: () => AcuerdoService;

  public acuerdos: IAcuerdo[] = [];
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.modalidadId) {
        vm.retrieveModalidad(to.params.modalidadId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.modalidad.id) {
      this.modalidadService()
        .update(this.modalidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.modalidad.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        });
    } else {
      this.modalidadService()
        .create(this.modalidad)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.modalidad.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        });
    }
  }

  public retrieveModalidad(modalidadId): void {
    this.modalidadService()
      .find(modalidadId)
      .then(res => {
        this.modalidad = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cicloPropedeuticoService()
      .retrieve()
      .then(res => {
        this.cicloPropedeuticos = res.data;
      });
    this.acuerdoService()
      .retrieve()
      .then(res => {
        this.acuerdos = res.data;
      });
  }
}
