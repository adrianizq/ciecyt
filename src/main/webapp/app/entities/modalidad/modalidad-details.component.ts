import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IModalidad } from '@/shared/model/modalidad.model';
import ModalidadService from './modalidad.service';

@Component
export default class ModalidadDetails extends Vue {
  @Inject private modalidadService: () => ModalidadService;
  public modalidad: IModalidad = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.modalidadId) {
        vm.retrieveModalidad(to.params.modalidadId);
      }
    });
  }

  public retrieveModalidad(modalidadId) {
    this.modalidadService()
      .find(modalidadId)
      .then(res => {
        this.modalidad = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
