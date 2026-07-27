import { Component, Vue, Inject } from 'vue-property-decorator';

import { ISolicitud } from '@/shared/model/solicitud.model';
import SolicitudService from './solicitud.service';

@Component
export default class SolicitudDetails extends Vue {
  @Inject('solicitudService') private solicitudService: () => SolicitudService;
  public solicitud: ISolicitud = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.solicitudId) {
        vm.retrieveSolicitud(to.params.solicitudId);
      }
    });
  }

  public retrieveSolicitud(solicitudId) {
    this.solicitudService()
      .find(solicitudId)
      .then(res => {
        this.solicitud = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
