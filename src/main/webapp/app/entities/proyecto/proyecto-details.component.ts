import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProyecto } from '@/shared/model/proyecto.model';
import ProyectoService from './proyecto.service';

@Component
export default class ProyectoDetails extends Vue {
  @Inject('proyectoService') private proyectoService: () => ProyectoService;
  public proyecto: IProyecto = {};

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
