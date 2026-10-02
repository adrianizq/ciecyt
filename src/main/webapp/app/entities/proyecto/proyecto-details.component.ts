import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IProyecto } from '@/shared/model/proyecto.model';
import ProyectoService from './proyecto.service';

@Component
export default class ProyectoDetails extends Vue {
  @Inject private proyectoService: () => ProyectoService;
  public proyecto: IProyecto = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoId) {
        vm.retrieveProyecto(to.params.proyectoId);
      }
    });
  }

  public retrieveProyecto(proyectoId) {
    this.proyectoService()
      .find(proyectoId)
      .then(res => {
        this.proyecto = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
