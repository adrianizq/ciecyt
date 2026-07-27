import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICicloPropedeutico } from '@/shared/model/ciclo-propedeutico.model';
import CicloPropedeuticoService from './ciclo-propedeutico.service';

@Component
export default class CicloPropedeuticoDetails extends Vue {
  @Inject('cicloPropedeuticoService') private cicloPropedeuticoService: () => CicloPropedeuticoService;
  public cicloPropedeutico: ICicloPropedeutico = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cicloPropedeuticoId) {
        vm.retrieveCicloPropedeutico(to.params.cicloPropedeuticoId);
      }
    });
  }

  public retrieveCicloPropedeutico(cicloPropedeuticoId) {
    this.cicloPropedeuticoService()
      .find(cicloPropedeuticoId)
      .then(res => {
        this.cicloPropedeutico = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
