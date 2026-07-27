import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minLength, maxLength } from 'vuelidate/lib/validators';

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
  validations,
})
export default class SolicitudUpdate extends Vue {
  @Inject('alertService') private alertService: () => AlertService;
  @Inject('solicitudService') private solicitudService: () => SolicitudService;
  public solicitud: ISolicitud = new Solicitud();

  @Inject('integranteProyectoService') private integranteProyectoService: () => IntegranteProyectoService;

  public integranteProyectos: IIntegranteProyecto[] = [];
  public isSaving = false;

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
        });
    } else {
      this.solicitudService()
        .create(this.solicitud)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ciecytApp.solicitud.created', { param: param.id });
          this.alertService().showAlert(message, 'success');
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
