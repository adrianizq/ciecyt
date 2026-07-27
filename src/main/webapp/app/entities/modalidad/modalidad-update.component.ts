import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

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
  validations,
})
export default class ModalidadUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('modalidadService') private modalidadService: () => ModalidadService;
  public modalidad: IModalidad = new Modalidad();

  @Inject('cicloPropedeuticoService') private cicloPropedeuticoService: () => CicloPropedeuticoService;

  public cicloPropedeuticos: ICicloPropedeutico[] = [];

  @Inject('acuerdoService') private acuerdoService: () => AcuerdoService;

  public acuerdos: IAcuerdo[] = [];
  public isSaving = false;

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
