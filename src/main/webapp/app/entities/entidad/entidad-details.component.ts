import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IEntidad } from '@/shared/model/entidad.model';
import EntidadService from './entidad.service';

@Component
export default class EntidadDetails extends Vue {
  @Inject private entidadService: () => EntidadService;
  public entidad: IEntidad = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.entidadId) {
        vm.retrieveEntidad(to.params.entidadId);
      }
    });
  }

  public retrieveEntidad(entidadId) {
    this.entidadService()
      .find(entidadId)
      .then(res => {
        this.entidad = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
