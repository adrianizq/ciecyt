import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProyectoFase } from '@/shared/model/proyecto-fase.model';
import ProyectoFaseService from './proyecto-fase.service';

@Component
export default class ProyectoFaseDetails extends Vue {
  @Inject('proyectoFaseService') private proyectoFaseService: () => ProyectoFaseService;
  public proyectoFase: IProyectoFase = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.proyectoFaseId) {
        vm.retrieveProyectoFase(to.params.proyectoFaseId);
      }
    });
  }

  public retrieveProyectoFase(proyectoFaseId) {
    this.proyectoFaseService()
      .find(proyectoFaseId)
      .then(res => {
        this.proyectoFase = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
