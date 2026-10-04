import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IIntegranteProyecto } from '@/shared/model/integrante-proyecto.model';
import IntegranteProyectoService from './integrante-proyecto.service';

@Component
export default class IntegranteProyectoDetails extends Vue {
  @Inject private integranteProyectoService: () => IntegranteProyectoService;
  public integranteProyecto: IIntegranteProyecto = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.integranteProyectoId) {
        vm.retrieveIntegranteProyecto(to.params.integranteProyectoId);
      }
    });
  }

  public retrieveIntegranteProyecto(integranteProyectoId) {
    this.integranteProyectoService()
      .find(integranteProyectoId)
      .then(res => {
        this.integranteProyecto = res;
      });
  }

  public previousState() {
    this.$router.push({ name: 'IntegranteProyecto' });
  }
}
