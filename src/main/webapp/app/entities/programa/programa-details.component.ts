import { Component, Vue, Inject, Hook } from 'vue-facing-decorator';

import { IPrograma } from '@/shared/model/programa.model';
import ProgramaService from './programa.service';

@Component
export default class ModalidadDetails extends Vue {
  @Inject private programaService: () => ProgramaService;
  public programa: IPrograma = {};

  @Hook
  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.programaId) {
        vm.retrievePrograma(to.params.programaId);
      }
    });
  }

  public retrievePrograma(programaId) {
    this.programaService()
      .find(programaId)
      .then(res => {
        this.programa = res;
      });
  }

  public previousState() {
    this.$router.push({ name: 'Programa' });
  }
}
