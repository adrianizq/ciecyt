import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IProyectoFase } from '@/shared/model/proyecto-fase.model';
import ProyectoFaseService from './proyecto-fase.service';

@Component
export default class ProyectoFaseDetails extends Vue {
  @Inject private proyectoFaseService: () => ProyectoFaseService;
  public proyectoFase: IProyectoFase = {};

  @Hook
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
    this.$router.push({ name: 'ProyectoFase' });
  }
}
