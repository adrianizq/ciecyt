import { useVuelidate } from '@vuelidate/core';
import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { numeric, required, minLength, maxLength } from '@vuelidate/validators';

import IntegranteProyectoService from '../integrante-proyecto/integrante-proyecto.service';
import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';

import AlertService from '@/shared/alert/alert.service';
import { ISolicitud, Solicitud } from '@/shared/model/solicitud.model';
import SolicitudService from './solicitud.service';

const validations: any = {
  solicitud: {
    estado: {},
    asunto: {},
    textoSolicitud: {},
    fechaSolicitud: {},
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
export default class SolicitudUpdate extends Vue {
  @Inject private alertService: () => AlertService;
  @Inject private solicitudService: () => SolicitudService;
  public solicitud: ISolicitud = new Solicitud();

  @Inject private integranteProyectoService: () => IntegranteProyectoService;

  public integranteProyectos: IIntegranteProyecto[] = [];
  public isSaving = false;

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solicitudId) {
        vm.retrieveSolicitud(to.params.solicitudId);
      }
      vm.initRelationships();
    });
  }

  public save(): void {
    this.isSaving = true;
    if (this.solicitud.id) {
      this.solicitudService()
        .update(this.solicitud)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.solicitud.updated', { param: param.id });
          this.alertService().showAlert(message, 'info');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    } else {
      this.solicitudService()
        .create(this.solicitud)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.solicitud.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error);
        });
    }
  }

  public retrieveSolicitud(solicitudId): void {
    this.solicitudService()
      .find(solicitudId)
      .then(res => {
        this.solicitud = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.integranteProyectoService()
      .retrieve()
      .then(res => {
        this.integranteProyectos = res.data;
      });
  }
}
